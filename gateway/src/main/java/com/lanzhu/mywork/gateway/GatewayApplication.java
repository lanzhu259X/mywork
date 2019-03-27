package com.lanzhu.mywork.gateway;

import com.lanzhu.mywork.gateway.utils.IpUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * description:
 *
 * @author lanzhu259X
 * @date 2018-12-14
 */
@SpringBootApplication
@EnableDiscoveryClient
public class GatewayApplication {

    public static void main(String[] args) {
        System.setProperty("local-ip", IpUtils.getIp());
        SpringApplication.run(GatewayApplication.class, args);
    }
}
