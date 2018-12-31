package com.lanzhu.mywork.message.common.enums;

import org.apache.commons.lang3.StringUtils;

/**
 * description:
 * 手机短信消息模本
 * @author lanzhu259X
 * @date 2018-12-31
 */
public enum MobileTemplate {

    REGISTER_VERIFY_CODE("register_verify_code", "手机注册验证码消息模本"),
    ;

    private String code;
    private String message;

    MobileTemplate(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public static MobileTemplate findByName(String name) {
        if (StringUtils.isBlank(name)) {
            return null;
        }
        for (MobileTemplate template : MobileTemplate.values()) {
            if (StringUtils.equalsIgnoreCase(template.name(), name)) {
                return template;
            }
        }
        return null;
    }

    public static MobileTemplate findByCode(String code) {
        if (StringUtils.isBlank(code)) {
            return null;
        }
        for (MobileTemplate template : MobileTemplate.values()) {
            if (StringUtils.equalsIgnoreCase(template.getCode(), code)) {
                return template;
            }
        }
        return null;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
