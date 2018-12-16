package com.lanzhu.mywork.master.model;

import com.lanzhu.mywork.master.constant.Language;
import com.lanzhu.mywork.master.constant.Terminal;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;

/**
 * description:
 *
 * @author lanzhu259X
 * @date 2018-09-20
 */
@Setter
@Getter
public class ApiRequest<T> extends ApiRequestHeader {

    private static final long serialVersionUID = 8891582422574032814L;

    @Valid
    private T data;

    public static <T> ApiRequest<T> instance(T data) {
        ApiRequest<T> request = new ApiRequest<>();
        request.setLanguage(Language.CH_ZH);
        request.setTerminal(Terminal.OTHER);
        request.setData(data);
        return request;
    }

    public static <T> ApiRequest<T> instance(ApiRequestHeader header, T data) {
        ApiRequest<T> request = new ApiRequest<>();
        request.setLanguage(header.getLanguage() == null ? Language.CH_ZH : header.getLanguage());
        request.setTerminal(header.getTerminal() == null ? Terminal.OTHER : header.getTerminal());
        request.setUserId(header.getUserId());
        request.setTrackingChain(header.getTrackingChain());
        request.setVersion(header.getVersion());
        request.setData(data);
        return request;
    }

}
