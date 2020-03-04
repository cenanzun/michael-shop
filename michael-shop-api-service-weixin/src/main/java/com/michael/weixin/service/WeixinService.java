package com.michael.weixin.service;

import com.michael.core.base.BaseResponse;
import com.michael.weixin.output.dto.AppDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @description: 微信服务接口
 * @author: Michael
 * @date: 2020/2/26 22:27
 */
@Api(tags = "微信服务接口")
public interface WeixinService {

    @ApiOperation(value = "微信服务获取用户信息")
    @GetMapping("/getApp")
    public BaseResponse<AppDTO> getApp();


}
