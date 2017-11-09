package cn.reinforce.web.fly.service;

import cn.reinforce.web.fly.model.User;
import cn.reinforce.web.fly.model.UserSession;

import javax.servlet.http.HttpServletRequest;

public interface UserSessionService {

	void save(UserSession userSession);
	
	UserSession update(UserSession userSession);
	
	UserSession findByUser(String uid);
	
	UserSession login(User user, HttpServletRequest request);

	UserSession mlogin(User user, HttpServletRequest request);

	boolean mCheckToken(String uid, String accessToken);
}
