package cn.reinforce.web.fly.service;

import cn.reinforce.web.fly.model.Forum;

import java.util.List;

public interface ForumService {

	/**
	 * 搜索根节点
	 * @return
	 */
	public List<Forum> searchRoot();
	
	public void save(Forum forum);
	
	public Forum update(Forum forum);
	
	public void delete(int fid);
	
	public Forum find(int id);
	
	/**
	 * 检查同一父级下版块名称是否存在
	 * @param forumName
	 * @return
	 */
	public boolean checkForumName(String forumName, int parentFid);
	
	/**
	 * 搜索所有子节点
	 * @return
	 */
	public List<Forum> searchChildPoint();
}
