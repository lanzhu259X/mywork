package com.lanzhu.mywork.master.ribbon;

import java.util.Map;

/**
 * description:
 *  Ribbon discovery filter context, stores the attributes based on which the server matching will be performed.
 * @author lanzhu259X
 * @date 2018-12-25
 */
public interface RibbonFilterContext {

    /**
     * Adds the context attribute.
     *
     * @param key   the attribute key
     * @param value the attribute value
     * @return the context instance
     */
    RibbonFilterContext add(String key, String value);

    /**
     * Retrieves the context attribute.
     *
     * @param key the attribute key
     * @return the attribute value
     */
    String get(String key);

    /**
     * Removes the context attribute.
     *
     * @param key the context attribute
     * @return the context instance
     */
    RibbonFilterContext remove(String key);

    /**
     * Retrieves the attributes.
     *
     * @return the attributes
     */
    Map<String, String> getAttributes();

    /**
     * add the context attribute. key="envFlag"
     *
     * @return the attribute value
     */
    RibbonFilterContext setEnvFlag(String value);

    /**
     * Retrieves the context attribute. key="envFlag"
     *
     * @return the attribute value
     */
    String getEnvFlag();
}
