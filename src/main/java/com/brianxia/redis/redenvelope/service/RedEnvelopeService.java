package com.brianxia.redis.redenvelope.service;

import com.brianxia.redis.redenvelope.pojo.RedEnvelope;

public interface RedEnvelopeService {
    public String addRedEnvelope(int amount,int person);
    public RedEnvelope getRedEnvelope(String id);
}
