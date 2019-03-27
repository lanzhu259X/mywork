package com.lanzhu.mywork.gateway.filter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {

    @Bean
    public RequestTimeGatewayFilterFactory getRequestTimeGatewayFilterFactory() {
        return new RequestTimeGatewayFilterFactory();
    }
}
