package com.michael.core.token;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.UUID;

/**
 * @description:
 * @author: Michael
 * @date: 2020/3/1 13:10
 */
public class GenerateToken {
    /**
     * 生成令牌
     *
     * @return 返回token
     */
    public static String createToken(String keyPrefix) {
        String token = keyPrefix + UUID.randomUUID().toString().replace("-", "");
        return token;
    }

}