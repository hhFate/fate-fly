package cn.reinforce.web.fly.dao;

import cn.reinforce.plugin.util.SerializeUtil;
import cn.reinforce.plugin.util.Tools;
import cn.reinforce.web.fly.model.Theme;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

@Repository
public class ThemeRedisDao {

    private static final Logger LOG = Logger.getLogger(ThemeRedisDao.class);

    private static final String TAG = "fly.theme.";

    @Autowired
    protected RedisTemplate<String, Theme> redisTemplate;

    /**
     * redis获取数据
     *
     * @param id
     * @return
     */
    public Theme redisThemeFetch(int id) {
        return redisTemplate.execute(connection -> {
            RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
            byte[] key = serializer.serialize(TAG + id + ":id");
            byte[] value = connection.get(key);
            if (value == null) {
                return null;
            }
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(value);
            Theme t = null;
            try {
                ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
                t = (Theme) objectInputStream.readObject();
            } catch (Exception e) {
                LOG.error(e);
            }
            return t;
        }, false, false);
    }

    /**
     * 插入redis
     *
     * @param theme
     */
    public void redisThemeUpdate(Theme theme) {
        boolean result = redisTemplate.execute(connection -> {
            RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
            byte[] key = serializer.serialize(TAG + theme.getId() + ":id");
            connection.set(key, SerializeUtil.serialize(theme));
            return true;
        }, false, false);
    }

    /**
     * 删除主题缓存
     *
     * @param themeId
     */
    public void redisThemeDelete(int themeId) {
        redisTemplate.execute(connection -> {
            RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
            byte[] key = serializer.serialize(TAG + themeId);
            connection.del(key);
            return false;
        }, false, false);
    }

    public boolean updateList(String k, List<Theme> list) {
        Assert.notEmpty(list);
        return redisTemplate.execute(connection -> {
            RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
            byte[] key = serializer.serialize("themeList." + k);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ObjectOutputStream objectOutputStream;
            try {
                objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
                objectOutputStream.writeObject(list);
                connection.set(key, byteArrayOutputStream.toByteArray());
                return true;
            } catch (IOException e) {
                LOG.error(e);
            }
            return true;
        }, false, true);
    }

    /**
     * 查询主题的guid
     *
     * @param k
     * @return
     */
    public int redisGuidFetch(String k) {
        return redisTemplate.execute(connection -> {
            RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
            byte[] key = serializer.serialize(TAG + k);
            byte[] value = connection.get(key);
            if (value == null) {
                return 0;
            }
            return Tools.parseInt(serializer.deserialize(value));
        }, false, false);
    }

    /**
     * 将主题的id存入redis
     *
     * @param k
     * @param id
     */
    public void redisGuidUpdate(String k, int id) {
        redisTemplate.execute(connection -> {
            RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
            byte[] key = serializer.serialize(TAG + k);
            byte[] name = serializer.serialize("" + id);
            connection.set(key, name);
            return true;
        }, false, false);
    }
}
