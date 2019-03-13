//package com.lanzhu.mywork.master.common.config;
//
//import com.lanzhu.mywork.master.common.ribbon.DiscoveryEnabledRule;
//import com.lanzhu.mywork.master.common.ribbon.MetadataAwareRule;
//import com.netflix.niws.loadbalancer.DiscoveryEnabledNIWSServerList;
//import org.springframework.beans.factory.config.ConfigurableBeanFactory;
//import org.springframework.boot.autoconfigure.AutoConfigureBefore;
//import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
//import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
//import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
//import org.springframework.cloud.netflix.ribbon.RibbonClientConfiguration;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Scope;
//
///**
// * description:
// *
// * @author lanzhu259X
// * @date 2018-12-25
// */
//@Configuration
//@ConditionalOnClass(DiscoveryEnabledNIWSServerList.class)
//@AutoConfigureBefore(RibbonClientConfiguration.class)
//@ConditionalOnProperty(value = "ribbon.filter.metadata.enabled")
//public class RibbonDiscoveryConfig {
//
//    @Bean
//    @ConditionalOnMissingBean
//    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
//    public DiscoveryEnabledRule metadataAwareRule() {
//        return new MetadataAwareRule();
//    }
//}
