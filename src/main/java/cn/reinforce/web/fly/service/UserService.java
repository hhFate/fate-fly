package cn.reinforce.web.fly.service;

import cn.reinforce.web.fly.model.User;

import java.util.List;


public interface UserService {

    User find(String uid);

    boolean exist(String uid);

    void save(User user);

    User save(String nickname, String username, String mobile, boolean mobileStatus, char sex, String email, String password);

    User update(User user);

    int updateAvatar(String uid, String avatar);

    void delete(String uid);

    User login(String loginName, String pwd);

    /**
     * 检查用户名是否存在
     *
     * @param loginName
     * @return 返回true则存在，返回false则不存在
     */
    boolean checkLoginName(String loginName);

    boolean checkUsername(String username);

    boolean checkNickName(String nickname);

    User findByMobile(String mobile);

    List<User> searchByMobile(String mobile);

    /**
     * 批量删除
     *
     * @param ids
     */
    void batchDel(List<Integer> ids);

}
