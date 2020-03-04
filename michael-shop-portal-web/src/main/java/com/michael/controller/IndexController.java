package com.michael.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @description:
 * @author: Michael
 * @date: 2020/3/3 17:11
 */
@Controller
public class IndexController {

    /**
     * 跳转到首页
     *
     * @return
     */
    @RequestMapping("/")
    public String index() {
        return "index";
    }

    /**
     * 跳转到首页
     *
     * @return
     */
    @RequestMapping("/index")
    public String indexHtml() {
        return index();
    }
}
