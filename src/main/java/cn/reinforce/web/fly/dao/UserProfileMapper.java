package cn.reinforce.web.fly.dao;

import cn.reinforce.web.fly.model.UserProfile;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserProfileMapper {

    int save(UserProfile userProfile);

    UserProfile find(int id);

    int update(UserProfile userProfile);

    /**
     * 根据用户的uid查找资料
     * @param uid
     * @return
     */
    UserProfile findByUser(String uid);
}