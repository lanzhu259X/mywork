package com.lanzhu.mywork.message.data.mapper;

import com.lanzhu.mywork.master.common.annotations.DefaultDB;
import com.lanzhu.mywork.message.data.entity.EmailTemplateInfo;

@DefaultDB
public interface EmailTemplateInfoMapper {

    int insert(EmailTemplateInfo record);

    EmailTemplateInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(EmailTemplateInfo record);

    EmailTemplateInfo getByTemplate(String template);

}