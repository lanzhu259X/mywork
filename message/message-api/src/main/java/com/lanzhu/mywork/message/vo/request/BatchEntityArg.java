package com.lanzhu.mywork.message.vo.request;

import com.lanzhu.mywork.master.commons.ToString;
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
public class BatchEntityArg extends ToString {

    /**
     * 消息地址
     */
    @NotNull
    private String address;

    /**
     * 用户ID
     */
    private Long userId;
}
