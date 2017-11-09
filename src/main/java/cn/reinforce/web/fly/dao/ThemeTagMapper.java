package cn.reinforce.web.fly.dao;

import cn.reinforce.web.fly.model.ThemeTag;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ThemeTagMapper {
    int delete(int id);

    int save(ThemeTag themeTag);

    ThemeTag find(int id);

    int updateByPrimaryKeySelective(ThemeTag themeTag);

    int update(ThemeTag record);

    /**
     * 查找某用户下的标签
     * @param tagName
     * @param uid
     * @return
     */
    public ThemeTag findByNameAndUser(@Param("tagName") String tagName, @Param("uid") String uid);

    /**
     *
     * @param uid
     * @param start
     * @param pageSize
     * @return
     */
    public List<ThemeTag> pageByDateAndUser(@Param("uid") String uid, @Param("start") int start, @Param("pageSize") int pageSize);

    public List<ThemeTag> random(int pageSize);
}