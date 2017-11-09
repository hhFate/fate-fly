package cn.reinforce.web.fly.dao;

import cn.reinforce.web.fly.model.UserSession;
import org.springframework.stereotype.Repository;

@Repository
public interface UserSessionMapper {
    int delete(String id);

    int save(UserSession userSession);

    UserSession find(String id);

    int update(UserSession userSession);

    /**
     * 根据用户id查找
     * @param uid
     * @return
     */
    UserSession findByUser(String uid);

}