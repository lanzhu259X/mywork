package com.lanzhu.mywork.account.data.mapper;

import com.lanzhu.mywork.account.data.entity.UserAuthenticate;
import com.lanzhu.mywork.master.common.annotations.DefaultDB;

@DefaultDB
public interface UserAuthenticateMapper {

    int deleteByPrimaryKey(Long id);

    int insert(UserAuthenticate record);

    UserAuthenticate selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(UserAuthenticate record);

}