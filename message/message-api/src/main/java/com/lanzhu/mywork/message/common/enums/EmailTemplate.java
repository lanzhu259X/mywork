package com.lanzhu.mywork.message.common.enums;

import org.apache.commons.lang3.StringUtils;

/**
 * description:
 *  邮件消息模本
 * @author lanzhu259X
 * @date 2018-12-31
 */
public enum EmailTemplate {

    REGISTER_VERIFY_CODE("register_verify_code", "邮箱注册验证码消息模本"),
    ;

    private String code;
    private String message;

    EmailTemplate(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public static EmailTemplate findByName(String name) {
        if (StringUtils.isBlank(name)) {
            return null;
        }
        for (EmailTemplate template : EmailTemplate.values()) {
            if (StringUtils.equalsIgnoreCase(template.name(), name)) {
                return template;
            }
        }
        return null;
    }

    public static EmailTemplate findByCode(String code) {
        if (StringUtils.isBlank(code)) {
            return null;
        }
        for (EmailTemplate template : EmailTemplate.values()) {
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
