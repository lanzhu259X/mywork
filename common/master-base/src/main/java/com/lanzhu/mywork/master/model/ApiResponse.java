package com.lanzhu.mywork.master.model;

import com.lanzhu.mywork.master.commons.ToString;
import com.lanzhu.mywork.master.error.BaseErrorCode;
import com.lanzhu.mywork.master.error.ErrorCode;
import lombok.Getter;
import lombok.Setter;

/**
 * description:
 * 所有的API接口返回的格式定义
 * {
 *     success: true/false
 *     code: errorCode
 *     message: error message
 *     data: {}/[]
 * }
 * @author lanzhu259X
 * @date 2018-09-20
 */
@Setter
@Getter
public class ApiResponse<T> extends ToString {

    private static final long serialVersionUID = 3859910071736214157L;

    /** 成功的 code */
    private static final String SUCCESS = ErrorCode.SUCCESS.getCode();

    /** 业务是否正常完成, 默认 SUCCESS */
    private String code = SUCCESS;

    /** 错误异常时的描述信息 */
    private String message;

    private T data;

    public ApiResponse() {
        super();
    }

    public ApiResponse(T data) {
        super();
        setData(data);
    }

    public ApiResponse(String code, T data) {
        super();
        setCode(code);
        setData(data);
    }

    public ApiResponse(String code, String message, T data) {
        super();
        setCode(code);
        setMessage(message);
        setData(data);
    }

    /**
     * true: 表示程序处理正常，没有业务异常 false: 表示业务异常
     * 每次判断业务是否正常执行，直接看该数据
     * @return
     */
    public boolean isSuccess() {
        return SUCCESS.equals(this.code);
    }

    public static <T> ApiResponse<T> getOk() {
        return new ApiResponse();
    }

    public static <T> ApiResponse<T> getOk(T data) {
        return new ApiResponse<>(data);
    }

    public static <T> ApiResponse<T> getFail() {
        return new ApiResponse<>(ErrorCode.SYS_COMMON.getCode(), null);
    }

    /**
     * 返回的message 是空的，有data
     * @param data
     * @param <T>
     * @return
     */
    public static <T> ApiResponse<T> getFail(T data) {
        return new ApiResponse<>(ErrorCode.SYS_COMMON.getCode(), null, data);
    }

    /**
     * message 不会进行国际化
     * @param errorCode
     * @param <T>
     * @return
     */
    public static <T> ApiResponse<T> getFail(BaseErrorCode errorCode) {
        return new ApiResponse<>(errorCode.getCode(), errorCode.getMessage(), null);
    }

    /**
     * 该错误的message不会进行国际化(如需要需要手动国际化)
     * @param message
     * @param data
     * @param <T>
     * @return
     */
    public static <T> ApiResponse<T> getFail(String message, T data) {
        return new ApiResponse<>(ErrorCode.SYS_COMMON.getCode(), message, data);
    }

    /**
     * 该错误的message不会进行国际化
     * @param errorCode
     * @param data
     * @param <T>
     * @return
     */
    public static <T> ApiResponse<T> getFail(BaseErrorCode errorCode, T data) {
        return new ApiResponse<>(errorCode.getCode(), errorCode.getMessage(), data);
    }

    /**
     * 该错误的message不会进行国际化(如需要需要手动国际化)
     * @param errorCode
     * @param data
     * @param <T>
     * @return
     */
    public static <T> ApiResponse<T> getFail(BaseErrorCode errorCode, String message, T data) {
        return new ApiResponse<>(errorCode.getCode(), message, data);
    }

}
