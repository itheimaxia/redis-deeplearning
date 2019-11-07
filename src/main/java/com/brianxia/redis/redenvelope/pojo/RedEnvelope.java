package com.brianxia.redis.redenvelope.pojo;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class RedEnvelope {
    private String id;
    private Integer amount;
    private Integer person;
}
