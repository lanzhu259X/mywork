package com.lanzhu.mywork.account.controller;

import com.lanzhu.mywork.account.api.UserApi;
import com.lanzhu.mywork.account.service.IUserService;
import com.lanzhu.mywork.account.vo.user.UserVo;
import com.lanzhu.mywork.master.model.ApiResult;
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
    public ApiResult<UserVo> getUserById(@PathVariable("userId") Long userId) {
        return ApiResult.getOk(iUserService.getUserById(userId));
    }

    @Override
    public ApiResult<UserVo> getUserByEmail(String email) {
        return ApiResult.getOk(iUserService.getUserByEmail(email));
    }

    @Override
    public ApiResult<UserVo> getUserByMobile(String mobile) {
        return ApiResult.getOk(iUserService.getUserByMobile(mobile));
    }
}
