package com.brianxia.redis.redenvelope.service;

import com.brianxia.redis.redenvelope.pojo.RedEnvelope;
import com.brianxia.redis.utils.SnowFlakeIdUtil;
import com.brianxia.redis.utils.SplitRedPacket;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class RedEnvelopeServiceImpl implements RedEnvelopeService{

    private final String REDIS_RE_COUNT = "RE:COUNT:";
    private final String REDIS_RE_AMOUNT = "RE:AMOUNT:";
    private final String REDIS_RE_LOCK = "RE:LOCK:";

    @Value("${server.port}")
    private String port;

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    //添加红包
    public String addRedEnvelope(int money,int person){
        SnowFlakeIdUtil sfu = new SnowFlakeIdUtil();
        String id =  String.valueOf(sfu.nextId());
        List list = SplitRedPacket.splitRedPackets(money, person);
        redisTemplate.opsForValue().set(REDIS_RE_COUNT + id,String.valueOf(person));
        redisTemplate.opsForList().leftPushAll(REDIS_RE_AMOUNT + id,list);
        return id;
    }

    @Override
    public RedEnvelope getRedEnvelope(String id) {

        if(StringUtils.isEmpty(id)){
            return null;
        }
        RedEnvelope re = new RedEnvelope();

        String lockkey = REDIS_RE_LOCK + id;
        //设置一下lock，如果已经有锁，会返回false
        Boolean aBoolean = redisTemplate.opsForValue().setIfAbsent(lockkey, port, 10, TimeUnit.SECONDS);
        if(aBoolean){
            //成功获得锁
            System.out.println(port+"获得了锁");
            Date start = new Date();
            try{
                //获取红包
                String countkey = REDIS_RE_COUNT + id;
                String amoutkey = REDIS_RE_AMOUNT + id;
                String s = redisTemplate.opsForValue().get(countkey);
                Integer count = Integer.valueOf(s);
                if(count > 0){
                    redisTemplate.opsForValue().decrement(countkey);
                    String s1 = redisTemplate.opsForList().rightPop(amoutkey);
                    re.setId(id);
                    re.setAmount(Integer.parseInt(s1));
                }
            }catch (Exception e){
                e.printStackTrace();
            }finally {
                String lockport = redisTemplate.opsForValue().get(lockkey);
                if(port.equals(lockport)){
                    redisTemplate.delete(lockkey);
                    Date end = new Date();
                    System.out.println(port+"释放了锁，本次锁持有时间:"
                            + (end.getTime()-start.getTime()) + "ms");
                }else{
                    System.out.println(port+"这把锁不是我的，我不会去释放它");
                }
            }

        }else{
            String lockport = redisTemplate.opsForValue().get(lockkey);
            System.out.println("获取锁失败,持有者："+lockport);
        }

        return re;
    }


}
