package cn.reinforce.web.fly.model;

import com.google.gson.annotations.Expose;

import java.util.Date;

public class UserSession {

    public static final String[] types = {"本地","QQ","新浪"};

    public static final int TYPE_LOCAL = 0;

    public static final int TYPE_QQ = 1;

    public static final int TYPE_XINLANG = 2;

    @Expose
    private String id;

    @Expose
    private Date lastLoginDate;

    @Expose
    private String lastLoginIp;

    @Expose
    private Date loginDate;

    @Expose
    private String loginIp;

    @Expose
    private String mSessionId;

    @Expose
    private String sessionId;

    @Expose
    private int type;

    @Expose
    private String uid;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public Date getLastLoginDate() {
        return lastLoginDate;
    }

    public void setLastLoginDate(Date lastLoginDate) {
        this.lastLoginDate = lastLoginDate;
    }

    public String getLastLoginIp() {
        return lastLoginIp;
    }

    public void setLastLoginIp(String lastLoginIp) {
        this.lastLoginIp = lastLoginIp == null ? null : lastLoginIp.trim();
    }

    public Date getLoginDate() {
        return loginDate;
    }

    public void setLoginDate(Date loginDate) {
        this.loginDate = loginDate;
    }

    public String getLoginIp() {
        return loginIp;
    }

    public void setLoginIp(String loginIp) {
        this.loginIp = loginIp == null ? null : loginIp.trim();
    }

    public String getmSessionId() {
        return mSessionId;
    }

    public void setmSessionId(String mSessionId) {
        this.mSessionId = mSessionId == null ? null : mSessionId.trim();
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId == null ? null : sessionId.trim();
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}