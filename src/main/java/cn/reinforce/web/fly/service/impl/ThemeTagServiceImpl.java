package cn.reinforce.web.fly.service.impl;

import cn.reinforce.web.fly.dao.ThemeTagMapper;
import cn.reinforce.web.fly.model.ThemeTag;
import cn.reinforce.web.fly.service.ThemeTagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ThemeTagServiceImpl implements ThemeTagService {

    @Autowired
    private ThemeTagMapper themeTagMapper;

    @Override
    public ThemeTag findByNameAndUser(String tagName, String uid) {
        return themeTagMapper.findByNameAndUser(tagName, uid);
    }

    @Override
    public List<ThemeTag> pageByDateAndUser(String uid, int pageNum, int pageSize) {
        return themeTagMapper.pageByDateAndUser(uid, pageNum, pageSize);
    }

    @Override
    public void save(ThemeTag themeTag) {
        themeTagMapper.save(themeTag);
    }

    @Override
    public boolean exits(String tagName, String uid) {
        return themeTagMapper.findByNameAndUser(tagName, uid) == null ? false : true;
    }

    @Override
    public ThemeTag update(ThemeTag themeTag) {
        themeTagMapper.update(themeTag);
        return themeTagMapper.find(themeTag.getId());
    }

    @Override
    public List<ThemeTag> random(int pageSize) {
        return themeTagMapper.random(pageSize);
    }
}
