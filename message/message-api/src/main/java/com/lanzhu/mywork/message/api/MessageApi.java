package com.lanzhu.mywork.message.api;

import com.lanzhu.mywork.master.config.FeignConfig;
import com.lanzhu.mywork.master.model.ApiResult;
import com.lanzhu.mywork.message.vo.request.BatchMessageArg;
import com.lanzhu.mywork.message.vo.request.EmailMessageArg;
import com.lanzhu.mywork.message.vo.request.MobileMessageArg;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * description:
 *
 * @author lanzhu259X
 * @date 2018-12-31
 */
@FeignClient(name = "message-api", configuration = FeignConfig.class)
public interface MessageApi {

    /**
     * 发生邮件消息
     * @param request
     * @return
     */
    @PostMapping("/message-api/send/email")
    ApiResult<Void> sendEmailMessage(@Validated @RequestBody EmailMessageArg request);

    /**
     * 批量发送邮件
     * @param request
     * @return
     */
    @PostMapping("/message-api/send/email/batch")
    ApiResult<Void> sendEmailBatch(@Validated @RequestBody BatchMessageArg request);

    /**
     * 发生短信信息
     * @param request
     * @return
     */
    @PostMapping("/message-api/send/mobile")
    ApiResult<Void> sendMobileMessage(@Validated @RequestBody MobileMessageArg request);

    /**
     * 批量发送短信
     * @param request
     * @return
     */
    @PostMapping("/message-api/send/mobile/batch")
    ApiResult<Void> sendMobileBatch(@Validated @RequestBody BatchMessageArg request);
}
