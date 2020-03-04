package com.michael.member.service;

import com.alibaba.fastjson.JSONObject;
import com.michael.core.base.BaseResponse;
import com.michael.member.input.dto.UserLoginInpDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @description: 登录服务
 * @author: Michael
 * @date: 2020/3/2 14:24
 */
@Api(tags = "用户登录服务接口")
public interface MemberLoginService {

    @PostMapping("/login")
    @ApiOperation(value = "会员用户登录信息接口")
    BaseResponse<JSONObject> login(@RequestBody UserLoginInpDTO userLoginInpDTO);

}