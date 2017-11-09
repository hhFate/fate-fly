package cn.reinforce.web.fly.controller;

import cn.reinforce.web.fly.common.Constants;
import cn.reinforce.web.fly.common.ErrorCodes;
import cn.reinforce.web.fly.model.Param;
import cn.reinforce.web.fly.model.SsoVO;
import cn.reinforce.web.fly.model.http.RequestLog;
import cn.reinforce.web.fly.service.FateRequestService;
import cn.reinforce.web.fly.service.ParamService;
import cn.reinforce.web.fly.service.UserProfileService;
import cn.reinforce.web.fly.service.UserService;
import cn.reinforce.web.fly.service.UserSessionService;
import cn.reinforce.plugin.util.GsonUtil;
import cn.reinforce.plugin.util.HttpClientUtil;
import cn.reinforce.plugin.util.MD5;
import com.google.gson.Gson;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/op")
public class LoginCtl {

    private static final Logger LOG = Logger.getLogger(LoginCtl.class);

    @Autowired
    private ParamService paramService;

    @Autowired
    private UserSessionService userSessionService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserProfileService userProfileService;

    @Autowired
    private FateRequestService fateRequestService;

    /**
     * 退出登录
     *
     * @param request
     * @param session
     * @return
     */
    @PostMapping("/logout")
    @ResponseBody
    @RequestLog
    public Object logout(HttpServletRequest request, HttpSession session) {
        Map<String, Object> map = new HashMap<>();
        // 清除数据库中的登录状态
//        String guid = ((UserSession) session.getAttribute("userSession")).getId();
//        userSessionService.logout(guid);
        // 清空当前session
        session.invalidate();
        String requestId = (String) request.getAttribute("requestId");
        fateRequestService.returnSuccess(map, "退出登录成功", requestId);
        return map;
    }

    @GetMapping("/login")
    @RequestLog
    public void toSso(HttpServletResponse response, HttpServletRequest request) {
        response.setContentType("text/html;charset=utf-8");
        try {
            if (request.getSession().getAttribute("callback") == null) {
                String callback = request.getHeader("REFERER");
                request.getSession().setAttribute("callback", callback);
            }

            String appUrl = paramService.findByKeyNotNull(Constants.APP_URL).getTextValue();
            String endpoint = paramService.findByKeyNotNull(Constants.SSO_ENDPOINT).getTextValue();
            String appKey = paramService.findByKeyNotNull(Constants.SSO_APP_KEY).getTextValue();
            String appSecret = paramService.findByKeyNotNull(Constants.SSO_APP_SECRET).getTextValue();

            long timestamp = System.currentTimeMillis();
            String sign = MD5.md5(appKey + appSecret + timestamp);

            response.sendRedirect("http://" + endpoint + "/login?redirect_uri="+request.getScheme()+"://" + appUrl + "/op/login/sso&appKey=" + appKey + "&sign=" + sign + "&timestamp=" + timestamp);
        } catch (IOException e) {
            LOG.error("重定向失败", e);
        }
    }

    @GetMapping("/login/sso")
    @ResponseBody
    @RequestLog
    public Object toSso(String uid, String userSessionId, String accessToken, String appKey, String sign, long timestamp, HttpServletRequest request, HttpServletResponse response, HttpSession session) throws IOException {
        Map<String, Object> map = new HashMap<>();
        Gson gson = GsonUtil.getGson();
        String endpoint = paramService.findByKeyNotNull(Constants.SSO_ENDPOINT).getTextValue();
        String appKeyLocal = paramService.findByKeyNotNull(Constants.SSO_APP_KEY).getTextValue();
        String appSecretLocal = paramService.findByKeyNotNull(Constants.SSO_APP_SECRET).getTextValue();

        long now = System.currentTimeMillis();
        String signVerify = MD5.md5(appKeyLocal + appSecretLocal + timestamp);

        String requestId = (String) request.getAttribute("requestId");

        if(!(StringUtils.equals(appKey, appKeyLocal)&&StringUtils.equals(sign, signVerify))){
            fateRequestService.returnError(map, ErrorCodes.ACCESS_TOKEN_ERROR, "APP验证失败", requestId, 200, LOG);
            return map;
        }

        String signForUser = MD5.md5(appKeyLocal + appSecretLocal + now);

        SsoVO ssoVO = gson.fromJson(HttpClientUtil.get("http://" + endpoint + "/app/user/" + uid + "?appKey=" + appKey + "&sign=" + signForUser + "&timestamp=" + now).getResult(), SsoVO.class);

        session.setAttribute("userSessionId", userSessionId);
        session.setAttribute("user", ssoVO.getUser());
        session.setAttribute("accessToken", accessToken);

        String callback = (String) session.getAttribute("callback");
        Param appUrl = paramService.findByKeyNotNull(Constants.APP_URL);
//        if (callback == null || "/op/login".contains(callback)) {
        callback = "http://" + appUrl.getTextValue() + "/";
//        }
        response.sendRedirect(callback);
        return null;
    }

    @GetMapping("/login/accessDenied")
    public String accessDenied() {
        return "login/accessDenied";
    }

}
