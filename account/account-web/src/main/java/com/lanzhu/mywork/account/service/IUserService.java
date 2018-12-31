package com.lanzhu.mywork.account.service;


import com.lanzhu.mywork.account.vo.user.UserVo;

/**
 * description:
 *
 * @author lanzhu259X
 * @date 2018-12-22
 */
public interface IUserService {


    /**
     * 根据用户ID获取用户信息
     * @param userId
     * @return
     */
    UserVo getUserById(Long userId);

    /**
     * 根据邮箱获取用户信息
     * @param email
     * @return
     */
    UserVo getUserByEmail(String email);

    /**
     * 根据手机号获取用户信息
     * @param mobile
     * @return
     */
    UserVo getUserByMobile(String mobile);


}
