package com.lanzhu.mywork.message.vo.request;

import com.lanzhu.mywork.master.commons.ToString;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;

/**
 * description:
 *
 * @author lanzhu259X
 * @date 2019-01-01
 */
@Setter
@Getter
public class BatchMessageArg extends ToString {

    /**
     * 信息模板
     */
    @NotNull
    private String template;

    /**
     * 模板参数
     */
    private Map<String, String> params;

    /**
     * 发送地址信息
     */
    @NotEmpty
    private List<BatchEntityArg> batchEntityArgs;
}
