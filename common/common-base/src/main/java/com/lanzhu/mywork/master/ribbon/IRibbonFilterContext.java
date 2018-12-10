package com.lanzhu.mywork.master.ribbon;

import java.util.Map;

/**
 * description:
 *  Ribbon discovery filter context, stores the attributes on which the server matching will be performed.
 * @author lanzhu259X
 * @date 2018-09-22
 */
public interface IRibbonFilterContext {

    /**
     * add the context attributes
     * @param key the attribute key
     * @param value the attribute value
     * @return the context instance
     */
    IRibbonFilterContext add(String key, String value);

    /**
     * retrieves the context attribute
     * @param key the attribute key
     * @return the attribute value
     */
    String get(String key);

    /**
     * remove the context attribute
     * @param key the attribute key
     * @return the context instance
     */
    IRibbonFilterContext remove(String key);

    /**
     * retrieves the attribute
     * @return the attributes map
     */
    Map<String, String> getAttributes();


    /**
     * add the context attribute which key="envTag"
     * @param value the envTag attribute value
     * @return the attribute instance
     */
    IRibbonFilterContext setEnvTag(String value);


    /**
     * retrieves the context attribute which key="envTag"
     * @return the attribute value
     */
    String getEnvTag();

}
