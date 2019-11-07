package com.brianxia.redis.redenvelope.controller;

import com.brianxia.redis.redenvelope.pojo.RedEnvelope;
import com.brianxia.redis.redenvelope.service.RedEnvelopeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/re")
public class RedEnvelopeController {

    @Autowired
    private RedEnvelopeServiceImpl redEnvelopeService;

    @RequestMapping(value = "/add",method = RequestMethod.GET)
    public Map addRedEnvelope(Integer amount,Integer person){

        String s = redEnvelopeService.addRedEnvelope(amount, person);

        Map<String,Object> map = new HashMap<>();
        map.put("id",s);
        return map;
    }

    @RequestMapping(value = "/get",method = RequestMethod.GET)
    public RedEnvelope getRedEnvelope(String id){

        return redEnvelopeService.getRedEnvelope(id);
    }

}
