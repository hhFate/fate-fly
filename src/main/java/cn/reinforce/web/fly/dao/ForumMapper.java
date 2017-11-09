package cn.reinforce.web.fly.dao;

import cn.reinforce.web.fly.model.Forum;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ForumMapper {
    int delete(int fid);

    int save(Forum forum);

    Forum find(int fid);

    int updateByPrimaryKeySelective(Forum forum);

    int update(Forum forum);

    List<Forum> searchByParent(int parentId);

    /**
     * 搜索根节点
     * @return
     */
    public List<Forum> searchRoot();

    public List<Forum> searchChildPoint();

    /**
     * 检查某版块下的子版块是否重名
     * @param forumName
     * @param parentFid
     * @return
     */
    public List<Forum> checkForumName(@Param("forumName") String forumName, @Param("parentFid") int parentFid);
}