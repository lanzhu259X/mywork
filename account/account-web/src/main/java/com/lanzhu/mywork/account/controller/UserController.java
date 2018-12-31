package com.lanzhu.mywork.account.controller;

import com.lanzhu.mywork.account.api.UserApi;
import com.lanzhu.mywork.account.service.IUserService;
import com.lanzhu.mywork.account.vo.user.UserVo;
import com.lanzhu.mywork.master.model.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * description:
 *
 * @author lanzhu259X
 * @date 2018-12-22
 */
@RestController
public class UserController implements UserApi {

    @Autowired
    private IUserService iUserService;

    @Override
    public ApiResponse<UserVo> getUserById(@PathVariable("userId") Long userId) {
        return ApiResponse.getOk(iUserService.getUserById(userId));
    }

    @Override
    public ApiResponse<UserVo> getUserByEmail(String email) {
        return ApiResponse.getOk(iUserService.getUserByEmail(email));
    }

    @Override
    public ApiResponse<UserVo> getUserByMobile(String mobile) {
        return ApiResponse.getOk(iUserService.getUserByMobile(mobile));
    }
}
