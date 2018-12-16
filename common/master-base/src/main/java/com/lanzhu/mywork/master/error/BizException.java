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
    private ErrorCode errorCode;

    /** 附带内容 */
    private Object data;

    public BizException() {
        super(ErrorCode.SYS_COMMON.getMessage());
        this.data = null;
        this.errorCode = ErrorCode.SYS_COMMON;
    }

    public BizException(String message) {
        super(message);
        this.data = message;
        this.errorCode = ErrorCode.SYS_COMMON;
    }

    public BizException(ErrorCode errorCode) {
        super(errorCode.getCode() + ":" + errorCode.getMessage());
        this.data = null;
        this.errorCode = errorCode;
    }

    public BizException(ErrorCode errorCode, Object data) {
        super(errorCode.getCode() + ":" + errorCode.getMessage());
        this.errorCode = errorCode;
        this.data = data;
    }

    @Override
    public String toString() {
        return String.format("error: %d, message: %s", this.getErrorCode().getCode(), this.getErrorCode().getMessage());
    }
}
