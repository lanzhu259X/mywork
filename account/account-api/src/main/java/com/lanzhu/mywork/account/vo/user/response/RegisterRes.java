package com.lanzhu.mywork.account.vo.user.response;

import com.lanzhu.mywork.account.common.enums.UserAuthType;
import com.lanzhu.mywork.master.commons.ToString;

/**
 * description:
 *
 * @author lanzhu259X
 * @date 2018-12-31
 */
public class RegisterRes extends ToString {
    private static final long serialVersionUID = 1384242265918092694L;

    /**
     * 令牌
     */
    private String token;

    /**
     * 认证标识
     */
    private String identifier;

    /**
     * 认证类型
     */
    private UserAuthType userAuthType;

}
