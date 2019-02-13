package com.lanzhu.mywork.master.common.config;

import com.lanzhu.mywork.master.common.utils.TrackingUtils;
import com.lanzhu.mywork.master.common.web.WebHelper;
import com.lanzhu.mywork.master.constant.Constant;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

/**
 * description: 主要用于微服务之间的调用链
 *
 * @author lanzhu259X
 * @date 2018-09-22
 */
@Aspect
@Component
@Configuration
@ConditionalOnClass(JoinPoint.class)
public class TrackingChainConfig {

    private static final String REQUEST = "REQ";

    @Pointcut("@within(org.springframework.web.bind.annotation.RestController)")
    public void executionService() {
        // do nothing because pointcut define
    }

    @Before(value = "executionService()")
    public void doBefore(JoinPoint joinPoint) {
        String envTag = StringUtils.EMPTY;
        HttpServletRequest request = WebHelper.getHttpServletRequest();
        if (request == null) {
            return;
        }
        try {
            envTag = request.getHeader(Constant.ENV_TAG_GRAY);
            if (StringUtils.isBlank(envTag)) {
                envTag = WebHelper.getCookieValue(request, Constant.ENV_TAG_GRAY);
            }
            String trackingChain = request.getHeader(Constant.HEAD_TRACKING_CHAIN);
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
