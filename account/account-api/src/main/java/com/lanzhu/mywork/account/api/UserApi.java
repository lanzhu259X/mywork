package com.lanzhu.mywork.account.api;

import com.lanzhu.mywork.account.vo.user.UserVo;
import com.lanzhu.mywork.master.config.FeignConfig;
import com.lanzhu.mywork.master.model.ApiResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * description:
 *
 * @author lanzhu259X
 * @date 2018-12-22
 */
@FeignClient(name = "account-user-api", configuration = FeignConfig.class)
public interface UserApi {

    /**
     * 获取用户信息
     * @param userId
     * @return
     */
    @GetMapping("/user/{userId}")
    ApiResult<UserVo> getUserById(@PathVariable("userId") Long userId);

    /**
     * 根据邮箱获取用户信息
     * @param email
     * @return
     */
    @GetMapping("/user/getUserByEmail")
    ApiResult<UserVo> getUserByEmail(@RequestParam(name = "email") String email);

    /**
     * 根据手机号获取用户信息
     * @param mobile
     * @return
     */
    @GetMapping("/user/getByMobile")
    ApiResult<UserVo> getUserByMobile(@RequestParam(name = "mobile") String mobile);

}
