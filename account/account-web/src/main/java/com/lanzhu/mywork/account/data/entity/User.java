package com.lanzhu.mywork.account.data.entity;

import com.lanzhu.mywork.account.vo.user.UserStatusEx;
import com.lanzhu.mywork.account.vo.user.request.UserUpdateArg;
import com.lanzhu.mywork.master.common.utils.DateUtils;
import com.lanzhu.mywork.master.commons.ToString;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * description:
 *
 * @author lanzhu259X
 * @date 2018-12-22
 */
@Setter
@Getter
public class User extends ToString {
    private static final long serialVersionUID = -6061509647332272301L;

    private Long userId;

    /**
     * status 使用long 类型的2进制占位标识，可以有64个状态
     * 每种状态统一0/1代表 0-false 1-true
     */
    private Long status;

    private Date createTime;

    private Date updateTime;

    private String displayName;

    private String avatarUrl;

    private String inviteCode;

    private Long inviteUserId;

    private UserStatusEx getUserStatusEx() {
        return new UserStatusEx(this.status);
    }


}
