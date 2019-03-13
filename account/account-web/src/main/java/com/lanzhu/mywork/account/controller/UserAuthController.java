package com.lanzhu.mywork.account.controller;

import com.lanzhu.mywork.account.api.UserAuthApi;
import com.lanzhu.mywork.account.vo.user.request.RegisterArg;
import com.lanzhu.mywork.master.model.ApiResponse;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserAuthController implements UserAuthApi {

    @Override
    public ApiResponse<Boolean> register(@Validated @RequestBody RegisterArg request) {
        return ApiResponse.getOk(Boolean.FALSE);
    }
}
