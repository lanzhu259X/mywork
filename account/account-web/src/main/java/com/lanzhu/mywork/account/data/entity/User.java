package com.lanzhu.mywork.account.data.entity;

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

    private String displayName;

    private Date createTime;

    private Date updateTime;

    public void updateByUpdateAgr(UserUpdateArg arg) {
        displayName = arg.getDisplayName();
        updateTime = DateUtils.getNewDate();
    }

}
