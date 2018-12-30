package com.lanzhu.mywork.account.mapper;

import com.lanzhu.mywork.account.AccountApplication;
import com.lanzhu.mywork.account.data.entity.User;
import com.lanzhu.mywork.account.data.mapper.UserMapper;
import com.lanzhu.mywork.master.common.utils.DateUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * description:
 *
 * @author lanzhu259X
 * @date 2018-12-30
 */
@SpringBootTest(classes = AccountApplication.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class UserMapperTest {

    @Resource
    private UserMapper userMapper;

    @Test
    public void insertTest() {
        User user = new User();
        user.setDisplayName("test");
        user.setCreateTime(DateUtils.getNewDate());
        user.setStatus(0L);
        userMapper.insert(user);
    }
}
