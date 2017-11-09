package cn.reinforce.web.fly.service.impl;

import cn.reinforce.web.fly.dao.ParamMapper;
import cn.reinforce.web.fly.dao.ParamRedisDao;
import cn.reinforce.web.fly.model.Param;
import cn.reinforce.web.fly.service.ParamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ParamServiceImpl implements ParamService {

	@Autowired
	private ParamMapper paramMapper;
	
	@Autowired
	private ParamRedisDao paramRedisDao;
	
	@Override
	public void save(Param param) {
		paramMapper.save(param);
	}

	@Override
	public Param update(Param param) {
		paramRedisDao.redisParamDelete(param);
		if(param.getId()==0){
			save(param);
		}else {
			paramMapper.update(param);
		}
		return param;
	}

	@Override
	public Param findByKey(String key) {
		Param param = paramRedisDao.redisParamFetch(key);
		if(param==null){
			param = paramMapper.findByKey(key);
			if(param!=null){
				paramRedisDao.redisParamUpdate(param);
			}
		}
		return param;
	}

	@Override
	public Param findByKeyNotNull(String key) {
		Param param = findByKey(key);
		if (param == null) {
			param = new Param();
		}
		return param;
	}

	@Override
	public Param updateParam(String key, Object value, int type){
		Param pobj = paramMapper.findByKey(key);
		if(pobj==null){
			pobj = new Param();
			pobj.setKey(key);
			pobj.setType(type);
		}
		if(type==Param.TYPE_TEXT){
			pobj.setTextValue((String) value);
		}else{
			pobj.setIntValue((int) value);
		}
		return update(pobj);
	}
}
