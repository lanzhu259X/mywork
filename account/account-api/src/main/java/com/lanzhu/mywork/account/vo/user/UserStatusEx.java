package com.lanzhu.mywork.account.vo.user;

import com.lanzhu.mywork.master.commons.ToString;
import com.lanzhu.mywork.master.constant.Constant;
import com.lanzhu.mywork.master.utils.BitUtils;
import lombok.Getter;

/**
 * description:
 *
 * @author lanzhu259X
 * @date 2018-12-30
 */
@Getter
public class UserStatusEx extends ToString {

    private static final long serialVersionUID = 1065311213221692946L;

    /**
     * 是否激活
     */
    private boolean isUserActive;

    /**
     * 是否禁用
     */
    private boolean isUserDisabled;

    /**
     * 用户是否被锁定
     */
    private boolean isUserLock;

    /**
     * 是否绑定手机
     */
    private boolean isUserBindMobile;

    /**
     * 是否绑定邮箱
     */
    private boolean isUserBindEmail;

    /**
     * 是否绑定微信
     */
    private boolean isUserBindWeiXin;

    /**
     * 是否绑定谷歌身份验证器
     */
    private boolean isUserBindGoogle;

    /**
     * 用户是否已被删除
     */
    private boolean isUserDelete;

    public UserStatusEx(long status) {
        super();
        this.isUserActive = BitUtils.isTrue(status, Constant.USER_ACTIVE);
        this.isUserDisabled = BitUtils.isTrue(status, Constant.USER_DISABLED);
        this.isUserLock = BitUtils.isTrue(status, Constant.USER_LOCK);
        this.isUserBindMobile = BitUtils.isTrue(status, Constant.USER_MOBILE);
        this.isUserBindEmail = BitUtils.isTrue(status, Constant.USER_EMAIL);
        this.isUserBindWeiXin = BitUtils.isTrue(status, Constant.USER_WEIXIN);
        this.isUserBindGoogle = BitUtils.isTrue(status, Constant.USER_GOOGLE);
        this.isUserDelete = BitUtils.isTrue(status, Constant.USER_DELETE);
    }

}
