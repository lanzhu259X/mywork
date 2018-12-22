package com.lanzhu.mywork.account.vo.user.request;

import com.lanzhu.mywork.master.commons.ToString;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

/**
 * description:
 * 修改显示名的请求参数
 * @author lanzhu259X
 * @date 2018-12-22
 */
@Getter
@Setter
public class UserUpdateArg extends BaseUserArg {

    private String displayName;

}
