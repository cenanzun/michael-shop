package com.michael.member.service;

import com.michael.core.base.BaseResponse;
import com.michael.member.output.dto.UserOutDTO;
import com.michael.weixin.output.dto.AppDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @description: 会员服务接口
 * @author: Michael
 * @date: 2020/2/26 23:25
 */

@Api(tags = "会员服务接口")
public interface MemberService {

    @ApiOperation(value = "会员服务调用微信服务")
    @GetMapping("/memberToWeiXin")
    public BaseResponse<AppDTO> memberToWeiXin();

    @ApiOperation(value = "查询所有用户")
    @GetMapping("/findAll")
    public List<UserOutDTO> findAll();


    @ApiOperation(value = "根据手机号码查询是否已经存在")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "mobile", dataType = "String", required = true, value = "用户手机号码"),})
    @PostMapping("/existMobile")
    BaseResponse<UserOutDTO> existMobile(@RequestParam("mobile") String mobile);

    @ApiOperation(value = "根据token查询用户信息")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "token", dataType = "String", required = true, value = "用户token"),})
    @GetMapping("/getUserInfo")
    BaseResponse<UserOutDTO> getInfo(@RequestParam("token") String token);

}