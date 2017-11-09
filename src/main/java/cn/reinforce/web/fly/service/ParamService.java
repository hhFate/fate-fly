package cn.reinforce.web.fly.service;

import cn.reinforce.web.fly.model.Param;

public interface ParamService {

	void save(Param param);
	
	Param update(Param param);
	
	Param findByKey(String key);

	Param findByKeyNotNull(String key);
	
	/**
	 * 快速更新配置信息
	 * @param key
	 * @param value
	 * @param type
	 */
	Param updateParam(String key, Object value, int type);
}
