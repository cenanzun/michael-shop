package com.michael.weixin.feign;

import com.michael.member.service.MemberService;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @description:
 * @author: Michael
 * @date: 2020/2/29 22:50
 */

@FeignClient("app-michael-member")
public interface MemberServiceFeign extends MemberService {

}