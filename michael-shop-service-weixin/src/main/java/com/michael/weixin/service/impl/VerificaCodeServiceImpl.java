package com.michael.weixin.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.michael.core.base.BaseApiService;
import com.michael.core.base.BaseResponse;
import com.michael.core.constants.Constants;
import com.michael.weixin.service.VerificaCodeService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description:
 * @author: Michael
 * @date: 2020/2/29 16:57
 */
@RestController
public class VerificaCodeServiceImpl extends BaseApiService<JSONObject> implements VerificaCodeService {

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    public BaseResponse<JSONObject> verificaWeixinCode(String phone, String weixinCode) {
        //判断手机号码非空
        if(StringUtils.isEmpty(phone)) {
            return setResultError("手机号码不能为空");
        }
        //判断哪注册码非空
        if(StringUtils.isEmpty(weixinCode)) {
            return setResultError("注册码不能为空");
        }
        //根据手机号码从redis获得注册码
        //校验注册码是否过期
        String key = Constants.WEIXINCODE_KEY + phone;
        String code = redisTemplate.boundValueOps(key).get();
        if(StringUtils.isEmpty(code)){
            return setResultError("注册码可能已经过期");
        }
        //校验注册码正确性
        if(!weixinCode.equals(code)) {
            return setResultError("注册码不正确");
        }
        //校验电话号码和注册码之后将注册码删除
        redisTemplate.delete(key);
        return setResultSuccess("验证码校验正确");
    }
}