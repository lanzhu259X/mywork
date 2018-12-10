package com.lanzhu.mywork.master.error;

import lombok.Getter;
import lombok.Setter;

/**
 * description:
 *
 * @author lanzhu259X
 * @date 2018-09-21
 */
@Setter
@Getter
public class BizException extends RuntimeException {
    private static final long serialVersionUID = -2831665763961609441L;

    /** 错误码 */
    private GeneralCode generalCode;

    /** 附带内容 */
    private Object data;

    public BizException() {
        super(GeneralCode.SYS_COMMON.getMessage());
        this.data = null;
        this.generalCode = GeneralCode.SYS_COMMON;
    }

    public BizException(String message) {
        super(message);
        this.data = message;
        this.generalCode = GeneralCode.SYS_COMMON;
    }

    public BizException(GeneralCode generalCode) {
        super(generalCode.getCode() + ":" + generalCode.getMessage());
        this.data = null;
        this.generalCode = generalCode;
    }

    public BizException(GeneralCode generalCode, Object data) {
        super(generalCode.getCode() + ":" + generalCode.getMessage());
        this.generalCode = generalCode;
        this.data = data;
    }

    @Override
    public String toString() {
        return String.format("error: %d, message: %s", this.getGeneralCode().getCode(), this.getGeneralCode().getMessage());
    }
}
