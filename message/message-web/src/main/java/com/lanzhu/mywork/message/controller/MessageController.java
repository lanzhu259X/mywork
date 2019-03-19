package com.lanzhu.mywork.message.controller;

import com.lanzhu.mywork.master.model.ApiResult;
import com.lanzhu.mywork.message.api.MessageApi;
import com.lanzhu.mywork.message.service.IMessageService;
import com.lanzhu.mywork.message.vo.request.BatchMessageArg;
import com.lanzhu.mywork.message.vo.request.EmailMessageArg;
import com.lanzhu.mywork.message.vo.request.MobileMessageArg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * description:
 *
 * @author lanzhu259X
 * @date 2019-01-01
 */
@RestController
public class MessageController implements MessageApi {


    @Autowired
    private IMessageService iMessageService;

    @Override
    public ApiResult<Void> sendEmailMessage(@Validated @RequestBody EmailMessageArg request) {
        iMessageService.sendEmailMessage(request);
        return ApiResult.getOk();
    }

    @Override
    public ApiResult<Void> sendEmailBatch(@Validated @RequestBody BatchMessageArg request) {
        iMessageService.sendEmailBatch(request);
        return ApiResult.getOk();
    }

    @Override
    public ApiResult<Void> sendMobileMessage(@Validated @RequestBody MobileMessageArg request) {
        iMessageService.sendMobileMessage(request);
        return ApiResult.getOk();
    }

    @Override
    public ApiResult<Void> sendMobileBatch(@Validated @RequestBody BatchMessageArg request) {
        iMessageService.sendMobileBatch(request);
        return ApiResult.getOk();
    }
}
