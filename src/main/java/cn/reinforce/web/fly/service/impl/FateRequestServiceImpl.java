package cn.reinforce.web.fly.service.impl;

import cn.reinforce.plugin.echarts.Pie;
import cn.reinforce.web.fly.dao.FateRequestMapper;
import cn.reinforce.web.fly.dao.FateRequestRedisDao;
import cn.reinforce.web.fly.model.Result;
import cn.reinforce.web.fly.model.http.FateRequest;
import cn.reinforce.web.fly.model.http.FateRequestPieView;
import cn.reinforce.web.fly.service.FateRequestService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public class FateRequestServiceImpl implements FateRequestService {

    @Autowired
    private FateRequestMapper fateRequestMapper;

    @Autowired
    private FateRequestRedisDao fateRequestRedisDao;

    @Override
    public FateRequest find(String requestId) {
        return fateRequestMapper.find(requestId);
    }

    @Override
    public void save(FateRequest fateRequest) {
        fateRequestMapper.save(fateRequest);
    }

    @Override
    public FateRequest update(String requestId, String result, int status, boolean error, String associateId, int type) {
        FateRequest fateRequest = fateRequestMapper.find(requestId);
        fateRequest.setResult(result);
        fateRequest.setError(error);
        fateRequest.setStatus(status);
        fateRequest.setAssociateId(associateId);
        fateRequest.setType(type);
        return update(fateRequest);
    }

    @Override
    public FateRequest update(String requestId, String result, int status, boolean error) {
        return update(requestId, result, status, error, null, 0);
    }

    @Override
    public void returnError(Map<String, Object> map, int errorCode, String msg, String requestId, int status, Logger log) {
        returnError(map, errorCode, msg, msg, requestId, status, log);
    }



    @Override
    public void returnError(Map<String, Object> map, int errorCode, String showMsg, String logMsg, String requestId, int status, Logger log) {
//        map.put(Constants.SUCCESS, false);
//        map.put("error_code", errorCode);
//        map.put(Constants.MSG, msg);
        log.info(logMsg);
//        map.put("request_id", requestId);
        Result result = new Result(false, errorCode, showMsg, requestId);
        map.put("result", result);
        update(requestId, logMsg, status, false, null, 0);
    }

    @Override
    public void returnSuccess(Map<String, Object> map, String msg, String requestId) {
//        map.put(Constants.SUCCESS, true);
//        map.put(Constants.MSG, msg);
//        map.put("request_id", requestId);
        Result result = new Result(true, 0, msg, requestId);

        map.put("result", result);
        update(requestId, msg, 200, false, null, 0);
    }

    @Override
    public FateRequest update(FateRequest fateRequest) {
        fateRequestMapper.update(fateRequest);
        return fateRequestMapper.find(fateRequest.getRequestId());
    }

    @Override
    public List<FateRequest> page(String name, String startTime, String endTime, String url, boolean error, int pageNum,
                                    int pageSize) {
		return fateRequestMapper.page(name, startTime, endTime, url, error, (pageNum - 1)*pageSize, pageSize);
    }

    @Override
    public long count(String name, String startTime, String endTime, String url, boolean error) {
		return fateRequestMapper.count(name, startTime, endTime, url, error);
    }

    @Override
    public List<FateRequest> searchNot(String name, String startTime, String endTime, String url, boolean error, int pageNum,
                                       int pageSize) {
		return fateRequestMapper.searchNot(name, startTime, endTime, url, error, (pageNum - 1)*pageSize, pageSize);
    }

    @Override
    public long countNot(String name, String startTime, String endTime, String url, boolean error) {
		return fateRequestMapper.countNot(name, startTime, endTime, url, error);
    }

    @Override
    public Pie statisticsLoginTime() {
		List<FateRequestPieView> views = fateRequestMapper.statisticsLoginTime();
        Pie pie = new Pie();
        pie.setTitle("登录时间分布");
		for(FateRequestPieView view:views){
            pie.getLegend().add(view.getTime());
            pie.newSeries(view.getTime(), (int) view.getCount(), false);
        }
        return pie;
    }

    @Override
    public void push(FateRequest request) {
        fateRequestRedisDao.push(request);
    }

    @Override
    public FateRequest pop() {
        return fateRequestRedisDao.pop();
    }

    @Override
    public long size() {
        return fateRequestRedisDao.size();
    }

    @Override
    public List<FateRequest> searchAreaBlank(int pageNum, int pageSize) {
		return fateRequestMapper.searchAreaBlank((pageNum - 1)*pageSize, pageSize);
    }

    @Override
    public void updateArea(String ip, String area, String operator, String requestTime) {
		fateRequestMapper.updateArea(ip, area, operator, requestTime);
    }

    @Override
    public void save(int status, String url, String referer, String source, String agent, String ip) {
        FateRequest fateRequest = new FateRequest();
        fateRequest.setStatus(status);
        fateRequest.setUrl(url);
        fateRequest.setReferer(referer);
        fateRequest.setSource(source);
        fateRequest.setAgent(agent);
        fateRequest.setIp(ip);
        fateRequestMapper.save(fateRequest);
    }

    @Override
    public List<FateRequest> searchError(int pageNum, int pageSize) {
		return fateRequestMapper.searchError((pageNum - 1)*pageSize, pageSize);
    }

    @Override
    public long countError() {
		return fateRequestMapper.countError();
    }
}
