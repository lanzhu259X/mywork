package com.lanzhu.mywork.master.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.lanzhu.mywork.master.constant.Constant;
import com.lanzhu.mywork.master.ribbon.RibbonFilterContextHolder;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
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

    @Autowired
    private Environment env;

    @Bean
    public ObjectMapper feignEncoder(ObjectFactory<HttpMessageConverters> messageConverters) {
        ObjectMapper objectMapper = Jackson2ObjectMapperBuilder.json().build();
        objectMapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
        for (HttpMessageConverter<?> converter : messageConverters.getObject().getConverters()) {
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
                // 将ribbon中的envFlag通过header继续向下传递
                if (RibbonFilterContextHolder.getCurrentContext().getEnvFlag() != null) {
                    requestTemplate.header(Constant.GRAY_ENV_HEADER,
                            RibbonFilterContextHolder.getCurrentContext().getEnvFlag());
                } else {
                    // 若ribbon中无值，则从header中获取并设置
                    if (request.getHeader(Constant.GRAY_ENV_HEADER) != null) {
                        RibbonFilterContextHolder.getCurrentContext()
                                .setEnvFlag(request.getHeader(Constant.GRAY_ENV_HEADER));
                    } else {
                        this.setEnvFlag();
                    }
                }
                requestTemplate.header("Content-Type", MediaType.APPLICATION_JSON_UTF8_VALUE);
            }
        }catch (Exception e) {
            log.error("FeignConfig error: ", e);
        }
    }

    private void setEnvFlag() {
        // 如果配置了参数eureka.instance.metadataMap.targetEnvflag
        String targetEnvflag = env.getProperty("eureka.instance.metadataMap.targetEnvflag");
        if (StringUtils.isNoneBlank(targetEnvflag)) {
            RibbonFilterContextHolder.getCurrentContext().setEnvFlag(targetEnvflag);
        }
    }
}
