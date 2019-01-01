package com.lanzhu.mywork.message.data.entity;

import com.lanzhu.mywork.master.commons.ToString;
import com.lanzhu.mywork.message.common.enums.TemplateStatus;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;


/**
 * 短信模本实体类
 * @author lanzhu259X
 * @date 2019-01-01
 */
@Setter
@Getter
public class MobileTemplateInfo extends ToString{
    private static final long serialVersionUID = 968350715289849411L;

    private Integer id;

    private String template;

    private TemplateStatus status;

    private String templateDesc;

    private String content;

    private Date createTime;

    private Date updateTime;

}