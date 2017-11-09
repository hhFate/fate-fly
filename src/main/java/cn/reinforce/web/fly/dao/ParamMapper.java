package cn.reinforce.web.fly.dao;

import cn.reinforce.web.fly.model.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ParamMapper {
    int delete(int id);

    int save(Param param);

    int insertSelective(Param param);

    Param find(int id);

    int updateByPrimaryKeySelective(Param param);

    int update(Param param);

    Param findByKey(String key);
}