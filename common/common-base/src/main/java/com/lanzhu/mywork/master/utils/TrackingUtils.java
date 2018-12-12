package com.lanzhu.mywork.master.utils;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.ThreadContext;
import org.apache.logging.log4j.util.Strings;

/**
 * description: 微服务间的跟踪链
 *
 * @author lanzhu259X
 * @date 2018-09-22
 */
public final class TrackingUtils {

    /** 对应log4j2.xml中 %X{trackingId} */
    public static final String TRACKING_CHAIN = "tracking-chain";

    /** tracking:type:context (例如: [tracking:job:a083188aaeed4e8fa73967f282b54629]) */
    public static final String SIMPLE_TEMPLATE = "tracking:%s:%s";

    /**
     * 入口开始处加入跟踪链
     *
     * @param type    跟踪类型
     * @param context 唯一标识内容
     */
    public static void putTracking(final String type, final String context) {
        ThreadContext.put(TRACKING_CHAIN, String.format(SIMPLE_TEMPLATE, type, context));
    }

    public static void putTracking(final String context) {
        ThreadContext.put(TRACKING_CHAIN, context);
    }

    /**
     * 入口结束处释放跟踪链
     */
    public static void removeTracking() {
        ThreadContext.remove(TRACKING_CHAIN);
    }

    /**
     * 获取当前线程的Tracking-chain
     * @return
     */
    public static String getTrackingChain() {
        return StringUtils.isNotBlank(ThreadContext.get(TRACKING_CHAIN)) ? ThreadContext.get(TRACKING_CHAIN)
                : Strings.EMPTY;
    }

}
