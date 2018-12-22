package com.lanzhu.mywork.account.vo.user;

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
public class UserVo extends ToString {
    private static final long serialVersionUID = 995305104764483054L;

    private Long userId;

    private String displayName;

    private Date createTime;

    private Date updateTime;


}
