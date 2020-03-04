package com.michael.member.entity;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "m_user")
@Data
public class UserDo {

    /**
     * 用户id
     */
    @Id
    @GenericGenerator(name = "idGenerator", strategy = "uuid")
    @GeneratedValue(generator = "idGenerator")
    @Column(name = "USER_ID")
    private String userId;
    /**
     * 手机号码
     */
    @Column(name = "MOBILE")
    private String mobile;
    /**
     * 邮箱
     */
    @Column(name = "EMAIL")
    private String email;
    /**
     * 密码
     */
    @Column(name = "PASSWORD")
    private String password;
    /**
     * 用户名称
     */
    @Column(name = "USER_NAME")
    private String userName;
    /**
     * 性别 0 男 1女
     */
    @Column(name = "SEX")
    private String sex;
    /**
     * 年龄
     */
    @Column(name = "AGE")
    private Integer age;
    /**
     * 注册时间
     */
    @Column(name = "CREATE_TIME")
    private Date createTime;
    /**
     * 修改时间
     */
    @Column(name = "UPDATE_TIME")
    private Date updateTime;
    /**
     * 账号是否可以用 1 正常 0冻结
     */
    @Column(name = "IS_AVALIBLE")
    private String isAvalible;
    /**
     * 用户头像
     */
    @Column(name = "PIC_IMG")
    private String picImg;
    /**
     * 用户关联 QQ 开放ID
     */
    @Column(name = "QQ_OPENID")
    private String qqOpenid;
    /**
     * 用户关联 微信 开放ID
     */
    @Column(name = "WX_OPENID")
    private String wxOpenid;
}
