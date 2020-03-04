package com.michael.weixin.mp.handler;

import com.michael.core.base.BaseResponse;
import com.michael.core.constants.Constants;
import com.michael.core.utils.RegexUtils;
import com.michael.member.output.dto.UserOutDTO;
import com.michael.weixin.feign.MemberServiceFeign;
import com.michael.weixin.mp.builder.TextBuilder;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.TimeUnit;

@Component
public class MsgHandler extends AbstractHandler {

	/**
	 * 发送验证码消息
	 */
	@Value("${michael.weixin.registration.code.message}")
	private String registrationCodeMessage;
	/**
	 * 默认回复消息
	 */
	@Value("${michael.weixin.default.registration.code.message}")
	private String defaultRegistrationCodeMessage;

	@Autowired
	private MemberServiceFeign memberServiceFeign;

	@Autowired
	private StringRedisTemplate stringRedisTemplate;

	@Override
	public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage, Map<String, Object> context, WxMpService weixinService,
									WxSessionManager sessionManager) {

		if (!wxMessage.getMsgType().equals(WxConsts.XmlMsgType.EVENT)) {
			// TODO 可以选择将消息保存到本地
		}

		// 当用户输入关键词如“你好”，“客服”等，并且有客服在线时，把消息转发给在线客服
		try {
			if (StringUtils.startsWithAny(wxMessage.getContent(), "你好", "客服")
					&& weixinService.getKefuService().kfOnlineList().getKfOnlineList().size() > 0) {
				return WxMpXmlOutMessage.TRANSFER_CUSTOMER_SERVICE().fromUser(wxMessage.getToUser())
						.toUser(wxMessage.getFromUser()).build();
			}
		} catch (WxErrorException e) {
			e.printStackTrace();
		}

		String fromContent = wxMessage.getContent();

		if(RegexUtils.checkMobile(fromContent)) {
			//查询用户是否存在
			BaseResponse<UserOutDTO> userEntityBaseResponse = memberServiceFeign.existMobile(fromContent);

			if(userEntityBaseResponse.getRtnCode().equals(Constants.HTTP_RES_CODE_200)) {
				//用户已经存在
				return new TextBuilder().build("该手机号码已存在!", wxMessage, weixinService);
			}

			//用户不存在
			if(!userEntityBaseResponse.getRtnCode().equals(Constants.HTTP_RES_CODE_EXISTMOBILE_203)) {
				//其他错误信息直接返回错误信息
				return new TextBuilder().build(userEntityBaseResponse.getMsg(), wxMessage, weixinService);
			}

			int code = registCode();

			//将注册码存入redis中 并 设置过期时间
			String key = Constants.WEIXINCODE_KEY + fromContent;
			stringRedisTemplate.opsForValue().set(key, code+"");
			stringRedisTemplate.expire(key, Constants.WEIXINCODE_TIMEOUT, TimeUnit.SECONDS);

			String content = registrationCodeMessage.format(registrationCodeMessage, code);
			return new TextBuilder().build(content, wxMessage, weixinService);
		}
		return new TextBuilder().build(defaultRegistrationCodeMessage, wxMessage, weixinService);
	}

	private int registCode() {
		int registcode = (int)(Math.random() * 9000 + 1000);
		return registcode;
	}

}