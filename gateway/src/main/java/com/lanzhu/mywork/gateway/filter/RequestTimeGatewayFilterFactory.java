package com.lanzhu.mywork.gateway.filter;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;

/**
 * 统计 uri 的请求时间
 */
@Log4j2
public class RequestTimeGatewayFilterFactory extends AbstractGatewayFilterFactory<RequestTimeGatewayFilterFactory.Config> {

    private static final String REQUEST_TIME_BEGIN = "requestTimeBegin";

    public RequestTimeGatewayFilterFactory() {
        super(Config.class);
    }

    @Override
    public List<String> shortcutFieldOrder() {
        return Arrays.asList("logRequestTime");
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
          exchange.getAttributes().put(REQUEST_TIME_BEGIN, System.currentTimeMillis());
          return chain.filter(exchange)
                  .then(
                          Mono.fromRunnable(() -> {
                              if (config.isLogRequestTime()) {
                                  Long startTime = exchange.getAttribute(REQUEST_TIME_BEGIN);
                                  if (startTime != null) {
                                      StringBuilder sb = new StringBuilder(exchange.getRequest().getURI().getRawPath())
                                              .append(": ")
                                              .append(System.currentTimeMillis() - startTime)
                                              .append("ms");
                                      log.info(sb.toString());
                                  }
                              }
                          })
                  );
        };
    }

    @Setter
    @Getter
    public static class Config {
        private boolean logRequestTime;
    }
}
