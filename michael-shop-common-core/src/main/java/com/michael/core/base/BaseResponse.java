package com.michael.core.base;

import lombok.Data;

@Data
public class BaseResponse<T> {

    //返回码
    private Integer rtnCode;
    //返回消息
    private String msg;
    //返回数据
    private T data;

    public BaseResponse() {

    }

    public BaseResponse(Integer rtnCode, String msg, T data) {
        super();
        this.rtnCode = rtnCode;
        this.msg = msg;
        this.data = data;
    }

}