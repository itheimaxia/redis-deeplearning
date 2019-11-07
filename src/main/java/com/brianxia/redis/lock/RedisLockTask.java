package com.brianxia.redis.lock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scripting.support.ResourceScriptSource;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

//@Service
public class RedisLockTask {

    private static String REDIS_LOCK_KEY = "lock:key:1";

    @Value("${server.port}")
    private String port;

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @Scheduled(cron = "0/5 * * * * ?")
    public void lock(){
        try {
            lock3();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void lock1() throws InterruptedException {
        //设置一下lock，如果已经有锁，会返回false
        Boolean aBoolean = redisTemplate.opsForValue().setIfAbsent(REDIS_LOCK_KEY, port, 20, TimeUnit.SECONDS);
        if(aBoolean){
            //成功获得锁
            System.out.println(port+"获得了锁");
            Thread.sleep(5000);
            redisTemplate.delete(REDIS_LOCK_KEY);
            System.out.println(port+"释放了锁");
        }else{
            String lockport = redisTemplate.opsForValue().get(REDIS_LOCK_KEY);
            System.out.println("获取锁失败,持有者："+lockport);
        }
    }

    private void lock2() throws InterruptedException {
        DefaultRedisScript<Boolean> booleanDefaultRedisScript = new DefaultRedisScript<>();
        booleanDefaultRedisScript.setScriptSource(new ResourceScriptSource(new ClassPathResource("lock.lua")));
        booleanDefaultRedisScript.setResultType(Boolean.class);
        List<String> param = new ArrayList<>();
        param.add(REDIS_LOCK_KEY);
        param.add(port);

        Boolean execute = redisTemplate.execute(booleanDefaultRedisScript, param);
        if(execute){
            //成功获得锁
            System.out.println(port+"获得了锁");
            Thread.sleep(5000);
            redisTemplate.delete(REDIS_LOCK_KEY);
            System.out.println(port+"释放了锁");
        }else{
            String lockport = redisTemplate.opsForValue().get(REDIS_LOCK_KEY);
            System.out.println("获取锁失败,持有者："+lockport);
        }
    }


    private void lock3() throws InterruptedException {
        //设置一下lock，如果已经有锁，会返回false
        Boolean aBoolean = redisTemplate.opsForValue().setIfAbsent(REDIS_LOCK_KEY, port, 15, TimeUnit.SECONDS);
        if(aBoolean){
            //成功获得锁
            System.out.println(port+"获得了锁");
            Thread.sleep(20000);
            String lockport = redisTemplate.opsForValue().get(REDIS_LOCK_KEY);
            if(port.equals(lockport)){
                redisTemplate.delete(REDIS_LOCK_KEY);
                System.out.println(port+"释放了锁");
            }else{
                System.out.println(port+"这把锁不是我的，我不会去释放它");
            }
        }else{
            String lockport = redisTemplate.opsForValue().get(REDIS_LOCK_KEY);
            System.out.println("获取锁失败,持有者："+lockport);
        }
    }
}
