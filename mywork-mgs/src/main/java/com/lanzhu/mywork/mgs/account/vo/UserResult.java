package com.lanzhu.mywork.mgs.account.vo;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Setter
@Getter
public class UserResult implements Serializable {

    private static final long serialVersionUID = 3237695629285279801L;

    private Date createTime;

    private Date updateTime;

    private String displayName;

    private String avatarUrl;

    private String inviteCode;

    private String inviteUserId;

}
