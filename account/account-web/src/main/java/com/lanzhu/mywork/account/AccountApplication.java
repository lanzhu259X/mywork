package com.lanzhu.mywork.account;

import com.lanzhu.mywork.master.common.constant.CommonConstant;
import com.lanzhu.mywork.master.common.utils.IpUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * description:
 *
 * @author lanzhu259X
 * @date 2018-12-22
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class AccountApplication {

    public static void main(String[] args) {
        System.setProperty(CommonConstant.LOCAL_IP, IpUtils.getIp());
        SpringApplication.run(AccountApplication.class, args);
    }
}
