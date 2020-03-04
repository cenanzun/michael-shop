package com.michael.member.repository;

import com.michael.member.entity.UserDo;
import com.michael.member.entity.UserTokenDo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @description:
 * @author: Michael
 * @date: 2020/3/2 0:34
 */
public interface UserTokenRepository extends JpaRepository<UserTokenDo, String> {

    List<UserTokenDo> findByUserIdAndLoginTypeAndIsAvailability(String userId, String loginType, String isAvailability);
}
