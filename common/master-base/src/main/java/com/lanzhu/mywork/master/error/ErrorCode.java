package com.lanzhu.mywork.master.error;

import org.apache.commons.lang3.StringUtils;

/**
 * description:
 *  定义所有微服务的错误码值
 *  <h4>错误码格式标准</h4>
 *  <p>
 *      1. {微服务端口号后三位]}{从001开始递增[三位]} 例如，微服务Gateway端口号为8001, 则Gateway的错误码：001001, 001002等
 *  </p>
 *  <p>
 *      2. ErrorCode 的name, 每个微服务定义两个字符为前缀，后接具体内容 例如 Gateway微服务，
 *         定义GW为开头字母, 则Gateway的错误码枚举定义为GW_PARAM_ERROR
 *  </p>
 * @author lanzhu259X
 * @date 2018-09-20
 */
public enum ErrorCode implements BaseErrorCode {

    // SYS COMMON 000 SY
    SUCCESS("000000", "正常"),
    SYS_ERROR("000001", "系统异常"),
    SYS_COMMON("000002", "业务异常错误"),
    SYS_PARAMS_VALID("000002", "参数错误"),
    ;

    /** 码值 */
    private String code;
    /** 描述信息 */
    private String message;

    ErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public static ErrorCode findByCode(String code) {
        ErrorCode[] errorCodes = ErrorCode.values();
        for (ErrorCode errorCode : errorCodes) {
            if (StringUtils.equals(code, errorCode.code)) {
                return errorCode;
            }
        }
        return null;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}