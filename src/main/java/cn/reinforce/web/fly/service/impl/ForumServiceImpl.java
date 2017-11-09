package cn.reinforce.web.fly.service.impl;

import cn.reinforce.web.fly.dao.ForumMapper;
import cn.reinforce.web.fly.model.Forum;
import cn.reinforce.web.fly.service.ForumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ForumServiceImpl implements ForumService {

	@Autowired
	private ForumMapper forumMapper;
	
	@Override
	public List<Forum> searchRoot() {
		return forumMapper.searchRoot();
	}

	@Override
	public void save(Forum forum) {
		forumMapper.save(forum);
	}

	@Override
	public Forum update(Forum forum) {
		forumMapper.update(forum);
		return forumMapper.find(forum.getFid());
	}

	@Override
	public void delete(int fid) {
		forumMapper.delete(fid);
	}

	@Override
	public Forum find(int id) {
		return forumMapper.find(id);
	}

	@Override
	public boolean checkForumName(String forumName, int parentFid) {
		return !forumMapper.checkForumName(forumName, parentFid).isEmpty();
	}

	@Override
	public List<Forum> searchChildPoint() {
		return forumMapper.searchChildPoint();
	}

}
