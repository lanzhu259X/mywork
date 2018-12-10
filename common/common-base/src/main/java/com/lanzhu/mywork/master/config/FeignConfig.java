package com.lanzhu.mywork.master.config;

import com.lanzhu.mywork.master.constant.Constant;
import com.lanzhu.mywork.master.ribbon.IRibbonFilterContext;
import com.lanzhu.mywork.master.ribbon.RibbonFilterContextHolder;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

/**
 * description:
 *
 * @author lanzhu259X
 * @date 2018-09-22
 */
@Log4j2
@Configuration
@ConditionalOnClass(RequestInterceptor.class)
public class FeignConfig implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate template) {
        try {
            ServletRequestAttributes attributes =
                    (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            if (attributes == null) {
                return;
            }
            HttpServletRequest request = attributes.getRequest();
            //把所有请求头信息抽出，设置到template中
            Enumeration<String> headNames = request.getHeaderNames();
            if (headNames != null) {
                while (headNames.hasMoreElements()) {
                    String name = headNames.nextElement();
                    String value = request.getHeader(name);
                    template.header(name, value);
                }
            }
            //将ribbon中的 envTag 通过header继续传输下去，用于灰度发布测试
            IRibbonFilterContext context = RibbonFilterContextHolder.getCurrentContext();
            if (StringUtils.isNotBlank(context.getEnvTag())) {
                template.header(Constant.ENV_TAG_GRAY, context.getEnvTag());
            }else {
                //若ribbon中无值，则从header中获取并设置
                if (StringUtils.isNotBlank(request.getHeader(Constant.ENV_TAG_GRAY))) {
                    context.setEnvTag(request.getHeader(Constant.ENV_TAG_GRAY));
                }
            }
            //使用json格式，编码UTF-8
            template.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8_VALUE);
        }catch (Exception e) {
            log.warn("FeignConfig error: ", e);
        }
    }
}