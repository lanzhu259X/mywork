package com.lanzhu.mywork.master.common.config;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lanzhu.mywork.master.common.utils.TrackingUtils;
import com.lanzhu.mywork.master.common.web.CustomServletRequestWrapper;
import com.lanzhu.mywork.master.constant.Constant;
import com.lanzhu.mywork.master.constant.Language;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.entity.ContentType;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.i18n.LocaleContext;
import org.springframework.context.i18n.SimpleLocaleContext;
import org.springframework.context.i18n.TimeZoneAwareLocaleContext;
import org.springframework.http.HttpMethod;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.util.WebUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Locale;
import java.util.TimeZone;
import java.util.UUID;

/**
 * description:
 *
 * @author lanzhu259X
 * @date 2018-09-22
 */
@Log4j2
@Configuration
@ConditionalOnProperty(value = "common.configs.locale.enabled", matchIfMissing = true)
public class LocaleConfig {

    private final static String NONE_JSON = "noneJson";

    private final static Language LANGUAGE = Language.CH_ZH;

    @Bean
    public LocaleResolver localeResolver() {
        SessionLocaleResolver slr = new SessionLocaleResolver();
        slr.setDefaultLocale(org.springframework.util.StringUtils.parseLocaleString(LANGUAGE.getCode()));
        return slr;
    }

    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor() {
        LocaleChangeInterceptor lci = new LocaleChangeInterceptor() {
            @Override
            public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
                    throws ServletException {
                return true;
            }
        };
        lci.setParamName("lang");
        return lci;
    }

    @Bean(name = "customServletRequestFilter")
    public OncePerRequestFilter oncePerRequestFilter() {
        OncePerRequestFilter filter = new OncePerRequestFilter() {

            @Override
            protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
                // 如果请求点类型不是json不进行拦截
                if (!StringUtils.containsIgnoreCase(request.getContentType(), ContentType.APPLICATION_JSON.getMimeType())) {
                    return true;
                }
                return false;
            }

            @Override
            protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                            FilterChain filterChain) throws ServletException, IOException {

                TrackingUtils.putTracking(NONE_JSON, UUID.randomUUID().toString().replaceAll("-", ""));
                // 设置成可以重复读取的流
                if (!(request instanceof CustomServletRequestWrapper)) {
                    request = new CustomServletRequestWrapper(request);
                }
                // 语言
                Locale locale = RequestContextUtils.getLocale(request);
                Language language = Language.findByCode(StringUtils.replace(locale.toLanguageTag(), "-", "_"));
                if (language == null) {
                    locale = org.springframework.util.StringUtils.parseLocaleString(LANGUAGE.getCode());
                    setLocale(request, response, locale);
                }else {
                    setLocale(request, response, org.springframework.util.StringUtils
                            .parseLocaleString(language.getCode()));
                }
                // 跟踪链
                String trackingChain = request.getHeader(Constant.HEAD_TRACKING_CHAIN);
                if (StringUtils.isNotBlank(trackingChain)) {
                    TrackingUtils.putTracking(trackingChain);
                }
                log.info("request uri:{}", request.getRequestURI());
                filterChain.doFilter(request, response);
                TrackingUtils.removeTracking();
            }
        };
        return filter;
    }

    protected void setLocale(HttpServletRequest request, HttpServletResponse response, Locale locale) {
        final LocaleContext localeContext = (locale != null ? new SimpleLocaleContext(locale) : null);
        Locale localeNew = null;
        TimeZone timeZone = null;
        if (localeContext != null) {
            localeNew = localeContext.getLocale();
            if (localeContext instanceof TimeZoneAwareLocaleContext) {
                timeZone = ((TimeZoneAwareLocaleContext) localeContext).getTimeZone();
            }
        }
        WebUtils.setSessionAttribute(request, SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME, localeNew);
        WebUtils.setSessionAttribute(request, SessionLocaleResolver.TIME_ZONE_SESSION_ATTRIBUTE_NAME, timeZone);
    }
}
