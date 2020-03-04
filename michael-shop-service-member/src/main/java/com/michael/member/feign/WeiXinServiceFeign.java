package com.michael.member.feign;

import com.michael.weixin.service.WeixinService;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @description:
 * @author: Michael
 * @date: 2020/2/26 23:42
 */
@FeignClient("app-michael-weixin")
public interface WeiXinServiceFeign extends WeixinService {

}