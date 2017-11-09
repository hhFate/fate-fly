package cn.reinforce.web.fly.service;

import cn.reinforce.web.fly.model.Theme;
import cn.reinforce.web.fly.model.User;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface ThemeService {

    Theme find(int id, boolean redisOpen);

    void save(Theme theme);

    void crush(int id, boolean redisOpen);

    List<Theme> findAll(boolean isDelete);

    Theme update(Theme theme, boolean redisOpen);

    List<Theme> pageByFid(int fid, int state, boolean timeOrder, boolean priority, boolean isDelete, int pageNum, int pageSize);

    /**
     * 按版块统计总数
     *
     * @param fid
     * @param state
     * @param isDelete
     * @return
     */
    long countByFid(int fid, int state, boolean isDelete);


    /**
     * 获取版块的最新主题
     *
     * @param fid
     * @return
     */
    Theme getLastestTheme(int fid);


    /**
     * 获取某个类型的文章的统计数据
     *
     * @param fid      文章类型
     * @param datetype 时间类型，是按天还是按月
     * @param day      日期的字符串格式
     * @return
     */
    long statistics(int fid, int datetype, String day);

    Theme findByDateAndTitle(String date, String title, boolean redisOpen);

    /**
     * 分页获取最热主题
     *
     * @param pageNum
     * @param pageSize
     * @return
     */
    List<Theme> pageHot(int pageNum, int pageSize);

    /**
     * 分页获取搜索最多的主题
     *
     * @param pageNum
     * @param pageSize
     * @return
     */
    List<Theme> pageSearchHot(int pageNum, int pageSize);

    /**
     * 统计站点主题浏览次数
     *
     * @param fid
     * @param state
     * @return
     */
    long countViews(int fid, int state);

    List<Theme> pageByUid(String uid, boolean order, int pageNum, int pageSize);

    long countByUid(String uid);

    List<Theme> pageByTag(String tag, int pageNum, int pageSize);

    long countByTag(String tag);

    /**
     * 批量逻辑删除
     *
     * @param guids
     */
    void multiDelete(List<Integer> guids);

    /**
     * 获取版块的最新一篇文章
     *
     * @param fid
     * @return
     */
    Theme findLastTheme(int fid);

    String getImg(String content);

    String replaceImg(String content);

    String dealTags(String src, User user);

    String checkImgs(String content, HttpServletRequest request);
}
