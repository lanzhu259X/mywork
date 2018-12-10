package com.lanzhu.mywork.master.ribbon;

/**
 * description:
 *  The Ribbon filter context holder.
 * @author lanzhu259X
 * @date 2018-09-22
 */
public class RibbonFilterContextHolder {

    /**
     * stores {@link IRibbonFilterContext} for current thread
     */
    private static final ThreadLocal<IRibbonFilterContext> contextHolder =
            new InheritableThreadLocal<IRibbonFilterContext>() {
                @Override
                protected IRibbonFilterContext initialValue() {
                    return new DefaultRibbonFilterContext();
                }
            };

    /**
     * retrieves current thread bound instance of {@link IRibbonFilterContext}
     * @return the current context instance
     */
    public static IRibbonFilterContext getCurrentContext() {
        return contextHolder.get();
    }

    /**
     * clear current context
     */
    public static void clearCurrentContext() {
        contextHolder.remove();
    }
}
