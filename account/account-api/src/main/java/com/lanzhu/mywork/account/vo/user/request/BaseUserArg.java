package com.lanzhu.mywork.account.vo.user.request;


import com.lanzhu.mywork.master.commons.ToString;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

/**
 * description:
 *
 * @author lanzhu259X
 * @date 2018-12-23
 */
@Setter
@Getter
public class BaseUserArg extends ToString {
    private static final long serialVersionUID = 729823401065976407L;

    @NotNull
    private Long userId;
}
