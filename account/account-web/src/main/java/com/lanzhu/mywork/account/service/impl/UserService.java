package com.lanzhu.mywork.account.service.impl;

import com.google.common.collect.Maps;
import com.lanzhu.mywork.account.common.AccountErrorCode;
import com.lanzhu.mywork.account.data.entity.User;
import com.lanzhu.mywork.account.service.IUserService;
import com.lanzhu.mywork.account.vo.user.UserVo;
import com.lanzhu.mywork.account.vo.user.request.UserQuery;
import com.lanzhu.mywork.account.vo.user.request.UserUpdateArg;
import com.lanzhu.mywork.master.common.utils.DateUtils;
import com.lanzhu.mywork.master.error.BizException;
import com.lanzhu.mywork.master.error.ErrorCode;
import com.lanzhu.mywork.master.model.SearchResult;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * description:
 *
 * @author lanzhu259X
 * @date 2018-12-22
 */
@Log4j2
@Service
public class UserService implements IUserService {

    private static Map<Long, User> userMap;

    static {
        userMap = Maps.newHashMap();
        User user1 = new User();
        user1.setUserId(10001L);
        user1.setDisplayName("USER_1");
        user1.setCreateTime(DateUtils.getNewDate());
        user1.setUpdateTime(DateUtils.getNewDate());

        User user2 = new User();
        user2.setUserId(10002L);
        user2.setDisplayName("USER_2");
        user2.setCreateTime(DateUtils.getNewDate());
        user2.setUpdateTime(DateUtils.getNewDate());

        userMap.put(user1.getUserId(), user1);
        userMap.put(user2.getUserId(), user2);
    }

    @Override
    public UserVo getUserVoById(Long userId) {
        User user = userMap.get(userId);
        if (user == null) {
            throw new BizException(AccountErrorCode.USER_NOT_FOUND);
        }
        UserVo vo = new UserVo();
        BeanUtils.copyProperties(user, vo);
        return vo;
    }

    @Override
    public void updateUser(UserUpdateArg updateArg) {
        if (updateArg == null || updateArg.getUserId() == null) {
            throw new BizException(ErrorCode.SYS_PARAMS_VALID);
        }
        Long userId = updateArg.getUserId();
        log.info("update user:{} arg:{}", userId, updateArg.toString());
        User user = userMap.get(updateArg.getUserId());
        if (user == null) {
            throw new BizException(AccountErrorCode.USER_NOT_FOUND);
        }
        user.updateByUpdateAgr(updateArg);
    }

    @Override
    public SearchResult<UserVo> queryUserVoList(UserQuery query) {
        if (query == null) {
            return new SearchResult<>(0L, Collections.emptyList());
        }
        List<UserVo> users = new ArrayList<>();
        if (query.getUserId() != null) {
            User user = userMap.get(query.getUserId());
            if (user != null) {
                UserVo userVo = new UserVo();
                BeanUtils.copyProperties(user, userVo);
                users.add(userVo);
                return new SearchResult<>(1L, users);
            }else {
                return new SearchResult<>(0L, Collections.emptyList());
            }
        }else {
            userMap.forEach((userId, user) -> {
                UserVo vo = new UserVo();
                BeanUtils.copyProperties(user, vo);
                users.add(vo);
            });
            Long total = Integer.toUnsignedLong(users.size());
            return new SearchResult<>(total, users);
        }
    }
}
