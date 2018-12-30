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


}
