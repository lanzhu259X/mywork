package com.lanzhu.mywork.message.data.entity;

import com.alibaba.fastjson.JSON;
import com.lanzhu.mywork.master.common.utils.DateUtils;
import com.lanzhu.mywork.master.commons.ToString;
import com.lanzhu.mywork.message.common.enums.MessageType;
import com.lanzhu.mywork.message.vo.request.EmailMessageArg;
import com.lanzhu.mywork.message.vo.request.MobileMessageArg;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
public class MessageLog extends ToString{
    private static final long serialVersionUID = -3050113824073481904L;
    private Long id;

    private MessageType messageType;

    private String template;

    private Long userId;

    private String toAddress;

    private Boolean isSuccess;

    private Date createTime;

    private String params;

    public static MessageLog buildMessageLog(EmailMessageArg emailMessageArg) {
        MessageLog messageLog = new MessageLog();
        messageLog.setTemplate(emailMessageArg.getTemplate());
        messageLog.setUserId(emailMessageArg.getUserId());
        messageLog.setMessageType(MessageType.EMAIL);
        messageLog.setToAddress(emailMessageArg.getEmail());
        messageLog.setCreateTime(DateUtils.getNewDate());
        messageLog.setIsSuccess(null);
        if (emailMessageArg.getParams() != null) {
            messageLog.setParams(JSON.toJSONString(emailMessageArg.getParams()));
        }
        return messageLog;
    }

    public static MessageLog buildMessageLog(MobileMessageArg messageArg) {
        MessageLog messageLog = new MessageLog();
        messageLog.setTemplate(messageArg.getTemplate());
        messageLog.setUserId(messageArg.getUserId());
        messageLog.setMessageType(MessageType.MOBILE);
        messageLog.setToAddress(messageArg.getMobile());
        messageLog.setCreateTime(DateUtils.getNewDate());
        messageLog.setIsSuccess(false);
        if (messageArg.getParams() != null) {
            messageLog.setParams(JSON.toJSONString(messageArg.getParams()));
        }
        return messageLog;
    }

}