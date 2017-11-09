package cn.reinforce.web.fly.dao;

import cn.reinforce.plugin.util.SerializeUtil;
import cn.reinforce.web.fly.model.Param;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Repository;

import java.io.ByteArrayInputStream;

@Repository
public class ParamRedisDao {

    private static final Logger LOG = Logger.getLogger(ParamRedisDao.class);

    private static final String TAG = "fly.param.";

    @Autowired
    protected RedisTemplate<String, Param> redisTemplate;

    /**
     * redis获取数据
     *
     * @param pKey
     * @return
     */
    public Param redisParamFetch(String pKey) {
        return redisTemplate.execute(connection -> {
            RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
            byte[] key = serializer.serialize(TAG + pKey);
            byte[] value = connection.get(key);
            if (value == null) {
                return null;
            }
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(value);
            Param t = (Param) SerializeUtil.unserialize(value);
            return t;
        }, false, false);
    }

    /**
     * 插入redis
     *
     * @param param
     */
    public void redisParamUpdate(Param param) {
        redisTemplate.execute(connection -> {
            RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
            byte[] key = serializer.serialize(TAG + param.getKey());
            connection.set(key, SerializeUtil.serialize(param));
            return true;
        }, false, false);
    }

    /**
     * 删除主题缓存
     *
     * @param param
     */
    public void redisParamDelete(Param param) {
        redisTemplate.execute(connection -> {
            RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
            byte[] key = serializer.serialize(TAG + param.getKey());
            connection.del(key);
            return false;
        }, false, false);
    }
}
