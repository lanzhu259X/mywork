package com.lanzhu.mywork.message.vo.request;

import com.lanzhu.mywork.master.commons.ToString;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.Map;

/**
 * description:
 *
 * @author lanzhu259X
 * @date 2018-12-31
 */
@Setter
@Getter
public class BaseMessageArg extends ToString {

    @NotNull
    private String template;

    private Map<String, String> params;

    private Long userId;

}
