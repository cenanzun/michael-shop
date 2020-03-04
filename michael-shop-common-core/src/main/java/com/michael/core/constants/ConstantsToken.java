package com.michael.core.constants;

/**
 * @description: token常量
 * @author: Michael
 * @date: 2020/3/2 20:46
 */
public interface ConstantsToken {

    // token
    String MEMBER_TOKEN_KEYPREFIX = "michael.member.login";

    // 安卓的登陆类型
    String MEMBER_LOGIN_TYPE_ANDROID = "Android";

    // IOS的登陆类型
    String MEMBER_LOGIN_TYPE_IOS = "IOS";

    // PC的登陆类型
    String MEMBER_LOGIN_TYPE_PC = "PC";

    // 登陆超时时间 有效期 90天
    Long MEMBRE_LOGIN_TOKEN_TIME = 77776000L;

}