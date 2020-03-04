package com.michael.member.feign;

import com.michael.weixin.service.VerificaCodeService;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @description:
 * @author: Michael
 * @date: 2020/2/29 23:19
 */
@FeignClient("app-michael-weixin")
public interface VerificaCodeServiceFeign extends VerificaCodeService {
}