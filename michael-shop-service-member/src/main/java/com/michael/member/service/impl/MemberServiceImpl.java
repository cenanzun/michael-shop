package com.michael.member.service.impl;

import com.michael.core.base.BaseApiService;
import com.michael.core.base.BaseResponse;
import com.michael.core.constants.Constants;
import com.michael.member.entity.UserDo;
import com.michael.member.feign.WeiXinServiceFeign;
import com.michael.member.output.dto.UserOutDTO;
import com.michael.member.repository.UserRepository;
import com.michael.member.service.MemberService;
import com.michael.weixin.output.dto.AppDTO;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @description:
 * @author: Michael
 * @date: 2020/2/26 23:31
 */
@RestController
public class MemberServiceImpl extends BaseApiService<UserOutDTO> implements MemberService {

    @Autowired
    private WeiXinServiceFeign weiXinServiceFeign;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StringRedisTemplate redisTemplate;

    public BaseResponse<AppDTO> memberToWeiXin() {
        return weiXinServiceFeign.getApp();
    }

    @Override
    public List<UserOutDTO> findAll() {
        List<UserDo> all = userRepository.findAll();
        List<UserOutDTO> list = new ArrayList<>();
        UserOutDTO userOutDTO = new UserOutDTO();
        for (int i = 0; i < all.size(); i++) {
            BeanUtils.copyProperties(all.get(i), userOutDTO);
            list.add(userOutDTO);
        }
        return list;
    }


    public BaseResponse<UserOutDTO> existMobile(String mobile) {
        if (StringUtils.isEmpty(mobile)) {
            return setResultError("手机号码不能为空!");
        }

        List<UserDo> list = userRepository.findByMobile(mobile);
        if (list.size() <= 0) {
            return setResultError(Constants.HTTP_RES_CODE_EXISTMOBILE_203, "用户不存在");
        }

        UserDo userDo = list.get(0);
        UserOutDTO userOutDTO = new UserOutDTO();

        BeanUtils.copyProperties(userDo, userOutDTO);
        if (userOutDTO == null) {
            return setResultError(Constants.HTTP_RES_CODE_EXISTMOBILE_203, "用户不存在");
        }
        return setResultSuccess(userOutDTO);
    }

    @Override
    public BaseResponse<UserOutDTO> getInfo(String token) {

        //0.校验token
        if (StringUtils.isEmpty(token)) {
            return setResultError("token不能为空!");
        }

        //1.在redis数据库中根据token查询到userid
        String redisUserId = redisTemplate.boundValueOps(token).get();

        //2.校验userid
        if (StringUtils.isEmpty(redisUserId)) {
            return setResultError("token过期或token不存在");
        }

        //3.在mysql数据库根据userid查询到userDo
        List<UserDo> list = userRepository.findByUserId(redisUserId);
        if (list.size() <= 0) {
            return setResultError("用户不存在");
        }

        //4.校验userdo
        UserDo userDo = list.get(0);
        if (userDo == null) {
            return setResultError("用户不存在");
        }

        //5.将userdo转化为userOutDTO
        UserOutDTO userOutDTO = new UserOutDTO();
        BeanUtils.copyProperties(userDo, userOutDTO);

        //6.返回结果
        return setResultSuccess(userOutDTO);
    }

}