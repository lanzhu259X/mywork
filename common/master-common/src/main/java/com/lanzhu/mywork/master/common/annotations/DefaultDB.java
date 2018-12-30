package com.lanzhu.mywork.master.common.annotations;

import java.lang.annotation.*;

/**
 * description: 默认数据库注解
 *
 * @author lanzhu259X
 * @date 2018-12-30
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
public @interface DefaultDB {

    public final static String TRANSACTION = "transactionManager";
}
