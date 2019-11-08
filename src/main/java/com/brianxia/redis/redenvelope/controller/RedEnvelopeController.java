package com.brianxia.redis.redenvelope.controller;

import com.brianxia.redis.redenvelope.pojo.RedEnvelope;
import com.brianxia.redis.redenvelope.service.RedEnvelopeServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@Api(description = "红包接口")
@RestController
@RequestMapping("/re")
public class RedEnvelopeController {

    @Autowired
    private RedEnvelopeServiceImpl redEnvelopeService;

    @ApiOperation("新增红包接口")
    @RequestMapping(value = "/add",method = RequestMethod.GET)
    public Map addRedEnvelope(@ApiParam(name = "amount",value="红包金额，如100元为10000") @RequestParam Integer amount,
                              @ApiParam(name = "person",value="红包个数") @RequestParam Integer person){

        String s = redEnvelopeService.addRedEnvelope(amount, person);

        Map<String,Object> map = new HashMap<>();
        map.put("id",s);
        return map;
    }

    @ApiOperation("获取红包接口")
    @RequestMapping(value = "/get",method = RequestMethod.GET)
    public RedEnvelope getRedEnvelope(@ApiParam(name = "id",value="红包ID") @RequestParam String id){

        return redEnvelopeService.getRedEnvelope(id);
    }

    @ApiOperation("自动获取红包接口")
    @RequestMapping(value = "/getAll",method = RequestMethod.GET)
    public List<RedEnvelope> getAll(@ApiParam(name = "id",value="红包ID") @RequestParam String id, @ApiParam(name = "count",value="获取红包数") @RequestParam Integer count){

        Date start = new Date();
        List<RedEnvelope> res = new ArrayList<>();
        for(int i = 0;i < count;i++){
            res.add(redEnvelopeService.getRedEnvelope(id));
//            try {
//                Thread.sleep(50L);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
        }
        Date end = new Date();
        System.out.println("结束红包获取，总耗时:" + (end.getTime() - start.getTime()) + "ms");
        return res;
    }
}
