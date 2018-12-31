package com.lanzhu.mywork.message.service.impl;

import com.alibaba.fastjson.JSON;
import com.lanzhu.mywork.master.model.ApiRequest;
import com.lanzhu.mywork.message.service.IMessageService;
import com.lanzhu.mywork.message.vo.request.EmailMessageArg;
import com.lanzhu.mywork.message.vo.request.MobileMessageArg;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

/**
 * description:
 *
 * @author lanzhu259X
 * @date 2019-01-01
 */
@Log4j2
@Service
public class MessageService implements IMessageService {

    @Override
    public void sendEmailMessage(ApiRequest<EmailMessageArg> request) {

    }

    @Override
    public void sendMobileMessage(ApiRequest<MobileMessageArg> request) {
        log.info("send mobile message arg:{}", JSON.toJSONString(request));
        return;
    }
}
