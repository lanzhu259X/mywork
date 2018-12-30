package com.lanzhu.mywork.account.data.mapper;

import com.lanzhu.mywork.account.data.entity.User;
import com.lanzhu.mywork.master.common.annotations.DefaultDB;

@DefaultDB
public interface UserMapper {

    int insert(User record);

    User selectByPrimaryKey(Long userId);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);
}