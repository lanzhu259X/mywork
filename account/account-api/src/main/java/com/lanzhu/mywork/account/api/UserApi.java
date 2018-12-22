package com.lanzhu.mywork.account.api;

import com.lanzhu.mywork.account.vo.user.UserVo;
import com.lanzhu.mywork.account.vo.user.request.UserQuery;
import com.lanzhu.mywork.account.vo.user.request.UserUpdateArg;
import com.lanzhu.mywork.master.config.FeignConfig;
import com.lanzhu.mywork.master.model.ApiRequest;
import com.lanzhu.mywork.master.model.ApiResponse;
import com.lanzhu.mywork.master.model.SearchResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * description:
 *
 * @author lanzhu259X
 * @date 2018-12-22
 */
@FeignClient(name = "account", configuration = FeignConfig.class)
public interface UserApi {


    /**
     * 根据user_id 获取用户信息
     * @param userId
     * @return
     */
    @GetMapping("/user/{userId}")
    ApiResponse<UserVo> getUserById(@PathVariable("userId") Long userId);


    /**
     * 修改用户的显示名
     * @param request
     * @return
     */
    @PutMapping("/user/update/displayName")
    ApiResponse<Void> updateDisplayName(@Validated @RequestBody ApiRequest<UserUpdateArg> request);

    /**
     * 查询用户列表
     * @param request
     * @return
     */
    @PostMapping("/user/query")
    ApiResponse<SearchResult<UserVo>> queryUserList(@Validated @RequestBody ApiRequest<UserQuery> request);

}
