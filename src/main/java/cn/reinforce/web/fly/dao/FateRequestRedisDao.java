package cn.reinforce.web.fly.dao;

import cn.reinforce.web.fly.model.http.FateRequest;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

/**
 * Created by Fate on 2016/7/13.
 */
@Repository
public class FateRequestRedisDao {

    private static final Logger LOG = Logger.getLogger(FateRequestRedisDao.class);

    private static final String TAG = "fly.";

    @Autowired
    protected RedisTemplate<String, FateRequest> redisTemplate;

    public void push(FateRequest request){
        redisTemplate.opsForList().leftPush(TAG + "requestQueue", request);
    }

    public FateRequest pop(){
        return redisTemplate.opsForList().rightPop(TAG + "requestQueue");
    }

    public long size(){
        return redisTemplate.opsForList().size(TAG + "requestQueue");
    }
}
