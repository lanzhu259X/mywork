package com.lanzhu.mywork.account.api;

import com.lanzhu.mywork.account.common.vo.UserVo;
import com.lanzhu.mywork.master.config.FeignConfig;
import com.lanzhu.mywork.master.model.ApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

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

}
