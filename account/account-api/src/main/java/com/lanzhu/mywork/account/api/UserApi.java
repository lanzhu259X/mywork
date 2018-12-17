package com.lanzhu.mywork.account.api;

import com.lanzhu.mywork.account.common.vo.UserVo;
import com.lanzhu.mywork.master.config.FeignConfig;
import com.lanzhu.mywork.master.model.ApiRequest;
import com.lanzhu.mywork.master.model.ApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * description:
 *
 * @author lanzhu259X
 * @date 2018-12-17
 */
@FeignClient(name = "account", configuration = FeignConfig.class)
@RequestMapping("/user")
public interface UserApi {

    /**
     * 根据userId获取用户信息
     * @param userId
     * @return
     */
    @GetMapping("/{id}")
    ApiResponse<UserVo> getUserById(@PathVariable("id") Long userId);

    /**
     * 修改用户的状态
     * @param request
     * @return
     */
    @PostMapping("/change/status")
    ApiResponse<Void> changeUserStatus(@Validated @RequestBody ApiRequest<UserVo> request);

}
