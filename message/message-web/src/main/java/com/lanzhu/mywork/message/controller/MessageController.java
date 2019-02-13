package com.lanzhu.mywork.message.controller;

import com.lanzhu.mywork.master.model.ApiResponse;
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
    public ApiResponse<Void> sendEmailMessage(@Validated @RequestBody EmailMessageArg request) {
        iMessageService.sendEmailMessage(request);
        return ApiResponse.getOk();
    }

    @Override
    public ApiResponse<Void> sendEmailBatch(@Validated @RequestBody BatchMessageArg request) {
        iMessageService.sendEmailBatch(request);
        return ApiResponse.getOk();
    }

    @Override
    public ApiResponse<Void> sendMobileMessage(@Validated @RequestBody MobileMessageArg request) {
        iMessageService.sendMobileMessage(request);
        return ApiResponse.getOk();
    }

    @Override
    public ApiResponse<Void> sendMobileBatch(@Validated @RequestBody BatchMessageArg request) {
        iMessageService.sendMobileBatch(request);
        return ApiResponse.getOk();
    }
}
