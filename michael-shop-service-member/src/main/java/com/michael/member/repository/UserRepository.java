package com.michael.member.repository;

import com.michael.member.entity.UserDo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @description:
 * @author: Michael
 * @date: 2020/3/2 0:34
 */
public interface UserRepository extends JpaRepository<UserDo, String> {

    List<UserDo> findByMobile(String mobile);

    List<UserDo> findByMobileAndPassword(String mobile, String password);

    List<UserDo> findByUserId(String userId);
}
