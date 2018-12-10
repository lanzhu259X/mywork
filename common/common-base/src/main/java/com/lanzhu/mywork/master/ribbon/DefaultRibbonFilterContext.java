package com.lanzhu.mywork.master.ribbon;

import com.lanzhu.mywork.master.constant.Constant;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * description:
 *
 * @author lanzhu259X
 * @date 2018-09-22
 */
public class DefaultRibbonFilterContext implements IRibbonFilterContext {

    /** filter attributes map */
    private final Map<String, String> attributes = new HashMap<>();

    @Override
    public IRibbonFilterContext add(String key, String value) {
        attributes.put(key, value);
        return this;
    }

    @Override
    public String get(String key) {
        return attributes.get(key);
    }

    @Override
    public IRibbonFilterContext remove(String key) {
        attributes.remove(key);
        return this;
    }

    @Override
    public Map<String, String> getAttributes() {
        return Collections.unmodifiableMap(attributes);
    }

    @Override
    public IRibbonFilterContext setEnvTag(String value) {
        attributes.put(Constant.ENV_TAG, value);
        return this;
    }

    @Override
    public String getEnvTag() {
        return attributes.get(Constant.ENV_TAG);
    }
}
