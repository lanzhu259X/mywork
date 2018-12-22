package com.lanzhu.mywork.account.common;

import com.lanzhu.mywork.master.error.BaseErrorCode;
import org.apache.commons.lang3.StringUtils;

/**
 * description: account 项目的错误码值
 *  注意：code 值以001开头
 * @author lanzhu259X
 * @date 2018-12-22
 */
public enum AccountErrorCode implements BaseErrorCode {
    USER_NOT_FOUND("001001", "用户不存在"),
    ;

    private String code;
    private String message;

    AccountErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public static AccountErrorCode findByCode(String code) {
        if (StringUtils.isBlank(code)) {
            return null;
        }
        for (AccountErrorCode errorCode : AccountErrorCode.values()) {
            if (StringUtils.equalsIgnoreCase(code, errorCode.getCode())) {
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
