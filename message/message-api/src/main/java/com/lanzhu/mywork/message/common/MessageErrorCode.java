package com.lanzhu.mywork.message.common;

import com.lanzhu.mywork.master.error.BaseErrorCode;
import org.apache.commons.lang3.StringUtils;

/**
 * description: Message 服务的错误码
 * 注意：code 值以002开头
 * @author lanzhu259X
 * @date 2018-12-31
 */
public enum MessageErrorCode implements BaseErrorCode {

    TEMPLATE_NOT_FOUND("002001", "消息模本不存在"),
    ;

    private String code;
    private String message;

    MessageErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public static MessageErrorCode findByCode(String code) {
        if (StringUtils.isBlank(code)) {
            return null;
        }
        for (MessageErrorCode errorCode : MessageErrorCode.values()) {
            if (StringUtils.equalsIgnoreCase(code, errorCode.getCode())) {
                return errorCode;
            }
        }
        return null;
    }


    @Override
    public String getCode() {
        return null;
    }

    @Override
    public String getMessage() {
        return null;
    }
}
