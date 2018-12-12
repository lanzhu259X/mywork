package com.lanzhu.mywork.master.web;

import com.lanzhu.mywork.master.constant.Language;
import com.lanzhu.mywork.master.error.ErrorCode;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.text.MessageFormat;
import java.util.Locale;

/**
 * description: 国际化信息
 *
 * @author lanzhu259X
 * @date 2018-12-08
 */
@Component
@Log4j2
public class MessageHelper {

    @Resource
    private MessageSource messageSource;

    public String getMessage(ErrorCode errorCode, Object... params) {
        return getMessage(errorCode, null, params);
    }

    public String getMessage(ErrorCode errorCode, Language language, Object... params) {
        String key = errorCode.getClass().getName() + "." + errorCode.name();
        return getMessage(key, language, params);
    }

    public String getMessage(String key, Object... params) {
        return getMessage(key, null, params);
    }

    public String getMessage(String key, Language language, Object... params) {
        // 目前只支持中文和英文
        if (language == null) {
            language = WebHelper.getLanguage();
        }
        Locale locale = Locale.CHINA;
        if (StringUtils.equalsIgnoreCase("en", language.getCode())) {
            locale = Locale.US;
        }
        log.debug("国际化转化: key:{} language:{}", key, language);
        String message;
        try {
            message = messageSource.getMessage(new DefaultMessageSourceResolvable(key), locale);
            if (params != null) {
                message = MessageFormat.format(message, params);
            }
        } catch (NoSuchMessageException e) {
            message = key;
        } catch (Throwable e) {
            log.error("国际化信息异常. key:{} ", key, e);
            message = key;
        }
        return message;
    }
}
