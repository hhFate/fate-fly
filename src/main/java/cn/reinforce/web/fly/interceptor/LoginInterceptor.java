package cn.reinforce.web.fly.interceptor;

import cn.reinforce.plugin.util.GsonUtil;
import cn.reinforce.web.fly.common.ErrorCodes;
import cn.reinforce.web.fly.model.User;
import cn.reinforce.web.fly.model.UserSession;
import cn.reinforce.web.fly.service.FateRequestService;
import cn.reinforce.web.fly.service.UserService;
import cn.reinforce.web.fly.service.UserSessionService;
import com.google.gson.Gson;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

@Component
public class LoginInterceptor implements HandlerInterceptor {

    private static final Logger LOG = Logger.getLogger(LoginInterceptor.class);

    @Autowired
    private UserSessionService userSessionService;

    @Autowired
    private FateRequestService fateRequestService;

    @Autowired
    private UserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {

        response.setContentType("text/html; charset=utf-8");
        if (request.getServletPath().contains("/m/") && !request.getServletPath().endsWith("/m/")) {//手机端的登录检测
            boolean flag = false;
            if (!request.getServletPath().contains("/op/")) {

                String uid = request.getParameter("uid");
                String accessToken = request.getParameter("accessToken");
                System.out.println(uid);
                //判断
                if (StringUtils.isEmpty(accessToken) || !userSessionService.mCheckToken(uid, accessToken)) {
                    Map<String, Object> map = new HashMap<>();
                    String requestId = (String) request.getAttribute("requestId");
                    fateRequestService.returnError(map, ErrorCodes.AUTH_FAIL, "登录已过期,请重新登录", requestId, 200, LOG);
                    Gson gson = GsonUtil.getGson();
                    PrintWriter pw = response.getWriter();
                    pw.write(gson.toJson(map));
                    pw.flush();
                    pw.close();
                } else {
                    flag = true;
                }
                return flag;
            }
            return true;
        } else {//PC端的登录检测
            HttpSession session = request.getSession();
            String accessToken = (String) session.getAttribute("accessToken");
            String userSessionId = (String) session.getAttribute("userSessionId");
            User user = (User) session.getAttribute("user");
            String base = request.getScheme() + "://"
                    + request.getServerName() + ":" + request.getServerPort()
                    + request.getContextPath();
            if (user == null || StringUtils.isEmpty(userSessionId) || session == null) {
                response.sendRedirect(base + "/");
                return false;
            }


            boolean flag = false;

            UserSession userSession = userSessionService.findByUser(user.getUid());

            if (userSessionId != null && accessToken != null && accessToken.equals(userSession.getSessionId())) {
                //防止在后台url泄露的情况下，用户直接输入地址进入后台，需验证是否有管理员权限
                if (request.getServletPath().contains("/admin") && user.getUserType() == User.USER_TYPE_NORMAL) {
                    response.sendRedirect(base + "/index");
                } else {
                    flag = true;
                }
            } else {
                session.invalidate();
                session = request.getSession(true);
                session.setAttribute("callback", request.getHeader("REFERER"));
                response.sendRedirect(base + "/");
            }
            return flag;
        }
    }

    @Override
    public void postHandle(HttpServletRequest request,
                           HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
        // TODO Auto-generated method stub

    }

    @Override
    public void afterCompletion(HttpServletRequest request,
                                HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        // TODO Auto-generated method stub
    }

}
