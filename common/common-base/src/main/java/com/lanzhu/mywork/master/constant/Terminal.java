package com.lanzhu.mywork.master.constant;

import org.apache.commons.lang3.StringUtils;

/**
 * description:
 *  system allow Terminal
 * @author lanzhu259X
 * @date 2018-09-14
 */
public enum Terminal {

    WEB("web", "web"),
    /** 当移动端无法区分是 ios 还是 android 时 */
    MOBILE("mobile", "mobile"),
    IOS("ios", "ios"),
    ANDROID("android", "android"),
    PC("pc", "pc"),
    H5("h5", "h5"),
    MAC("mac", "mac"),
    OTHER("other", "other");

    private String code;
    private String desc;

    Terminal(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static Terminal findByCode(String code) {
        if (StringUtils.isBlank(code)) {
            return null;
        }
        Terminal[] terminals = Terminal.values();
        for (Terminal terminal : terminals) {
            if (StringUtils.equalsIgnoreCase(terminal.getCode(), code)) {
                return terminal;
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
