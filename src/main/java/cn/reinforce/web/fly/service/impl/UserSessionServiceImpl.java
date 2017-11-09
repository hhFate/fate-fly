package cn.reinforce.web.fly.service.impl;

import cn.reinforce.plugin.util.GsonUtil;
import cn.reinforce.plugin.util.HttpClientUtil;
import cn.reinforce.plugin.util.IPUtil;
import cn.reinforce.plugin.util.MD5;
import cn.reinforce.plugin.util.TokenUtil;
import cn.reinforce.plugin.util.entity.HttpResult;
import cn.reinforce.web.fly.common.Constants;
import cn.reinforce.web.fly.dao.UserSessionMapper;
import cn.reinforce.web.fly.model.SsoVO;
import cn.reinforce.web.fly.model.User;
import cn.reinforce.web.fly.model.UserSession;
import cn.reinforce.web.fly.service.ParamService;
import cn.reinforce.web.fly.service.UserSessionService;
import com.google.gson.Gson;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Service
@Transactional
public class UserSessionServiceImpl implements UserSessionService {

    private final static Logger LOG = Logger.getLogger(UserSessionServiceImpl.class);

    @Autowired
    private UserSessionMapper userSessionMapper;

    @Autowired
    private ParamService paramService;

//    @Autowired
//    private ThirdPartyAccountDao thirdPartyAccountDao;

    @Override
    public void save(UserSession userSession) {
        userSessionMapper.save(userSession);
    }

    @Override
    public UserSession update(UserSession userSession) {
        userSessionMapper.update(userSession);
        return userSession;
    }

    @Override
    public UserSession findByUser(String uid) {
        Gson gson = GsonUtil.getGson();
        String endpoint = paramService.findByKeyNotNull(Constants.SSO_ENDPOINT).getTextValue();
        String appKey = paramService.findByKeyNotNull(Constants.SSO_APP_KEY).getTextValue();
        String appSecret = paramService.findByKeyNotNull(Constants.SSO_APP_SECRET).getTextValue();

        long timestamp = System.currentTimeMillis();
        String sign = MD5.md5(appKey+appSecret+timestamp);
        HttpResult result = HttpClientUtil.get("https://"+endpoint + "/app/user/session/"+uid+"?appKey="+appKey+"&sign="+sign+"&timestamp="+timestamp);
        UserSession userSession = gson.fromJson(result.getResult(), SsoVO.class).getUserSession();
        return userSession;
    }


    @Override
    public UserSession login(User user, HttpServletRequest request) {
        UserSession userSession = findByUser(user.getUid());
        boolean save = false;
        if (userSession == null) {
            userSession = new UserSession();
            save = true;
        } else {
            userSession.setLastLoginDate(userSession.getLoginDate());
            userSession.setLastLoginIp(userSession.getLoginIp());
        }
        userSession.setLoginDate(new Date());
        userSession.setLoginIp(IPUtil.getIp(request));
        String sessionId = TokenUtil.getRandomString(32, 2);
        userSession.setSessionId(sessionId);
        userSession.setUid(user.getUid());
        userSession.setType(0);
        if (save) {
            save(userSession);
        } else {
            userSession = update(userSession);
        }
        return userSession;
    }

    @Override
    public UserSession mlogin(User user, HttpServletRequest request) {
        UserSession userSession = findByUser(user.getUid());
        if (userSession == null) {
            userSession = new UserSession();
            userSession.setUid(user.getUid());
            userSessionMapper.save(userSession);
        } else {
            userSession.setLastLoginDate(userSession.getLoginDate());
            userSession.setLastLoginIp(userSession.getLoginIp());
        }
        userSession.setLoginDate(new Date());
        userSession.setLoginIp(IPUtil.getIp(request));
        String sessionId = TokenUtil.getRandomString(32, 2);
        userSession.setmSessionId(sessionId);
        userSession = update(userSession);
        return userSession;
    }

    @Override
    public boolean mCheckToken(String uid, String accessToken) {
        UserSession userSession = userSessionMapper.findByUser(uid);
        if (userSession!=null && accessToken.equals(userSession.getmSessionId())) {
            return true;
        }
        return false;
    }
}
