package cn.reinforce.web.fly.model;

import com.google.gson.annotations.Expose;

/**
 * @author Fate
 * @create 2017/6/23
 */
public class SsoVO {

    @Expose
    private User user;

    @Expose
    private UserSession userSession;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public UserSession getUserSession() {
        return userSession;
    }

    public void setUserSession(UserSession userSession) {
        this.userSession = userSession;
    }
}
