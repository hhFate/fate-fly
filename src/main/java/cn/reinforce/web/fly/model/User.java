package cn.reinforce.web.fly.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.gson.annotations.Expose;

import javax.persistence.Access;
import javax.persistence.AccessType;
import java.io.Serializable;
import java.util.List;

public class User implements Serializable {

    /**
     * 管理员
     */
    public static final int USER_TYPE_ADMIN = 1;

    /**
     * 普通用户
     */
    public static final int USER_TYPE_NORMAL = 2;


    public static final String[] STATES = {"正常", "已删除"};

    /**
     * 状态正常
     */
    public static final int STATE_NORMAL = 1;

    /**
     * 被禁封
     */
    public static final int STATE_FORBIDDEN = 2;
    private static final long serialVersionUID = -4137626165095969678L;


    @Expose
    private String uid;

    @Expose
    private long activateTime;

    @Expose
    private String username;

    @Expose
    private String email;

    @Expose
    private String avatar;

    @Expose
    private String mobile;

    @Expose
    private String nickname;

    @Expose
    private String realName;

    @Expose
    private String idCard;

    @JsonIgnore
    private String password;

    @Expose
    private String duty;

    @Expose
    private String workNumber;

    @JsonIgnore
    private boolean passwordLock;

    @Access(AccessType.PROPERTY)
    @Expose
    private int state;

    @Expose
    private String stateName;

    @Expose
    private int userType;

    @Expose
    private UserProfile profile;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public long getActivateTime() {
        return activateTime;
    }

    public void setActivateTime(long activateTime) {
        this.activateTime = activateTime;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDuty() {
        return duty;
    }

    public void setDuty(String duty) {
        this.duty = duty;
    }

    public String getWorkNumber() {
        return workNumber;
    }

    public void setWorkNumber(String workNumber) {
        this.workNumber = workNumber;
    }

    public boolean isPasswordLock() {
        return passwordLock;
    }

    public void setPasswordLock(boolean passwordLock) {
        this.passwordLock = passwordLock;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    public int getUserType() {
        return userType;
    }

    public void setUserType(int userType) {
        this.userType = userType;
    }

    public UserProfile getProfile() {
        return profile;
    }

    public void setProfile(UserProfile profile) {
        this.profile = profile;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        User user = (User) o;

        return uid != null ? uid.equals(user.uid) : user.uid == null;
    }

    @Override
    public int hashCode() {
        return uid != null ? uid.hashCode() : 0;
    }
}