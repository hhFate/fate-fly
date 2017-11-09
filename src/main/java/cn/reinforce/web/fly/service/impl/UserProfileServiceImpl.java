package cn.reinforce.web.fly.service.impl;

import cn.reinforce.web.fly.dao.UserProfileMapper;
import cn.reinforce.web.fly.model.UserProfile;
import cn.reinforce.web.fly.service.UserProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserProfileServiceImpl implements UserProfileService {

	@Autowired
	private UserProfileMapper userProfileMapper;
	
	@Override
	public void save(UserProfile userProfile) {
		userProfileMapper.save(userProfile);
	}

	@Override
	public UserProfile update(UserProfile userProfile) {
		userProfileMapper.update(userProfile);
		return userProfileMapper.find(userProfile.getId());
	}

	@Override
	public UserProfile find(int id) {
		return userProfileMapper.find(id);
	}

	@Override
	public UserProfile findByUser(String uid) {
		return userProfileMapper.findByUser(uid);
	}

}
