package com.lanzhu.mywork.master.common.utils;

import org.apache.commons.lang3.StringUtils;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * description:
 * xxxx${argName}xxxx${argName1}xxxx 类型的字符串信息替换到map中的值
 * @author lanzhu259X
 * @date 2019-01-01
 */
public class StringTemplateUtils {

    public static final String DEF_REGEX="\\$\\{(.+?)\\}";

    public static String render(String template, Map<String, String> data) {
        return render(template,data,DEF_REGEX);
    }

    public static String render(String template, Map<String, String> data,String regex) {
        if(StringUtils.isBlank(template)){
            return "";
        }
        if(StringUtils.isBlank(regex)){
            return template;
        }
        if(data == null || data.size() == 0){
            return template;
        }
        try {
            StringBuffer sb = new StringBuffer();
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(template);
            while (matcher.find()) {
                // 键名
                String name = matcher.group(1);
                // 键值
                String value = data.get(name);
                if (value == null) {
                    value = "";
                }
                matcher.appendReplacement(sb, value);
            }
            matcher.appendTail(sb);
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return template;
    }
}
