package cn.reinforce.web.fly.model;

import java.io.Serializable;
import java.util.List;

public class Forum implements Serializable{

    /**
     * 分区
     */
    public static final int TYPE_REGION = 1;

    /**
     * 版块
     */
    public static final int TYPE_FORUM = 2;

    /**
     * 子版块
     */
    public static final int TYPE_SUB = 3;

    private static final long serialVersionUID = 2166219440386863965L;

    /**
     * 板块id
     */
    private int fid;

    /**
     * 描述
     */
    private String forumDesc;

    /**
     * 板块名称
     */
    private String forumName;

    /**
     * 顺序
     */
    private int forumOrder;

    private int type;

    private Integer parentForumId;

    private Forum parentForum;

    private List<Forum> childForums;

    private boolean delete;

    public int getFid() {
        return fid;
    }

    public void setFid(int fid) {
        this.fid = fid;
    }

    public String getForumDesc() {
        return forumDesc;
    }

    public void setForumDesc(String forumDesc) {
        this.forumDesc = forumDesc == null ? null : forumDesc.trim();
    }

    public String getForumName() {
        return forumName;
    }

    public void setForumName(String forumName) {
        this.forumName = forumName == null ? null : forumName.trim();
    }

    public int getForumOrder() {
        return forumOrder;
    }

    public void setForumOrder(int forumOrder) {
        this.forumOrder = forumOrder;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Integer getParentForumId() {
        return parentForumId;
    }

    public void setParentForumId(Integer parentForumId) {
        this.parentForumId = parentForumId;
    }

    public Forum getParentForum() {
        return parentForum;
    }

    public void setParentForum(Forum parentForum) {
        this.parentForum = parentForum;
    }

    public List<Forum> getChildForums() {
        return childForums;
    }

    public void setChildForums(List<Forum> childForums) {
        this.childForums = childForums;
    }

    public boolean isDelete() {
        return delete;
    }

    public void setDelete(boolean delete) {
        this.delete = delete;
    }
}