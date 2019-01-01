package com.lanzhu.mywork.message.data.mapper;

import com.lanzhu.mywork.master.common.annotations.DefaultDB;
import com.lanzhu.mywork.message.data.entity.MobileTemplateInfo;

@DefaultDB
public interface MobileTemplateInfoMapper {

    int insert(MobileTemplateInfo record);

    MobileTemplateInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(MobileTemplateInfo record);

    MobileTemplateInfo getByTemplate(String template);

}