package com.lanzhu.mywork.account.common.vo;

import com.lanzhu.mywork.master.commons.ToString;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * description:
 *
 * @author lanzhu259X
 * @date 2018-12-16
 */
@Setter
@Getter
public class UserVo extends ToString {
    private static final long serialVersionUID = 1709630768040872879L;

    private Long id;

    private String name;

    private Date createTime;

    private Date updateTime;
}
