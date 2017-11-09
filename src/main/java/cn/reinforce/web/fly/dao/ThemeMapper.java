package cn.reinforce.web.fly.dao;

import cn.reinforce.web.fly.model.Theme;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ThemeMapper {
    int delete(int id);

    int save(Theme theme);

    Theme find(int id);

    int updateByPrimaryKeySelective(Theme theme);

    int update(Theme theme);

    List<Theme> findAll(boolean isDelete);

    /**
     * 批量逻辑删除
     *
     * @param ids
     */
    void multiDelete(List<Integer> ids);

    /**
     * 根据版块和状态分页
     * 带排序
     * @param fid
     * @param state
     * @param timeOrder
     * @param priority
     * @param isDelete
     * @param pageNum
     * @param pageSize
     * @return
     */
    List<Theme> pageByFid(@Param("fid") int fid, @Param("state") int state, @Param("timeOrder") boolean timeOrder, @Param("priority") boolean priority, @Param("isDelete") boolean isDelete, @Param("start") int start, @Param("pageSize") int pageSize);

    long countByFid(@Param("fid") int fid, @Param("state") int state, @Param("isDelete") boolean isDelete);

    /**
     * 查询用户所属的文章
     * @param uid
     * @param order
     * @param pageNum
     * @param pageSize
     * @return
     */
    List<Theme> pageByUid(@Param("uid") String uid, @Param("order") boolean order, @Param("start") int start, @Param("pageSize") int pageSize);

    long countByUid(@Param("uid") String uid);

    List<Theme> pageByTag(@Param("tag") String tag, @Param("start") int start, @Param("pageSize") int pageSize);

    long countByTag(@Param("tag") String tag);

    /**
     * 统计某版块的被浏览次数
     * @param fid
     * @param state
     * @return
     */
    long countViews(@Param("fid") int fid, @Param("state") int state);

    /**
     * 后台统计用
     * @param fid
     * @param dateType
     * @param day
     * @return
     */
    long statistics(@Param("fid") int fid, @Param("dateType") int dateType, @Param("day") String day);

    Theme findByDateAndTitle(@Param("date") String date, @Param("title") String title);

    List<Theme> pageHot(@Param("start") int start, @Param("pageSize") int pageSize);

    List<Theme> pageSearchHot(@Param("start") int start, @Param("pageSize") int pageSize);

    Theme findLastTheme(@Param("fid") int fid);
}