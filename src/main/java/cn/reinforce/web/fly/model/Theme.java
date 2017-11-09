package cn.reinforce.web.fly.model;

import cn.reinforce.plugin.util.Tools;

import javax.persistence.Access;
import javax.persistence.AccessType;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class Theme implements Serializable {

    /**
     * 草稿状态
     */
    public static final int STATE_EDIT = 1;

    /**
     * 发布状态
     */
    public static final int STATE_PUBLISH = 2;
    private static final long serialVersionUID = -3535284072732140728L;

    private int id;

    private String author;

    private String authorId;

    private int down;

    private int fid;

    private String forumName;

    private boolean isDelete;

    private Date lastModify;

    private int priority;

    private Date publishDate;

    private int replies;

    private boolean reprint;

    private int search;

    private int state;

    private String tags;

    private String title;

    private String titleImg;

    private int up;

    private int views;

    private String guid;

    private String content;

    private String contentView;

    @Access(AccessType.PROPERTY)
    private String imgList;

    private String url;

    private List<String> imgs;

    /**
     * 代码演示的文章需要额外加载的css
     */
    private String css;

    /**
     * 代码演示的文章需要额外加载的js
     */
    private String js;

    /**
     * 是否推送到过公众号
     */
    private boolean push;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author == null ? null : author.trim();
    }

    public String getAuthorId() {
        return authorId;
    }

    public void setAuthorId(String authorId) {
        this.authorId = authorId;
    }

    public int getDown() {
        return down;
    }

    public void setDown(int down) {
        this.down = down;
    }

    public int getFid() {
        return fid;
    }

    public void setFid(int fid) {
        this.fid = fid;
    }

    public String getForumName() {
        return forumName;
    }

    public void setForumName(String forumName) {
        this.forumName = forumName == null ? null : forumName.trim();
    }

    public boolean getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(boolean isDelete) {
        this.isDelete = isDelete;
    }

    public Date getLastModify() {
        return lastModify;
    }

    public void setLastModify(Date lastModify) {
        this.lastModify = lastModify;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public Date getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
    }

    public int getReplies() {
        return replies;
    }

    public void setReplies(int replies) {
        this.replies = replies;
    }

    public boolean isReprint() {
        return reprint;
    }

    public void setReprint(boolean reprint) {
        this.reprint = reprint;
    }

    public int getSearch() {
        return search;
    }

    public void setSearch(int search) {
        this.search = search;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags == null ? null : tags.trim();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public int getUp() {
        return up;
    }

    public void setUp(int up) {
        this.up = up;
    }

    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid == null ? null : guid.trim();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public String getContentView() {
        return contentView;
    }

    public void setContentView(String contentView) {
        this.contentView = contentView;
    }

    public String getImgList() {
        return imgList;
    }

    public void setImgList(String imgList) {
        if (imgList != null) {
            imgs = Tools.asList(imgList.split(","));
        }
        this.imgList = imgList == null ? null : imgList.trim();
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    public List<String> getImgs() {
        return imgs;
    }

    public void setImgs(List<String> imgs) {
        this.imgs = imgs;
    }

    public String getCss() {
        return css;
    }

    public void setCss(String css) {
        this.css = css;
    }

    public String getJs() {
        return js;
    }

    public void setJs(String js) {
        this.js = js;
    }

    public boolean isPush() {
        return push;
    }

    public void setPush(boolean push) {
        this.push = push;
    }

    public String getTitleImg() {
        return titleImg;
    }

    public void setTitleImg(String titleImg) {
        this.titleImg = titleImg;
    }
}