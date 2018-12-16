package com.lanzhu.mywork.master.commons;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;

/**
 * description:
 *  model can extends then override class toString method,
 *  add method to json and jsonString
 * @author lanzhu259X
 * @date 2018-09-13
 */
public class ToString implements Serializable {
    private static final long serialVersionUID = 3067921241573392288L;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

    protected String toJsonString() {
        return JSON.toJSONString(this);
    }

    protected String toJsonString(boolean isFormatter) {
        return isFormatter ? JSON.toJSONString(this, SerializerFeature.PrettyFormat) : toJsonString();
    }

}
