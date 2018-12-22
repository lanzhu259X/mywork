package com.lanzhu.mywork.account.service;

import com.lanzhu.mywork.account.vo.user.UserVo;
import com.lanzhu.mywork.account.vo.user.request.UserQuery;
import com.lanzhu.mywork.account.vo.user.request.UserUpdateArg;
import com.lanzhu.mywork.master.model.SearchResult;

import java.util.List;

/**
 * description:
 *
 * @author lanzhu259X
 * @date 2018-12-22
 */
public interface IUserService {

    /**
     * 根据 userId 获取用户信息
     * @param userId
     * @return
     */
    UserVo getUserVoById(Long userId);

    /**
     * 修改用户信息
     * @param updateArg
     * @return
     */
    void updateUser(UserUpdateArg updateArg);

    /**
     * 查询用户列表
     * @param query
     * @return
     */
    SearchResult<UserVo> queryUserVoList(UserQuery query);
}
