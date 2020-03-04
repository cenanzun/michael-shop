package com.michael.weixin.service.impl;

import com.michael.core.base.BaseApiService;
import com.michael.core.base.BaseResponse;
import com.michael.weixin.output.dto.AppDTO;
import com.michael.weixin.service.WeixinService;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WeixinServiceImpl extends BaseApiService<AppDTO> implements WeixinService {

    public BaseResponse<AppDTO> getApp() {
        return setResultSuccess(new AppDTO("root","123"));
    }

}