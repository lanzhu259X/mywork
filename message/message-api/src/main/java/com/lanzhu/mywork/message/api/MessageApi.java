package com.lanzhu.mywork.message.api;

import com.lanzhu.mywork.master.config.FeignConfig;
import com.lanzhu.mywork.master.model.ApiRequest;
import com.lanzhu.mywork.master.model.ApiResponse;
import com.lanzhu.mywork.message.common.MessageConstant;
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
@FeignClient(name = MessageConstant.MESSAGE_FEIGN, configuration = FeignConfig.class)
public interface MessageApi {

    /**
     * 发生邮件消息
     * @param request
     * @return
     */
    @PostMapping("/message/send/email")
    ApiResponse<Void> sendEmailMessage(@Validated @RequestBody ApiRequest<EmailMessageArg> request);

    /**
     * 发生短信信息
     * @param request
     * @return
     */
    @PostMapping("/message/send/mobile")
    ApiResponse<Void> sendMobileMessage(@Validated @RequestBody ApiRequest<MobileMessageArg> request);
}
