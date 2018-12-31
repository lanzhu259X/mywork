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
public class MobileMessageArg extends BaseMessageArg {

    /**
     * 手机号码
     */
    @NotNull
    private String mobile;
}
