package cn.baizhi.test;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.serializer.StringRedisSerializer;

public class TestRedisTemplate extends BaseTest{
    @Autowired
    private RedisTemplate<Object,Object> redisTemplate;
    @Test
    public void testKey(){
        //阻止key进行序列化
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        System.out.println(redisTemplate.keys("*"));
        ValueOperations<Object, Object> valueOperations = redisTemplate.opsForValue();
    }
}
