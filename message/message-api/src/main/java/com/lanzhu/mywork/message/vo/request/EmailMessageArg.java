package com.lanzhu.mywork.message.vo.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

/**
 * description:
 *
 * @author lanzhu259X
 * @date 2019-01-01
 */
@Setter
@Getter
public class EmailMessageArg extends BaseMessageArg {
    private static final long serialVersionUID = 9137202174335842154L;

    /**
     * 收件的邮箱地址
     */
    @NotNull
    private String email;
}
