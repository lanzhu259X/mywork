package com.lanzhu.mywork.master.config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.lanzhu.mywork.master.constant.Constant;
import com.lanzhu.mywork.master.ribbon.RibbonFilterContextHolder;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import feign.codec.Decoder;
import feign.codec.Encoder;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.support.ResponseEntityDecoder;
import org.springframework.cloud.openfeign.support.SpringDecoder;
import org.springframework.cloud.openfeign.support.SpringEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
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
    public Decoder feignDecoder() {
        HttpMessageConverter jacksonConverter = new MappingJackson2HttpMessageConverter(customObjectMapper());
        ObjectFactory<HttpMessageConverters> objectFactory = () -> new HttpMessageConverters(jacksonConverter);
        return new ResponseEntityDecoder(new SpringDecoder(objectFactory));
    }

    @Bean
    public Encoder feignEncoder(){
        HttpMessageConverter jacksonConverter = new MappingJackson2HttpMessageConverter(customObjectMapper());
        ObjectFactory<HttpMessageConverters> objectFactory = () -> new HttpMessageConverters(jacksonConverter);
        return new SpringEncoder(objectFactory);
    }

    private ObjectMapper customObjectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
        objectMapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);
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
