package com.lanzhu.mywork.master.utils;

/**
 * description: 位运算，user status 使用的是位运算符来进行计算
 *
 * @author lanzhu259X
 * @date 2018-12-30
 */
public class BitUtils {

    public static boolean isTrue(Long status, Long code) {
        return (status & code) != 0;
    }

    public static boolean isEnable(Long status, Long code) {
        return (status & code) == code;
    }

    public static boolean isFalse(Long status, Long code) {
        return !isTrue(status, code);
    }

    public static Long enable(Long status, Long code) {
        return status | code;
    }

    public static Long disable(Long status, Long code) {
        return status & ~code;
    }
}
