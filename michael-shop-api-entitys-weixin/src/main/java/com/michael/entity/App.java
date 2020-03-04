package com.michael.entity;

import lombok.Data;

/**
 * @description:
 * @author: Michael
 * @date: 2020/2/26 22:22
 */
@Data
public class App {

    /**
     * appid
     */
    private String appId;
    /**
     * 应用名称
     */
    private String appName;

    public App() {

    }

    public App(String appId, String appName) {
        super();
        this.appId = appId;
        this.appName = appName;
    }

}