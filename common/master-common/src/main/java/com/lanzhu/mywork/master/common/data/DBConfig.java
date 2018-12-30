package com.lanzhu.mywork.master.common.data;

import com.alibaba.druid.pool.DruidDataSource;
import com.github.pagehelper.PageInterceptor;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Properties;

/**
 * description:
 *
 * @author lanzhu259X
 * @date 2018-12-30
 */
@Log4j2
public abstract class DBConfig {

    private String mapperLocation;

    @Resource
    private ApplicationContext applicationContext;

    @Resource
    private Environment environment;


    protected void setMapperLocation(String mapperLocation) {
        this.mapperLocation = mapperLocation;
    }

    /**
     * Mapper.xml 的加载路径
     * 默认为类加载路径下去除config/下的 data/../*Mapper.xml
     * @return
     */
    protected String getMapperLocation() {
        if (this.mapperLocation == null) {
            this.mapperLocation = StringUtils.removeEnd(this.getClass().getResource("").toString(), "config/") + "data/**/*Mapper.xml";
        }
        return this.mapperLocation;
    }

    protected abstract DataSource dataSource() throws SQLException;

    /**
     * 构建dataSource
     * @param prefix
     * @return
     */
    protected DataSource dataSource(String prefix) {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setUsername(environment.getProperty(prefix + ".username", String.class));
        dataSource.setPassword(environment.getProperty(prefix + ".password", String.class));
        dataSource.setUrl(environment.getProperty(prefix + ".url", String.class));
        dataSource.setDriverClassName(environment.getProperty(prefix + ".driverClassName", String.class));
        return dataSource;
    }

    /**
     * DataSourceTransactionManager 统一构建方法
     * @param dataSource
     * @return
     */
    protected DataSourceTransactionManager transactionManager(DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    /**
     * SqlSessionFactory 统一构建方法
     * @param dataSource
     * @return
     * @throws Exception
     */
    protected SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {
        final SqlSessionFactoryBean sqlSessionFactory = new SqlSessionFactoryBean();
        sqlSessionFactory.setDataSource(dataSource);
        sqlSessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(getMapperLocation()));
        sqlSessionFactory.setConfiguration(getConfiguration());
        PageInterceptor page = new PageInterceptor();
        Properties properties = new Properties();
        properties.setProperty("reasonable", "true");
        properties.setProperty("offsetAsPageNum", "true");
        properties.setProperty("autoRuntimeDialect", "true");
        properties.setProperty("supportMethodsArguments", "true");
        properties.setProperty("rowBoundsWithCount", "false");
        page.setProperties(properties);
        sqlSessionFactory.setPlugins(new Interceptor[] {page});
        return sqlSessionFactory.getObject();
    }

    /**
     * SqlSessionFactory 的配置信息
     * @return
     */
    protected Configuration getConfiguration() {
        Configuration config = new Configuration();
        // 使全局的映射器启用或禁用缓存
        config.setCacheEnabled(true);
        // 全局启用或禁用延迟加载。当禁用时，所有关联对象都会即时加载
        config.setLazyLoadingEnabled(false);
        // 配置默认的执行器。SIMPLE 就是普通的执行器；REUSE 执行器会重用预处理语句（prepared statements）； BATCH 执行器将重用语句并执行批量更新。
        config.setDefaultExecutorType(ExecutorType.SIMPLE);
        return config;
    }

}
