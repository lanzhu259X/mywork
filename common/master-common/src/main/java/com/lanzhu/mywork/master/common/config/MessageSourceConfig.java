package com.lanzhu.mywork.master.common.config;

import com.lanzhu.mywork.master.common.web.MyWorkReloadableResourceBundleMessageSource;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * description:
 *
 * @author lanzhu259X
 * @date 2018-12-23
 */
@Configuration
public class MessageSourceConfig {

    @Bean
    public MessageSource messageSource() {
        MyWorkReloadableResourceBundleMessageSource messageSource = new MyWorkReloadableResourceBundleMessageSource();
        messageSource.setBasename("classpath*:/i18n/messages");
        return messageSource;
    }
}
