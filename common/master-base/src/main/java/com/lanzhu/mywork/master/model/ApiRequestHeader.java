package com.lanzhu.mywork.master.model;

import com.lanzhu.mywork.master.commons.ToString;
import com.lanzhu.mywork.master.constant.Language;
import com.lanzhu.mywork.master.constant.Terminal;
import lombok.Getter;
import lombok.Setter;

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

    /** 登录后设置 */
    private Long userId;

    /** 跟踪链 */
    private String trackingChain;

}
