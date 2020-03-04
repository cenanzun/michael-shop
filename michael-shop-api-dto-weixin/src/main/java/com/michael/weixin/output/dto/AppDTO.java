package com.michael.weixin.output.dto;

import lombok.Data;

/**
 * @description:
 * @author: Michael
 * @date: 2020/3/1 11:55
 */
@Data
public class AppDTO {

    /**
     * appid
     */
    private String appId;
    /**
     * 应用名称
     */
    private String appName;

    public AppDTO() {

    }

    public AppDTO(String appId, String appName) {
        super();
        this.appId = appId;
        this.appName = appName;
    }

}