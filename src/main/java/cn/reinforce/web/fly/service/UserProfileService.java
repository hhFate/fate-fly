package cn.reinforce.web.fly.service;

import cn.reinforce.web.fly.model.UserProfile;

public interface UserProfileService {

	void save(UserProfile userProfile);

	UserProfile update(UserProfile userProfile);
	
	UserProfile find(int id);
	
	UserProfile findByUser(String uid);
}
