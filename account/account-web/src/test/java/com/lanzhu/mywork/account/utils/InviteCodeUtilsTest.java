package com.lanzhu.mywork.account.utils;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Random;

/**
 * description:
 *
 * @author lanzhu259X
 * @date 2018-12-31
 */
@RunWith(SpringJUnit4ClassRunner.class)
public class InviteCodeUtilsTest {

    @Test
    public void testInviteCode() {
        Random random = new Random();
        for (int i=0; i<50; i++) {
            int id = random.nextInt(Integer.MAX_VALUE);
            long idVal = Long.valueOf(id);
            String inviteCode = InviteCodeUtils.idToCode(idVal);
            long nId = InviteCodeUtils.codeToId(inviteCode);
            System.out.println(idVal + "->" + inviteCode + "->" + nId);
        }
    }
}
