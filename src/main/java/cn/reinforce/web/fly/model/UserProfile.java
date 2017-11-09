package cn.reinforce.web.fly.model;

import com.google.gson.annotations.Expose;

import java.io.Serializable;
import java.util.Date;

/**
 * 对User类的扩展
 * 存储一些不常用的用户信息
 * @author Fate
 * @create 2017/3/22
 */
public class UserProfile implements Serializable{


    private static final long serialVersionUID = -8969794335656240884L;

    @Expose
    private int id;

    @Expose
    private String address;

    @Expose
    private Date birthday;

    @Expose
    private boolean emailStatus;

    @Expose
    private String avatarBig;

    @Expose
    private String avatarHd;

    @Expose
    private String avatarLocal;

    @Expose
    private String avatarMid;

    @Expose
    private String avatarSmall;

    @Expose
    private boolean mobileStatus;

    @Expose
    private String qq;

    @Expose
    private char sex;

    @Expose
    private String uid;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public boolean getEmailStatus() {
        return emailStatus;
    }

    public void setEmailStatus(boolean emailStatus) {
        this.emailStatus = emailStatus;
    }

    public String getHeadIconBig() {
        return avatarBig;
    }

    public void setHeadIconBig(String avatarBig) {
        this.avatarBig = avatarBig == null ? null : avatarBig.trim();
    }

    public String getHeadIconHd() {
        return avatarHd;
    }

    public void setHeadIconHd(String avatarHd) {
        this.avatarHd = avatarHd == null ? null : avatarHd.trim();
    }

    public String getHeadIconLocal() {
        return avatarLocal;
    }

    public void setHeadIconLocal(String avatarLocal) {
        this.avatarLocal = avatarLocal == null ? null : avatarLocal.trim();
    }

    public String getHeadIconMid() {
        return avatarMid;
    }

    public void setHeadIconMid(String avatarMid) {
        this.avatarMid = avatarMid == null ? null : avatarMid.trim();
    }

    public String getHeadIconSmall() {
        return avatarSmall;
    }

    public void setHeadIconSmall(String avatarSmall) {
        this.avatarSmall = avatarSmall == null ? null : avatarSmall.trim();
    }

    public boolean getMobileStatus() {
        return mobileStatus;
    }

    public void setMobileStatus(boolean mobileStatus) {
        this.mobileStatus = mobileStatus;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq == null ? null : qq.trim();
    }

    public char getSex() {
        return sex;
    }

    public void setSex(char sex) {
        this.sex = sex;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

}