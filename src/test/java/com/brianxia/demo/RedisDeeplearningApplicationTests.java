package com.brianxia.demo;

import com.brianxia.redis.RedisDeeplearningApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.security.RunAs;

@SpringBootTest(classes = RedisDeeplearningApplication.class)
class RedisDeeplearningApplicationTests {

    @Autowired
    RedisTemplate<String,String> redisTemplate;
    @Test
    void contextLoads() {
        redisTemplate.opsForValue().set("test","123");
        System.out.println(redisTemplate.opsForValue().get("test"));
        redisTemplate.delete("test");
        System.out.println(redisTemplate.opsForValue().get("test"));
    }

}
