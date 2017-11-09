package cn.reinforce.web.fly.dao;

import cn.reinforce.web.fly.model.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserMapper {

    int save(User user);

    User find(String uid);

    int update(User user);

    int updateAvatar(@Param("uid") String uid, @Param("avatar") String avatar);

    int delete(String uid);

    User findByLoginName(@Param("loginName") String loginName, @Param("state") int state);

    User findByUsername(@Param("username") String username, @Param("state") int state);

    User findByMobile(String mobile);

    User findByNickName(String nickname);

    List<User> searchByMobile(@Param("mobile") String mobile);

    List<User> search(@Param("keyword") String keyword, @Param("uid") String uid);


    void batchDel(List<Integer> ids);
}