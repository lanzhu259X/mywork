package com.lanzhu.mywork.mgs.account;

import com.lanzhu.mywork.mgs.account.vo.UserResult;
import com.lanzhu.mywork.mgs.base.BaseAction;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;


@Log4j2
@RestController
@RequestMapping("/private/v1")
public class UserController extends BaseAction {


    @GetMapping("/account/user/{userId}")
    public UserResult getUserById(@PathVariable("userId") Long userId) {
        log.info("get user by userId:{}", userId);
        UserResult result = new UserResult();
        result.setDisplayName("test");
        result.setAvatarUrl("https://xxx.com/xxx.png");
        result.setInviteCode("12345");
        result.setUpdateTime(new Date());
        result.setCreateTime(new Date());
        return result;
    }

}
