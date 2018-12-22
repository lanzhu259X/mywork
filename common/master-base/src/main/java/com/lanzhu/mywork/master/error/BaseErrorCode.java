package com.lanzhu.mywork.master.error;

/**
 * description:
 *
 * @author lanzhu259X
 * @date 2018-12-23
 */
public interface BaseErrorCode {

    /**
     * 获取错误码
     * @return
     */
    String getCode();

    /**
     * 错误码描述
     * @return
     */
    String getMessage();
}
