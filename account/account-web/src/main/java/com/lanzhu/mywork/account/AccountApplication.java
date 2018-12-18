package com.lanzhu.mywork.account;

import com.lanzhu.mywork.master.common.utils.IpUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author liliang1
 * @date 2018-12-17 10:13
 */
@ComponentScan(basePackages = {"com.lanzhu.mywork.account"})
@SpringBootApplication
@EnableFeignClients
@EnableDiscoveryClient
public class AccountApplication {

    public static void main(String[] args) {
        System.setProperty("local-ip", IpUtils.getIp());
        SpringApplication.run(AccountApplication.class, args);
    }
}
