package com.lanzhu.mywork.master.web;

import com.google.common.collect.Maps;
import com.lanzhu.mywork.master.error.BizException;
import com.lanzhu.mywork.master.error.ErrorCode;
import com.lanzhu.mywork.master.models.api.ApiResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.HandlerMethod;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * description: 异常统一处理
 *
 * @author lanzhu259X
 * @date 2018-12-08
 */
@Log4j2
@RestControllerAdvice
public class ExceptionHandlerAdvice {

    @Resource
    private MessageHelper messageHelper;

    @ExceptionHandler(value = BindException.class)
    public ApiResponse<?> exception(HttpServletResponse response, BindException exception, HandlerMethod handler)
            throws IOException {
        log.error("System error:", exception);
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        return this.getValid(exception);
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ApiResponse<?> exception(HttpServletResponse response, MethodArgumentNotValidException exception,
                                    HandlerMethod handler) throws IOException {
        log.error("System error:", exception);
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        return this.getValid(exception.getBindingResult());
    }

    @ExceptionHandler(value = BizException.class)
    public ApiResponse<?> exception(BizException bizException, HandlerMethod handlerMethod, HttpServletRequest request, HttpServletResponse response) throws IOException {
        log.warn("BizException: {}", this.parseExceptionAsString(bizException));
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        String message = messageHelper.getMessage(bizException.getErrorCode());
        return ApiResponse.getFail(bizException.getErrorCode(), message, bizException.getData());
    }

    @ExceptionHandler({Error.class, Exception.class, Throwable.class, RuntimeException.class})
    public ApiResponse<?> exception(HttpServletResponse response, Throwable exception, HttpServletRequest request) throws IOException {
        log.error("System error:", exception);
        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        String message = messageHelper.getMessage(ErrorCode.SYS_ERROR);
        return ApiResponse.getFail(ErrorCode.SYS_ERROR, message, null);
    }

    private ApiResponse<?> getValid(BindingResult bindingResult) {
        Map<String, Object> data = Maps.newHashMap();
        for (FieldError error : bindingResult.getFieldErrors()) {
            data.put(error.getField(), error.getDefaultMessage());
        }
        String message = messageHelper.getMessage(ErrorCode.SYS_PARAMS_VALID);
        return ApiResponse.getFail(ErrorCode.SYS_PARAMS_VALID, message, data);
    }

    private String parseExceptionAsString(Exception e) {
        StringBuilder sb = new StringBuilder();
        sb.append(e.getClass().getName());
        sb.append(":");
        sb.append(e.toString());
        sb.append("\n");
        StackTraceElement[] stes = e.getStackTrace();
        if (stes != null) {
            for (StackTraceElement ste : stes) {
                sb.append("\tat ");
                sb.append(ste.toString());
                sb.append("\n");
            }
        }
        return sb.toString();
    }

}
