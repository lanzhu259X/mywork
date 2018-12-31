package com.lanzhu.mywork.account.service.impl;


import com.lanzhu.mywork.account.common.AccountErrorCode;
import com.lanzhu.mywork.account.common.enums.UserAuthType;
import com.lanzhu.mywork.account.data.entity.User;
import com.lanzhu.mywork.account.data.entity.UserAuthenticate;
import com.lanzhu.mywork.account.data.mapper.UserAuthenticateMapper;
import com.lanzhu.mywork.account.data.mapper.UserMapper;
import com.lanzhu.mywork.account.service.IUserService;
import com.lanzhu.mywork.account.vo.user.UserVo;
import com.lanzhu.mywork.master.error.BizException;
import com.lanzhu.mywork.master.error.ErrorCode;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * description:
 *
 * @author lanzhu259X
 * @date 2018-12-22
 */
@Log4j2
@Service
public class UserService implements IUserService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserAuthenticateMapper userAuthenticateMapper;


    @Override
    public UserVo getUserById(Long userId) {
        if (userId == null) {
            throw new BizException(ErrorCode.SYS_PARAMS_VALID);
        }
        User user = userMapper.selectByPrimaryKey(userId);
        if (user == null) {
            throw new BizException(AccountErrorCode.USER_NOT_FOUND);
        }
        UserVo userVo = new UserVo();
        BeanUtils.copyProperties(user, userVo);
        return userVo;
    }

    @Override
    public UserVo getUserByEmail(String email) {
        UserAuthenticate userAuthenticate = getUserAuthByIdentifier(UserAuthType.EMAIL, email);
        if (userAuthenticate == null) {
            throw new BizException(AccountErrorCode.USER_NOT_FOUND);
        }
        return getUserById(userAuthenticate.getUserId());
    }

    @Override
    public UserVo getUserByMobile(String mobile) {
        UserAuthenticate userAuthenticate = getUserAuthByIdentifier(UserAuthType.MOBILE, mobile);
        if (userAuthenticate == null) {
            throw new BizException(AccountErrorCode.USER_NOT_FOUND);
        }
        return getUserById(userAuthenticate.getUserId());
    }

    private UserAuthenticate getUserAuthByIdentifier(UserAuthType userAuthType, String identifier) {
        if (userAuthType == null || StringUtils.isBlank(identifier)) {
            return null;
        }
        return userAuthenticateMapper.getUserAuthByIdentifier(userAuthType.name(), identifier);
    }
}
