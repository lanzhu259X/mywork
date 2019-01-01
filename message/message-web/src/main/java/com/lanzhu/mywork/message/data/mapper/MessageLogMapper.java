package com.lanzhu.mywork.message.data.mapper;

import com.lanzhu.mywork.master.common.annotations.DefaultDB;
import com.lanzhu.mywork.message.data.entity.MessageLog;
import org.apache.ibatis.annotations.Param;

@DefaultDB
public interface MessageLogMapper {

    int insert(MessageLog record);

    MessageLog selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(MessageLog record);

    /**
     * 变更日志的是否成功标识
     * @param id
     * @param b
     * @return
     */
    int updateIsSuccess(@Param("id") Long id, @Param("isSuccess") boolean b);
}