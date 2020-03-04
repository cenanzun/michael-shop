package com.michael.member.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.michael.core.base.BaseApiService;
import com.michael.core.base.BaseResponse;
import com.michael.core.constants.Constants;
import com.michael.core.utils.MD5Util;
import com.michael.member.feign.VerificaCodeServiceFeign;
import com.michael.member.input.dto.UserInpDTO;
import com.michael.member.mapper.UserMapper;
import com.michael.member.entity.UserDo;
import com.michael.member.repository.UserRepository;
import com.michael.member.service.MemberRegisterService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * @description:
 * @author: Michael
 * @date: 2020/2/29 18:57
 */
@RestController
public class MemberRegisterServiceImpl extends BaseApiService<JSONObject> implements MemberRegisterService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private VerificaCodeServiceFeign verificaCodeServiceFeign;

    @Override
    @Transactional
    public BaseResponse<JSONObject> register(@RequestBody UserInpDTO userInpDTO, String registCode) {
        System.out.println(userInpDTO);
        //1.参数验证
        String userName = userInpDTO.getUserName();
        if(StringUtils.isEmpty(userName)) {
            return setResultError("用户名称不能为空");
        }
        String mobile = userInpDTO.getMobile();
        if(StringUtils.isEmpty(mobile)) {
            return setResultError("手机号码不能为空");
        }
        String password = userInpDTO.getPassword();
        if(StringUtils.isEmpty(password)) {
            return setResultError("密码不能为空");
        }
        //2.验证注册码是否正确 (pluse)
        BaseResponse<JSONObject> jsonObjectBaseResponse = verificaCodeServiceFeign.verificaWeixinCode(mobile, registCode);
        if(!jsonObjectBaseResponse.getRtnCode().equals(Constants.HTTP_RES_CODE_200)) {
            return setResultError(jsonObjectBaseResponse.getMsg());
        }

        //3.对用户的密码进行加密
        String passwordMD5 = MD5Util.MD5(password);
        userInpDTO.setPassword(passwordMD5);

        UserDo userDo = new UserDo();
        Date date = new Date();
        //设置初始化值
        userDo.setCreateTime(date);
        userDo.setUpdateTime(date);
        userDo.setIsAvalible("1");
        BeanUtils.copyProperties(userInpDTO,userDo);

        UserDo save = userRepository.save(userDo);

        System.out.println(save);

        //4.调用数据库插入数据
        return save != null ? setResultSuccess("注册成功!") : setResultError("注册失败");
    }

}