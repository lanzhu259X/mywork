package com.lanzhu.mywork.master.config;

import org.apache.http.client.HttpClient;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.ssl.SSLContexts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import java.security.GeneralSecurityException;
import java.security.KeyStore;
import java.util.concurrent.TimeUnit;

/**
 * description:
 *
 * @author lanzhu259X
 * @date 2018-09-22
 */
@Configuration
public class RestClientConfig {

    private static final String HTTP = "http";
    private static final String HTTPS = "https";

    @Value("${http.client.pool.default.max.per.route:150}")
    private int httpClientPoolDefaultMaxPerRoute = 150;
    @Value("${http.client.pool.max.total:300}")
    private int httpClientPoolMaxTotal = 300;

    @Bean
    public ClientHttpRequestFactory httpRequestFactory() {
        return new BufferingClientHttpRequestFactory(new HttpComponentsClientHttpRequestFactory(httpClient()));
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate(httpRequestFactory());
    }

    @Bean
    public HttpClient httpClient() {
        RegistryBuilder<ConnectionSocketFactory> registryBuilder =
                RegistryBuilder.<ConnectionSocketFactory>create();
        registryBuilder.register(HTTP, PlainConnectionSocketFactory.getSocketFactory());
        try {
            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            TrustStrategy trustStrategy = (x509Certificates, s) -> {
                //信任所有
                return true;
            };
            SSLContext sslContexts = SSLContexts.custom().loadTrustMaterial(keyStore, trustStrategy).build();
            HostnameVerifier hostnameVerifier = new NoopHostnameVerifier();
            SSLConnectionSocketFactory connectionSocketFactory =
                    new SSLConnectionSocketFactory(sslContexts, hostnameVerifier);
            registryBuilder.register(HTTPS, connectionSocketFactory);
        } catch (GeneralSecurityException e) {
            throw new RuntimeException(e);
        }

        Registry<ConnectionSocketFactory> registry = registryBuilder.build();
        //设置链接管理器
        PoolingHttpClientConnectionManager manager =
                new PoolingHttpClientConnectionManager(registry, null, null, null, 10, TimeUnit.MINUTES);
        manager.setDefaultMaxPerRoute(httpClientPoolDefaultMaxPerRoute);
        manager.setMaxTotal(httpClientPoolMaxTotal);
        //构建客户端
        return HttpClientBuilder.create().setConnectionManager(manager)
                .setConnectionManagerShared(true)
                .build();
    }
}
