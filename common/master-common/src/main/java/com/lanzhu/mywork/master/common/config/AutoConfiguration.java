package com.lanzhu.mywork.master.common.config;

import com.lanzhu.mywork.master.common.web.WebHelper;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

/**
 * description:
 *
 * @author lanzhu259X
 * @date 2018-12-23
 */
@Configuration
@ComponentScan("com.lanzhu.mywork.master")
public class AutoConfiguration {

    @Resource
    private ApplicationContext applicationContext;

    @PostConstruct
    private void init() {
        WebHelper.setApplicationContext(applicationContext);
    }
}
