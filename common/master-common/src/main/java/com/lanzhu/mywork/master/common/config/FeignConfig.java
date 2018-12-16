package com.lanzhu.mywork.master.common.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

/**
 * description:
 *
 * @author lanzhu259X
 * @date 2018-12-16
 */
@Log4j2
@Configuration
@ConditionalOnClass(RequestInterceptor.class)
public class FeignConfig implements RequestInterceptor{

    @Bean
    public ObjectMapper feignEncoder(HttpMessageConverters messageConverters) {
        ObjectMapper objectMapper = Jackson2ObjectMapperBuilder.json().build();
        objectMapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
        for (HttpMessageConverter<?> converter : messageConverters.getConverters()) {
            if (converter instanceof MappingJackson2HttpMessageConverter) {
                MappingJackson2HttpMessageConverter mjmc = (MappingJackson2HttpMessageConverter) converter;
                mjmc.setObjectMapper(objectMapper);
            }
        }
        return objectMapper;
    }

    @Override
    public void apply(RequestTemplate requestTemplate) {
        try {
            ServletRequestAttributes attributes =
                    (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            if (null != attributes) {
                HttpServletRequest request = attributes.getRequest();
                Enumeration<String> headerNames = request.getHeaderNames();
                if (headerNames != null) {
                    while (headerNames.hasMoreElements()) {
                        String name = headerNames.nextElement();
                        String values = request.getHeader(name);
                        requestTemplate.header(name, values);
                    }
                }
                requestTemplate.header("Content-Type", MediaType.APPLICATION_JSON_UTF8_VALUE);
            }
        }catch (Exception e) {
            log.error("FeignConfig error: ", e);
        }
    }
}
