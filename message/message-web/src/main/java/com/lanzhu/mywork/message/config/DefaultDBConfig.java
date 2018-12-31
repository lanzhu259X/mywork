package com.lanzhu.mywork.message.config;

import com.lanzhu.mywork.master.common.annotations.DefaultDB;
import com.lanzhu.mywork.master.common.data.DBConfig;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

/**
 * description:
 *
 * @author lanzhu259X
 * @date 2018-12-30
 */
@Configuration
@MapperScan(basePackages = "com.lanzhu.mywork.message.data", annotationClass = DefaultDB.class,
        sqlSessionFactoryRef = "sqlSessionFactory")
public class DefaultDBConfig extends DBConfig {

    @Primary
    @Bean(name = "dataSource")
    @Override
    public DataSource dataSource() {
        return super.dataSource("druid.datasource.default");
    }

    @Primary
    @Bean(name = DefaultDB.TRANSACTION)
    @Override
    public DataSourceTransactionManager transactionManager(@Qualifier("dataSource") DataSource dataSource) {
        return super.transactionManager(dataSource);
    }

    @Primary
    @Bean(name = "sqlSessionFactory")
    @Override
    public SqlSessionFactory sqlSessionFactory(@Qualifier("dataSource") DataSource dataSource) throws Exception {
        return super.sqlSessionFactory(dataSource);
    }

}
