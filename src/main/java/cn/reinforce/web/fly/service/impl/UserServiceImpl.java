package cn.reinforce.web.fly.service.impl;

import cn.reinforce.plugin.util.EncryptUtil;
import cn.reinforce.web.fly.dao.UserMapper;
import cn.reinforce.web.fly.dao.UserProfileMapper;
import cn.reinforce.web.fly.model.User;
import cn.reinforce.web.fly.model.UserProfile;
import cn.reinforce.web.fly.service.UserService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserProfileMapper userProfileMapper;

    @Override
    public User find(String uid) {
        return userMapper.find(uid);
    }

    @Override
    public boolean exist(String uid) {
        return find(uid) != null;
    }


    @Override
    public void save(User user) {
        userMapper.save(user);
    }

    @Override
    public User save(String nickname, String username, String mobile, boolean mobileStatus, char sex, String email, String password) {
        User user = new User();
        user.setNickname(StringUtils.trim(nickname));
        user.setUsername(StringUtils.trim(username));
        user.setMobile(mobile);
        user.setEmail(StringUtils.trim(email));
        user.setUserType(User.USER_TYPE_NORMAL);
        user.setState(User.STATE_NORMAL);
        long now = System.currentTimeMillis();
        user.setActivateTime(now);
        user.setPassword(EncryptUtil.pwd(now, password));
        userMapper.save(user);

        UserProfile profile = new UserProfile();
        profile.setUid(user.getUid());
        profile.setSex(sex);
        profile.setMobileStatus(mobileStatus);
        userProfileMapper.save(profile);

        return user;
    }

    @Override
    public User update(User user) {
        userMapper.update(user);
        return userMapper.find(user.getUid());
    }

    @Override
    public int updateAvatar(String uid, String avatar) {
        return userMapper.updateAvatar(uid, avatar);
    }

    @Override
    public void delete(String uid) {
        userMapper.delete(uid);
    }

    @Override
    public User login(String loginName, String pwd) {
        User user = userMapper.findByLoginName(loginName, User.STATE_NORMAL);
        String p = EncryptUtil.pwd(user.getActivateTime(), pwd);
        System.out.println(p);
        if (p.equals(user.getPassword())) {
            return user;
        }
        return null;
    }

    @Override
    public boolean checkLoginName(String loginName) {
        return userMapper.findByLoginName(loginName, User.STATE_NORMAL) != null;
    }

    @Override
    public boolean checkUsername(String username) {
        return userMapper.findByUsername(username, User.STATE_NORMAL) != null;
    }

    @Override
    public boolean checkNickName(String nickname) {
        return userMapper.findByNickName(nickname) != null;
    }

    @Override
    public User findByMobile(String mobile) {
        return userMapper.findByMobile(mobile);
    }


    @Override
    public List<User> searchByMobile(String mobile) {
        return userMapper.searchByMobile("%" + mobile + "%");
    }


    @Override
    public void batchDel(List<Integer> ids) {
        userMapper.batchDel(ids);
    }


}
