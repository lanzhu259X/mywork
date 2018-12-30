package com.lanzhu.mywork.account.data.entity;

import com.lanzhu.mywork.master.commons.ToString;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * 用户认证信息
 * @author lanzhu259X
 * @date 2018-12-31
 */
@Setter
@Getter
public class UserAuthenticate extends ToString {
    private static final long serialVersionUID = 1989658632937533729L;

    private Long id;

    private Long userId;

    private String authType;

    private String identifier;

    private Boolean enabled;

    private Date createTime;

    private Date updateTime;


}