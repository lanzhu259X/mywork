package com.lanzhu.mywork.account.api;

import com.lanzhu.mywork.account.common.AccountConstant;
import com.lanzhu.mywork.account.vo.user.request.RegisterArg;
import com.lanzhu.mywork.master.config.FeignConfig;
import com.lanzhu.mywork.master.model.ApiRequest;
import com.lanzhu.mywork.master.model.ApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * description:
 *  注册登录认证相关接口
 *
 * @author lanzhu259X
 * @date 2018-12-31
 */
@FeignClient(name = AccountConstant.FEIGN_CLIENT_NAME, configuration = FeignConfig.class)
public interface UserAuthApi {


    /**
     * 注册
     * @param request
     * @return
     */
    @PostMapping("/user/auth/register")
    ApiResponse<Boolean> register(@Validated @RequestBody ApiRequest<RegisterArg> request);


}
