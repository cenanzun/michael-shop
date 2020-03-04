package com.michael.fegin;

import com.michael.member.service.MemberLoginService;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @description:
 * @author: Michael
 * @date: 2020/2/29 22:50
 */

@FeignClient("app-michael-member")
public interface MemberLoginServiceFeign extends MemberLoginService {

}