package com.michael.member.input.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 用户登录
 */
@Data
@ApiModel(value = "用户登陆")
public class UserLoginInpDTO {
	/**
	 * 手机号码
	 */
	@ApiModelProperty(value = "手机号码")
	private String mobile;
	/**
	 * 密码
	 */
	@ApiModelProperty(value = "密码")
	private String password;

	/**
	 * 登陆类型 PC端 移动端 安卓 IOS 平板
	 */
	@ApiModelProperty(value = "登陆类型")
	private String loginType;

	/**
	 * 设置信息
	 */
	@ApiModelProperty(value = "设备信息")
	private String deviceInfo;


}
