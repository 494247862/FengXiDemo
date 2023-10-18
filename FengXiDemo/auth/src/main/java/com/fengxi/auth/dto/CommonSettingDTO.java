package com.fengxi.auth.dto;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * 配置文件数据DTO
 */
@Component
@Data
public class CommonSettingDTO {

    /**
     * 登录存储到redis的key名称
     */
    @Value("${common.redisUserKey}")
    private String redisUserKey;

    /**
     * 是否开启swagger
     */
    @Value("${common.swagger.enabled}")
    private Boolean swaggerEnabled;

    /**
     * token密钥
     */
    @Value("${common.token.key}")
    private String tokenKey;

    /**
     * 是否单点登录
     */
    @Value("${common.token.single}")
    private Boolean tokenSingle;

    /**
     * 分钟 过期时间
     */
    @Value("${common.token.timeout}")
    private Integer tokenTimeOut;

    /**
     * 不用鉴权的url，注意逗号
     */
    @Value("${common.token.notAuthUrls}")
    private String notAuthUrls;

    /**
     * 保存多少天的日志
     */
    @Value("${common.logSaveDay}")
    private Long logSaveDay;

    /**
     * webstocket频道名，用于解决分布式难题
     */
    @Value("${common.redisWebStocketChanne}")
    private String redisWebStocketChanne;
}
