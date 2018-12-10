package com.lanzhu.mywork.master.models.api;

import com.lanzhu.mywork.master.commons.ToString;
import com.lanzhu.mywork.master.constant.Language;
import com.lanzhu.mywork.master.constant.Terminal;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

/**
 * description:
 *  request API Header
 * @author lanzhu259X
 * @date 2018-09-13
 */
@Setter
@Getter
public class ApiRequestHeader extends ToString {

    private static final long serialVersionUID = 8320238014132508488L;

    /** system language */
    private Language language;

    /** system terminal */
    private Terminal terminal;

    /** system version */
    private String version;

    /** 授权令牌 登录后设置 */
    private String token;

    /** 跟踪链 */
    private String trackingChain;

    /** 域名 */
    private String domain;

    private String userAgent;

    /** 补充参数 */
    private Map<String, String> headMap;


}
