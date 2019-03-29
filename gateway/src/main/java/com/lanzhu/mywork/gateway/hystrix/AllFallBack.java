package com.lanzhu.mywork.gateway.hystrix;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Date;

@Log4j2
@RestController
public class AllFallBack {

    @RequestMapping(value = "/hystrix-fallback", method = RequestMethod.GET)
    public Mono<?> allFallBack(ServerWebExchange serverWebExchange) {
        ServerHttpRequest request = serverWebExchange.getRequest();
        JSONObject jsonObject = JSON.parseObject(JSON.toJSONString(request));
        String method = null;
        String remoteAddress = null;
        String uri = null;
        if (jsonObject != null) {
            JSONObject nativeRequest = jsonObject.getJSONObject("nativeRequest");
            if (nativeRequest != null) {
                method = nativeRequest.getString("method");
                remoteAddress = nativeRequest.getString("remoteAddress");
                uri = nativeRequest.getString("uRI");
            }
        }
        log.warn("hystrix fall back error. uri:{} method:{} remoteAddress:{}", uri, method, remoteAddress);
        JSONObject result = new JSONObject();
        result.put("status", 500);
        result.put("message", "server error");
        result.put("path", uri);
        result.put("time", new Date());
        return Mono.just(result);
    }
}
