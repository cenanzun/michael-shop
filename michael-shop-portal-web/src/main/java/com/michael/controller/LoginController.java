package com.michael.controller;

import com.alibaba.fastjson.JSONObject;
import com.michael.core.base.BaseResponse;
import com.michael.member.input.dto.UserLoginInpDTO;
import com.michael.member.service.MemberLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class LoginController {
    private static final String MEMBER_LOGIN_PAGE = "member/login";

    @Autowired
    private MemberLoginService memberLoginService;

    /**
     * 跳转到注册页面
     *
     * @return
     */
    @GetMapping("/login")
    public String getLogin() {
        return MEMBER_LOGIN_PAGE;
    }

    /**
     * 跳转到注册页面
     *
     * @return
     */
    @PostMapping("/login")
    public String postLogin(@RequestBody UserLoginInpDTO userLoginInpDTO) {

        return "login";
    }

}
