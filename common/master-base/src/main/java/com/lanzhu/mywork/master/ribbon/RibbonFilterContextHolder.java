package com.lanzhu.mywork.master.ribbon;

/**
 * description:
 *
 * @author lanzhu259X
 * @date 2018-12-25
 */
public class RibbonFilterContextHolder {

    /**
     * Stores the {@link RibbonFilterContext} for current thread.
     */
    private static final ThreadLocal<RibbonFilterContext> contextHolder =
            new InheritableThreadLocal<RibbonFilterContext>() {
                @Override
                protected RibbonFilterContext initialValue() {
                    return new DefaultRibbonFilterContext();
                }
            };

    /**
     * Retrieves the current thread bound instance of {@link RibbonFilterContext}.
     *
     * @return the current context
     */
    public static RibbonFilterContext getCurrentContext() {
        return contextHolder.get();
    }

    /**
     * Clears the current context.
     */
    public static void clearCurrentContext() {
        contextHolder.remove();
    }

}
