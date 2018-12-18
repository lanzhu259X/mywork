package com.lanzhu.mywork.account.controller;

import com.google.common.collect.Maps;
import com.lanzhu.mywork.account.api.UserApi;
import com.lanzhu.mywork.account.common.vo.UserVo;
import com.lanzhu.mywork.master.error.BizException;
import com.lanzhu.mywork.master.error.ErrorCode;
import com.lanzhu.mywork.master.model.ApiRequest;
import com.lanzhu.mywork.master.model.ApiResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.Map;

/**
 * description:
 *
 * @author lanzhu259X
 * @date 2018-12-17
 */
@Log4j2
@RestController
public class UserController implements UserApi {

    private static Map<Long, UserVo> userVoMap;

    static {
        userVoMap = Maps.newHashMap();
        UserVo vo = new UserVo();
        vo.setId(10000L);
        vo.setName("name-1");
        vo.setStatus(1);
        vo.setCreateTime(new Date());
        userVoMap.put(10000L, vo);

        UserVo vo1 = new UserVo();
        vo1.setId(10001L);
        vo1.setName("name-1");
        vo1.setStatus(1);
        vo1.setCreateTime(new Date());
        vo1.setUpdateTime(new Date());
        userVoMap.put(10001L, vo1);
    }

    @Override
    public ApiResponse<UserVo> getUserById(@PathVariable("id") Long userId) {
        log.info("get user by userId:{}", userId);
        UserVo vo = userVoMap.get(userId);
        if (vo == null) {
            log.warn("user not found by userId:{}", userId);
            throw new BizException(ErrorCode.SYS_PARAMS_VALID);
        }else {
            return ApiResponse.getOk(vo);
        }
    }

    @Override
    public ApiResponse<Void> changeUserStatus(@Validated @RequestBody ApiRequest<UserVo> request) {
        log.info("request change user status.");
        UserVo vo = request.getData();
        if (vo == null || vo.getId() == null) {
            log.warn("request change user status params error.");
            throw new BizException(ErrorCode.SYS_PARAMS_VALID);
        }
        Long userId = vo.getId();
        int status = vo.getStatus();
        UserVo userVo = userVoMap.get(userId);
        if (userVo == null) {
            log.warn("user id:{} is wrong.", userId);
            throw new BizException(ErrorCode.SYS_ERROR);
        }
        userVo.setStatus(status);
        userVo.setUpdateTime(new Date());
        userVoMap.put(userId, userVo);
        return ApiResponse.getOk();
    }
}
