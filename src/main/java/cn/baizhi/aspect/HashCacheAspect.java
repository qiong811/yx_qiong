package cn.baizhi.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class HashCacheAspect {
    @Autowired
    private RedisTemplate redisTemplate;
    @Around("@annotation(cn.baizhi.annotation.AroundAspect)")
    public Object aspect(ProceedingJoinPoint proceedingJoinPoint){
        System.out.println("进入环绕通知");
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        StringBuilder sb=new StringBuilder();
        String className = proceedingJoinPoint.getTarget().getClass().getName();
        Signature methodName = proceedingJoinPoint.getSignature();
        sb.append(className).append(methodName);
        Object[] args = proceedingJoinPoint.getArgs();
        for (Object arg : args) {
            sb.append(arg);
        }
        HashOperations hashOperations = redisTemplate.opsForHash();
        Object obj = null;
        if(hashOperations.hasKey(className, sb.toString())){
            obj= hashOperations.get(className, sb.toString());
        }else {
            try {
                obj = proceedingJoinPoint.proceed();
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
            hashOperations.put(className, sb.toString(), obj);
        }

        return obj;
    }
    @After("@annotation(cn.baizhi.annotation.DeleteCaChe)")
    public void delCache(JoinPoint joinPoint){
        System.out.println("进入后置通知");
        String name = joinPoint.getTarget().getClass().getName();
        System.out.println(name);
        redisTemplate.delete(name);
    }
}
