package com.michael.core.base;

import lombok.Data;

import java.util.Date;


@Data
public class BaseDo {
    /**
     * 注册时间
     */
    private Date createTime;
    /**
     * 修改时间
     */
    private Date updateTime;
    /**
     * id
     */
    private String id;

    /**
     * 是否可用 1可用 2不可用
     */
    private String isAvailability;

}