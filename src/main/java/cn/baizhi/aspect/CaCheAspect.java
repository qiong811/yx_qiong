package cn.baizhi.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.util.Set;

//@Aspect
//@Component
public class CaCheAspect {
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Around("execution(* cn.baizhi.service.*Impl.query*(..))")
    public Object aspect(ProceedingJoinPoint proceedingJoinPoint){
        System.out.println("进入环绕通知");
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        StringBuilder sb=new StringBuilder();
        String className = proceedingJoinPoint.getTarget().getClass().getName();
        Signature methodName = proceedingJoinPoint.getSignature();
        sb.append(className).append(methodName);
        Object[] args = proceedingJoinPoint.getArgs();
        for (Object arg : args) {
            sb.append(arg.toString());
        }
        ValueOperations valueOperations = redisTemplate.opsForValue();
        Object obj = null;
        if(redisTemplate.hasKey(sb.toString())){
            obj= valueOperations.get(sb.toString());
        }else {
            try {
                obj = proceedingJoinPoint.proceed();
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
            valueOperations.set(sb.toString(), obj);
        }

        return obj;
    }
    @After("@annotation(cn.baizhi.annotation.DeleteCaChe)")
    public void delCache(JoinPoint joinPoint){
        System.out.println("进入后置通知");
        String name = joinPoint.getTarget().getClass().getName();
        System.out.println(name);
        Set keys = redisTemplate.keys("*");
        for (Object key : keys) {
            String s = (String) key;
            if(s.startsWith(name)){
                redisTemplate.delete(s);
            }
        }
    }
}
