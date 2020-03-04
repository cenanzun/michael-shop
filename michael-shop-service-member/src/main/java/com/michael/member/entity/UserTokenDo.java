package com.michael.member.entity;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "m_user_token")
@Data
public class UserTokenDo {
    /**
     * id
     */
    @Id
    @GenericGenerator(name = "idGenerator", strategy = "uuid")
    @GeneratedValue(generator = "idGenerator")
    @Column(name = "id")
    private String id;
    /**
     * 用户token
     */
    @Column(name = "token")
    private String token;
    /**
     * 登陆类型
     */
    @Column(name = "login_type")
    private String loginType;

    /**
     * 设备信息
     */
    @Column(name = "device_info")
    private String deviceInfo;
    /**
     * 是否可用 1可用 2不可用
     */
    @Column(name = "is_availability")
    private String isAvailability;
    /**
     * 用户userId
     */
    @Column(name = "user_id")
    private String userId;
}