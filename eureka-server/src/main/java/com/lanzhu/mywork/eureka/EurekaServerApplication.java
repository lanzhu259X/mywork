package com.lanzhu.mywork.eureka;

import com.lanzhu.mywork.master.common.constant.CommonConstant;
import com.lanzhu.mywork.master.common.utils.IpUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * description:
 *
 * @author lanzhu259X
 * @date 2018-12-19
 */
@SpringBootApplication
@EnableEurekaServer
public class EurekaServerApplication {

    public static void main(String[] args) {
        System.setProperty(CommonConstant.LOCAL_IP, IpUtils.getIp());
        SpringApplication.run(EurekaServerApplication.class, args);
    }
}
