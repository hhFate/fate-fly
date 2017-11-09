package cn.reinforce.web.fly.service;

import cn.reinforce.plugin.echarts.Pie;
import cn.reinforce.web.fly.model.http.FateRequest;
import org.apache.log4j.Logger;

import java.util.List;
import java.util.Map;

public interface FateRequestService {

	FateRequest find(String requestId);
	
	void save(FateRequest fateRequest);
	
	
	FateRequest update(FateRequest fateRequest);

	/**
	 * 直接更新request记录
	 * @param requestId
	 * @param result
	 * @param status
	 * @param error
	 * @param associateId
	 * @param type
	 * @return
	 */
	FateRequest update(String requestId, String result, int status, boolean error, String associateId, int type);

	FateRequest update(String requestId, String result, int status, boolean error);
	/**
	 * 返回错误码
	 * @param map
	 * @param errorCode
	 * @param msg
	 * @param requestId
	 * @param log
	 */
	void returnError(Map<String, Object> map, int errorCode, String msg, String requestId, int status, Logger log);


	void returnError(Map<String, Object> map, int errorCode, String showMsg, String logMsg, String requestId, int status, Logger log);

	void returnSuccess(Map<String, Object> map, String msg, String requestId);
	
	/**
	 * 搜索对应url的日志
	 * @param url
	 * @param error
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	List<FateRequest> page(String name, String startTime, String endTime, String url, boolean error, int pageNum, int pageSize);
	
	long count(String name, String startTime, String endTime, String url, boolean error);
	
	/**
	 * 上面的反集
	 * @param url
	 * @param error
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	List<FateRequest> searchNot(String name, String startTime, String endTime, String url, boolean error, int pageNum, int pageSize);
	
	long countNot(String name, String startTime, String endTime, String url, boolean error);
	
	/**
	 * 统计用户登录时间段
	 * @return
	 */
	Pie statisticsLoginTime();

	void save(int status, String url, String referer, String source, String agent, String ip);

    /**
     * 搜索异常记录（状态码>=400）
     * @param pageNum
     * @param pageSize
     * @return
     */
    List<FateRequest> searchError(int pageNum, int pageSize);

    long countError();

	void push(FateRequest request);

	FateRequest pop();

	long size();

	/**
	 * 搜索地域为空的请求
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	List<FateRequest> searchAreaBlank(int pageNum, int pageSize);

	void updateArea(String ip, String area, String operator, String requestTime);
}
