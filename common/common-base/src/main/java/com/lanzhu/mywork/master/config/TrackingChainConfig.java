package com.lanzhu.mywork.master.config;

import com.lanzhu.mywork.master.constant.Constant;
import com.lanzhu.mywork.master.models.api.ApiRequest;
import com.lanzhu.mywork.master.utils.TrackingUtils;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

/**
 * description:
 *
 * @author lanzhu259X
 * @date 2018-09-22
 */
@Aspect
@Component
@Configuration
@ConditionalOnClass(JoinPoint.class)
public class TrackingChainConfig {

    private static final String REQUEST = "request";

    @Pointcut("@within(org.springframework.web.bind.annotation.RestController)")
    public void executionService() {
    }

    @Before(value = "executionService()")
    public void doBefore(JoinPoint joinPoint) {
        String envTag;
        try{
            HttpServletRequest request =
                    ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            envTag = request.getHeader(Constant.ENV_TAG_GRAY);
        }catch (Exception e) {
            envTag = StringUtils.EMPTY;
        }

        try {
            final String trackingChain = ((ApiRequest) joinPoint.getArgs()[0]).getTrackingChain();
            if (StringUtils.isNotBlank(trackingChain)) {
                TrackingUtils.putTracking(trackingChain);
            }else {
                getNewTrackingChain(envTag);
            }
        }catch (Exception e) {
            getNewTrackingChain(envTag);
        }
    }

    @After(value = "executionService()")
    public void doAfter(JoinPoint joinPoint) {
        TrackingUtils.removeTracking();
    }

    private void getNewTrackingChain(final String envTag) {
        if (StringUtils.isNotBlank(envTag) && !StringUtils.equalsIgnoreCase(envTag, Constant.ENV_TAG_NORMAL)) {
            TrackingUtils.putTracking(REQUEST + "-" + envTag,
                    String.valueOf(UUID.randomUUID()).replaceAll("-", StringUtils.EMPTY));
        }else {
            TrackingUtils.putTracking(REQUEST, String.valueOf(UUID.randomUUID()).replaceAll("-", StringUtils.EMPTY));
        }
    }
}
