package com.lanzhu.mywork.account.controller;

import com.lanzhu.mywork.account.api.UserApi;
import com.lanzhu.mywork.account.service.IUserService;
import com.lanzhu.mywork.account.vo.user.UserVo;
import com.lanzhu.mywork.account.vo.user.request.UserQuery;
import com.lanzhu.mywork.account.vo.user.request.UserUpdateArg;
import com.lanzhu.mywork.master.model.ApiRequest;
import com.lanzhu.mywork.master.model.ApiResponse;
import com.lanzhu.mywork.master.model.SearchResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
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
        return ApiResponse.getOk(iUserService.getUserVoById(userId));
    }

    @Override
    public ApiResponse<Void> updateDisplayName(@Validated @RequestBody ApiRequest<UserUpdateArg> request) {
        iUserService.updateUser(request.getData());
        return ApiResponse.getOk();
    }

    @Override
    public ApiResponse<SearchResult<UserVo>> queryUserList(@Validated @RequestBody ApiRequest<UserQuery> request) {
        return ApiResponse.getOk(iUserService.queryUserVoList(request.getData()));
    }
}
