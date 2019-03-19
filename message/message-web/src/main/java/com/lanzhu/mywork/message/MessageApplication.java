package com.lanzhu.mywork.message;

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
 * @date 2019-01-01
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@EnableAutoConfiguration
@ComponentScan(basePackages = {"com.lanzhu.mywork.message"})
public class MessageApplication {

    public static void main(String[] args) {
        System.setProperty(CommonConstant.LOCAL_IP, IpUtils.getIp());
        SpringApplication.run(MessageApplication.class, args);
    }
}
