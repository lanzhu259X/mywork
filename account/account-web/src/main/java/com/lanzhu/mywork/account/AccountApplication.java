package com.lanzhu.mywork.account;

import com.lanzhu.mywork.master.common.constant.CommonConstant;
import com.lanzhu.mywork.master.common.utils.IpUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * description:
 *
 * @author lanzhu259X
 * @date 2018-12-22
 */
@ComponentScan(basePackages = {"com.lanzhu.mywork.account", "com.lanzhu.mywork.master.common"})
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@EnableAutoConfiguration
public class AccountApplication {

    public static void main(String[] args) {
        System.setProperty(CommonConstant.LOCAL_IP, IpUtils.getIp());
        SpringApplication.run(AccountApplication.class, args);
    }
}
