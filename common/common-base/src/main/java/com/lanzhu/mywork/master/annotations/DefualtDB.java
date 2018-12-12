package com.lanzhu.mywork.master.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * description:
 *      自定义 mybatis 数据库连接层Mapper的注解
 * @author lanzhu259X
 * @date 2018-09-13
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface DefualtDB {

    public final static String TRANSACTION = "transactionManager";
}
