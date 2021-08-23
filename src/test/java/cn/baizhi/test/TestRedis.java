package cn.baizhi.test;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.util.Set;



public class TestRedis extends BaseTest{
    @Autowired
    private RedisTemplate redisTemplate;
//    @Autowired
//    private StringRedisTemplate stringRedisTemplate;
    @Test
    public void test(){
        System.out.println(redisTemplate);
       redisTemplate.setKeySerializer(new StringRedisSerializer());
        ValueOperations valueOperations = redisTemplate.opsForValue();
        System.out.println(redisTemplate.keys("*"));
//        String name = (String)valueOperations.get("name");
//        System.out.println(name);
//        System.out.println("====");

        Set keys = redisTemplate.keys("*");
        for (Object key : keys) {
            System.out.println(key);
        }
    }
}
