package com.lanzhu.mywork.message.service;

import com.lanzhu.mywork.master.model.ApiRequest;
import com.lanzhu.mywork.message.vo.request.BatchMessageArg;
import com.lanzhu.mywork.message.vo.request.EmailMessageArg;
import com.lanzhu.mywork.message.vo.request.MobileMessageArg;

/**
 * description:
 *
 * @author lanzhu259X
 * @date 2019-01-01
 */
public interface IMessageService {

    /**
     * 发生邮件消息
     * @param request
     */
    void sendEmailMessage(EmailMessageArg request);

    /**
     * 批量发送邮件
     * @param data
     */
    void sendEmailBatch(BatchMessageArg data);

    /**
     * 发生短信信息
     * @param request
     */
    void sendMobileMessage(MobileMessageArg request);


    /**
     * 批量发送短信
     * @param data
     */
    void sendMobileBatch(BatchMessageArg data);
}
