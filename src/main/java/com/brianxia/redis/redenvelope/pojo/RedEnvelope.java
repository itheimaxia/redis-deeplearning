package com.brianxia.redis.redenvelope.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@ApiModel
public class RedEnvelope {
    @ApiModelProperty("红包ID")
    private String id;
    @ApiModelProperty("红包金额")
    private Integer amount;
}
