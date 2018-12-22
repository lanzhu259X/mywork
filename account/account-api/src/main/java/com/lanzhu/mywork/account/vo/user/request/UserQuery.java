package com.lanzhu.mywork.account.vo.user.request;

import com.lanzhu.mywork.master.model.PageBase;
import lombok.Getter;
import lombok.Setter;

/**
 * description:
 *
 * @author lanzhu259X
 * @date 2018-12-23
 */
@Setter
@Getter
public class UserQuery extends PageBase {
    private static final long serialVersionUID = -8262931378409019521L;

    private Long userId;

}
