package com.lanzhu.mywork.master.constant;

import org.apache.commons.lang3.StringUtils;

/**
 * description:
 *  system language enum
 * @author lanzhu259X
 * @date 2018-09-13
 */
public enum Language {

    EN_US("en", "英语(美国)"),
    CH_ZH("cn", "简体中文(中国)");

    private String code;
    private String desc;

    Language(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    /**
     * find Language by code
     * @param code
     * @return
     */
    public static Language findByCode(String code) {
        if (StringUtils.isBlank(code)) {
            return null;
        }
        Language[] values = Language.values();
        for (Language language : values) {
            if (StringUtils.equals(language.getCode(), code)) {
                return language;
            }
        }
        return null;
    }

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
