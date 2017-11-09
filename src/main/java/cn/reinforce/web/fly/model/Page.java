package cn.reinforce.web.fly.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * 分页类，简化分页操作，配合特定的js使用
 *
 * @author 幻幻(Fate)
 */
public class Page {

    /**
     * 当前页
     */
    private int pageNum;
    /**
     * 每页条数
     */
    private int pageSize;

    /**
     * 总条数
     */
    private int totalSize;
    /**
     * 总页数
     */
    private int totalPage;
    /**
     * 类型
     */
    @JsonIgnore
    private int type;
    /**
     * 中间位置
     */
    @JsonIgnore
    private int middle;

    public Page(int pageNum, int pageSize, int totalSize) {
        this.pageNum = pageNum;
        this.totalSize = totalSize;
        this.pageSize = pageSize;
        this.totalPage = getTotalPage(pageSize, totalSize);
    }

    private int getTotalPage(int pageSize, int totalSize) {
        int totalPage = totalSize / pageSize;
        if (totalSize % pageSize != 0)
            totalPage++;
        return totalPage;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotalSize() {
        return totalSize;
    }

    public void setTotalSize(int totalSize) {
        this.totalSize = totalSize;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
        this.middle = type / 2;
    }

    public int getMiddle() {
        return middle;
    }


}
