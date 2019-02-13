package com.lanzhu.mywork.master.constant;

/**
 * description:
 *
 * @author lanzhu259X
 * @date 2018-09-22
 */
public final class Constant {

    /** 语言 KEY */
    public static final String LANG = "lang";
    /** 终端类型 KEY */
    public static final String CLIENT_TYPE = "clientType";

    /** 灰度环境头 */
    public static final String GRAY_ENV_HEADER = "X-GRAY-ENV";
    /** 系统环境标签 */
    public static final String ENV_TAG = "envTag";
    /** 环境灰度发布的标签KEY */
    public static final String ENV_TAG_GRAY = "gray";
    /** 通用的环境标签值 */
    public static final String ENV_TAG_NORMAL = "normal";

    /** 用户ID */
    public static final String HEAD_USER_ID = "X-USER-ID";
    /** 请求跟踪链 */
    public static final String HEAD_TRACKING_CHAIN = "X-TRACKING-CHAIN";


    public static final String API_REQUEST_HEADER = "api_request_header";
    public static final String API_SYS_HEADER = "sys_header";



    /** 用户状态值：使用的是long类型的64位二进制占位符，0-false 1-true
     *  1：用户是否激活
     *  2：用户是否禁用
     *  3：用户是否锁定
     *  4：手机是否绑定
     *  5：邮箱是否绑定
     *  6：微信是否绑定
     *  7: 谷歌身份认证绑定
     *
     *  20：用户是否已删除
     */
    public static final long USER_ACTIVE = 1L; // 激活
    public static final long USER_DISABLED = 2L << 0; // 禁用
    public static final long USER_LOCK = 2L << 1; // 锁定
    public static final long USER_MOBILE = 2L << 2; // 已绑定手机
    public static final long USER_EMAIL = 2L << 3; // 邮箱绑定
    public static final long USER_WEIXIN = 2L << 4; // 微信绑定
    public static final long USER_GOOGLE = 2L << 5; // 谷歌身份认证绑定
    public static final long USER_DELETE = 2L << 18; // 用户已删除




}
