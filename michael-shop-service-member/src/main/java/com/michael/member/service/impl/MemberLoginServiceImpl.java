package com.michael.member.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.michael.core.base.BaseApiService;
import com.michael.core.base.BaseResponse;
import com.michael.core.constants.ConstantsToken;
import com.michael.core.token.GenerateToken;
import com.michael.core.utils.MD5Util;
import com.michael.member.entity.UserDo;
import com.michael.member.entity.UserTokenDo;
import com.michael.member.input.dto.UserLoginInpDTO;
import com.michael.member.repository.UserRepository;
import com.michael.member.repository.UserTokenRepository;
import com.michael.member.service.MemberLoginService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @description:
 * @author: Michael
 * @date: 2020/3/2 14:36
 */

@RestController
public class MemberLoginServiceImpl extends BaseApiService<JSONObject> implements MemberLoginService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserTokenRepository userTokenRepository;

    @Autowired
    private StringRedisTemplate redisTemplate;

    public BaseResponse<JSONObject> login(@RequestBody UserLoginInpDTO userLoginInpDTO) {
        /**
         * 逻辑:
         * 1.校验参数
         * 2.对密码进行加密
         * 3.根据手机号和密码得到用户信息
         *  3.1校验用户信息
         * 4.根据用户信息得到用户id
         * 5.根据用户id和登录类型查询可用的用户token信息
         * ins0.初始化membertransaction
         * ins1.定义token前缀常量
         *  5.1如果存在可用的用户token信息
         *      5.1.2将当前用户token信息的可用属性设置为2(不可用)
         *      5.1.2判断redis中是否存在对应的token,如果有将redis中的对应的token删除
         * 6.向redis中设置新的token
         * 7.调用工具类获取用户token令牌
         * 8.向用户token表中插入当前登录信息
         * 9.封装结果集并返回
         */

        //1.验证参数
        String mobile = userLoginInpDTO.getMobile();
        if (StringUtils.isEmpty(mobile)) {
            return setResultError("手机号码不能为空");
        }
        String password = userLoginInpDTO.getPassword();
        if (StringUtils.isEmpty(password)) {
            return setResultError("密码不能为空");
        }
        String loginType = userLoginInpDTO.getLoginType();
        if (StringUtils.isEmpty(loginType)) {
            return setResultError("登陆类型不能为空!");
        }
        if (!(loginType.equals(ConstantsToken.MEMBER_LOGIN_TYPE_ANDROID) || loginType.equals(ConstantsToken.MEMBER_LOGIN_TYPE_IOS)
                || loginType.equals(ConstantsToken.MEMBER_LOGIN_TYPE_PC))) {
            return setResultError("登陆类型出现错误!");
        }
        String deviceInfor = userLoginInpDTO.getDeviceInfo();
        if (StringUtils.isEmpty(deviceInfor)) {
            return setResultError("设备信息不能为空!");
        }

        //2.对登录密码实现加密
        String password_md5 = MD5Util.MD5(password);

        //3.根据手机号和密码得到用户信息
        List<UserDo> userDos = userRepository.findByMobileAndPassword(mobile, password_md5);
        //3.1校验用户信息
        if (userDos.size() == 0) {
            return setResultError("用户名或密码错误!");
        }
        UserDo userDo = userDos.get(0);

        //4.根据用户信息得到用户id
        String userId = userDo.getUserId();

        //5.根据用户id和登录类型查询可用的用户token信息
        List<UserTokenDo> userToken_available
                = userTokenRepository.findByUserIdAndLoginTypeAndIsAvailability(userId, loginType, "1");

        try {
            //ins0.初始化membertransaction

            //ins1.定义token前缀常量
            final String tokenPrefix = ConstantsToken.MEMBER_TOKEN_KEYPREFIX + loginType;
            ;

            //5.1如果存在可用的用户token信息
            if (userToken_available.size() > 0) {
                UserTokenDo userTokenDo = userToken_available.get(0);
                //5.1.2将当前用户token信息的可用属性设置为2(不可用)
                userTokenDo.setIsAvailability("2");
                userTokenRepository.save(userTokenDo);
                //5.1.2判断redis中是否存在对应的token,如果有将redis中的对应的token删除
                String redisTokenVal = redisTemplate.boundValueOps(tokenPrefix).get();
                if (StringUtils.isNotEmpty(redisTokenVal)) {
                    redisTemplate.delete(tokenPrefix);
                }
            }

            //6.向redis中设置新的token
            redisTemplate.boundValueOps(tokenPrefix).set(userId);

            //7.调用工具类获取用户token令牌
            String userToken = GenerateToken.createToken(tokenPrefix);

            //8.向用户token表中插入当前登录信息
            UserTokenDo userTokenDo = new UserTokenDo();
            userTokenDo.setIsAvailability("1");
            userTokenDo.setUserId(userDo.getUserId());
            userTokenDo.setDeviceInfo(deviceInfor);
            userTokenDo.setLoginType(loginType);
            userTokenDo.setToken(userToken);
            userTokenRepository.save(userTokenDo);

            //9.封装结果集并返回
            JSONObject data = new JSONObject();
            data.put("token", userToken);
            return setResultSuccess(data);
        } catch (Exception e) {
            return setResultError("系统错误!");
        }
    }

}