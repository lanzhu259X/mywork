package com.lanzhu.mywork.account.vo.user.request;

import com.lanzhu.mywork.account.common.enums.UserAuthType;
import com.lanzhu.mywork.master.commons.ToString;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

/**
 * description:
 *
 * @author lanzhu259X
 * @date 2018-12-31
 */
@Setter
@Getter
public class RegisterArg extends ToString {

    private static final long serialVersionUID = 841116202577681850L;

    /**
     * 认证类型
     */
    @NotNull
    private UserAuthType userAuthType;

    /**
     * 身份标识
     */
    @NotNull
    private String identifier;

    /**
     * 验证码
     */
    private String verifyCode;

    /**
     * 密码
     */
    private String password;

    /**
     * 邀请码
     */
    private String inviteCode;



}
