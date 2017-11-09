package cn.reinforce.web.fly.interceptor;

import cn.reinforce.plugin.util.GsonUtil;
import cn.reinforce.plugin.util.IPUtil;
import cn.reinforce.plugin.util.Tools;
import cn.reinforce.web.fly.common.Constants;
import cn.reinforce.web.fly.model.Param;
import cn.reinforce.web.fly.service.ParamService;
import com.google.gson.Gson;
import cn.reinforce.web.fly.common.ErrorCodes;
import cn.reinforce.web.fly.model.Result;
import cn.reinforce.web.fly.model.User;
import cn.reinforce.web.fly.model.http.FateRequest;
import cn.reinforce.web.fly.model.http.RequestLog;
import cn.reinforce.web.fly.service.FateRequestService;
import cn.reinforce.web.fly.service.UserService;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

@Component
public class RequestLogInterceptor implements HandlerInterceptor {

    private static final Logger LOG = Logger.getLogger(RequestLogInterceptor.class);

    @Autowired
    private UserService userService;

    @Autowired
    private ParamService paramService;

    @Autowired
    private FateRequestService fateRequestService;

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception e) {
        if (e != null) {
            e.printStackTrace();
            if (handler.getClass().isAssignableFrom(HandlerMethod.class)) {
                HandlerMethod handlerMethod = (HandlerMethod) handler;
                RequestLog requestLog = handlerMethod.getMethodAnnotation(RequestLog.class);
                if (requestLog != null) {
                    PrintWriter pw = null;
                    try {
                        String requestId = (String) request.getAttribute("requestId");
                        StringWriter sw = new StringWriter();
                        e.printStackTrace(new PrintWriter(sw, true));
                        fateRequestService.update(requestId, sw.toString(), response.getStatus(), true, null, 0);
                        Map<String, Object> map = new HashMap<>();
                        Result result = new Result(false, ErrorCodes.UNKNOW_ERROR, StringUtils.isEmpty(requestLog.msg()) ? "未知错误，请联系管理员" : requestLog.msg(), requestId);
                        map.put("result", result);
                        Gson gson = GsonUtil.getGson();
                        response.setContentType("text/javascript; charset=utf-8");
                        pw = response.getWriter();
                        pw.write(gson.toJson(map));
                        pw.flush();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    } finally {
                        if (pw != null) {
                            pw.close();
                        }
                    }
                }
            }
        }
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response,
                           Object handler, ModelAndView mv) throws Exception {
        // TODO Auto-generated method stub

    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object handler) throws Exception {
        // TODO Auto-generated method stub
        //绑定全局参数
        HttpSession session = request.getSession();

        if (session.getAttribute("siteName") == null) {
            Param siteName = paramService.findByKeyNotNull(Constants.SITE_NAME);
            session.setAttribute("siteName", siteName.getTextValue());
        }

        if (session.getAttribute("statistics") == null) {
            Param statistics = paramService.findByKeyNotNull(Constants.STATISTICS);
            session.setAttribute("statistics", statistics.getTextValue());
        }

        if (session.getAttribute("statisticsHead") == null) {
            Param statisticsHead = paramService.findByKeyNotNull(Constants.STATISTICSHEAD);
            session.setAttribute("statisticsHead", statisticsHead.getTextValue());
        }

        //处理要记录的操作
        if (handler.getClass().isAssignableFrom(HandlerMethod.class)) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            RequestLog requestLog = handlerMethod.getMethodAnnotation(RequestLog.class);
            User user = null;
            if (requestLog != null) {
                FateRequest fateRequest = new FateRequest();
                fateRequest.setMethod(request.getMethod());
                fateRequest.setUrl(request.getRequestURI());
                fateRequest.setKlass(handlerMethod.getMethod().getDeclaringClass() + "@" + handlerMethod.getMethod().getName());
                fateRequest.setIp(IPUtil.getIp(request));
                fateRequest.setReferer(request.getHeader("Referer"));
                fateRequest.setAgent(request.getHeader("User-Agent"));


                String queries = paramToString(request);
                //数据有效性验证
//				String stringToSign = request.getMethod() + "&" + SignatureUtil.percentEncode("/") + "&" + SignatureUtil.percentEncode(queries);
//				
//				final String ALGORITHM = "HmacSHA1";
//				final String ENCODING = "UTF-8";
//				String key = request.getParameter("requestId")+"&";
//
//				Mac mac = Mac.getInstance(ALGORITHM);
//				mac.init(new SecretKeySpec(key.getBytes(ENCODING), ALGORITHM));
//				byte[] signData = mac.doFinal(stringToSign.getBytes(ENCODING));
//				
//				String signature = new String(Base64.encode(signData));

                //如果是登录接口，则不保存参数，因为有敏感信息
                if (!request.getRequestURI().contains("/login")) {
                    fateRequest.setQueries(queries);
                }
                fateRequest.setTimestamp(System.currentTimeMillis());
                //开放接口无需用户信息
                if (!request.getRequestURI().contains("/op/")) {
                    //先从接口中获取时间戳和用户id
                    long timestamp = Tools.parseLong(request.getParameter("timestamp"));
                    String uid = request.getParameter("uid");
                    if (!StringUtils.isEmpty(uid)) {
                        user = userService.find(uid);
                    }

                    //获取不到，则判断为网页端的调用，从session中获取
                    if (user == null) {
                        user = (User) session.getAttribute("user");
                    }
                    if (user != null) {
                        //有些不需要timestamp的接口直接写入当前时间
                        if (timestamp == 0) {
                            timestamp = System.currentTimeMillis();
                        }
                        fateRequest.setTimestamp(timestamp);
                        fateRequest.setUid(user.getUid());
                        fateRequest.setUsername(user.getNickname());
                    }
                }
                fateRequestService.save(fateRequest);
                //记下requestId，如果后期产生错误码或是报错，可以把相应信息记录下来
                request.setAttribute("requestId", fateRequest.getRequestId());

            }

        }
        return true;
    }

    /**
     * 所有参数转换成一个字符串
     *
     * @param request
     * @return
     */
    public String paramToString(HttpServletRequest request) {
        StringBuffer sb = new StringBuffer();
        Enumeration<String> paramNames = request.getParameterNames();
        while (paramNames.hasMoreElements()) {
            String paramName = (String) paramNames.nextElement();
            if (!"signature".equals(paramName) && !"signature".equals("userSign") && !"customerSign".equals(paramName)) {
                String[] paramValues = request.getParameterValues(paramName);
                for (String s : paramValues) {
                    sb.append(paramName).append("=").append(s).append("&");
                }
            }
        }
        return sb.length() == 0 ? "" : sb.substring(0, sb.length() - 1).toString();
    }
}
