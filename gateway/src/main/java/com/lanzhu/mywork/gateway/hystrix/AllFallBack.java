package com.lanzhu.mywork.gateway.hystrix;

import com.lanzhu.mywork.master.error.ErrorCode;
import com.lanzhu.mywork.master.model.ApiResult;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;

@Log4j2
@RestController
public class AllFallBack {

    @RequestMapping(value = "/hystrix-fallback", method = RequestMethod.GET)
    public ApiResult<?> allFallBack(ServerWebExchange serverWebExchange) {
        ServerHttpRequest request = serverWebExchange.getRequest();
        String path = null;
        String method = null;
        if (request != null) {
            path = request.getURI().getPath();
            method = request.getMethodValue();
        }
        log.warn("hystrix fall back error. path:{} method:{}", path, method);
        return ApiResult.getFail(ErrorCode.SYS_ERROR, "service not available");
    }
}
