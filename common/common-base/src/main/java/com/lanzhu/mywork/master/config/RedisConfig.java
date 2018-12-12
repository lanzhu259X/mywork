package com.lanzhu.mywork.master.config;

import com.alibaba.fastjson.support.spring.GenericFastJsonRedisSerializer;
import com.alibaba.fastjson.util.IOUtils;
import com.lanzhu.mywork.master.constant.CacheKeyCons;
import com.lanzhu.mywork.master.utils.RedisCacheUtils;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.annotation.DeclareWarning;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.DefaultRedisCachePrefix;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.integration.redis.util.RedisLockRegistry;
import org.springframework.session.data.redis.config.ConfigureRedisAction;

/**
 * description:
 *
 * @author lanzhu259X
 * @date 2018-09-22
 */
@ConditionalOnClass(RedisTemplate.class)
@Configuration
@EnableCaching
public class RedisConfig extends CachingConfigurerSupport {

    /** 前缀默认使用微服务名称 */
    @Value("${spring.application.name:}")
    private String cachePrefix;

    @ConditionalOnClass(ConfigureRedisAction.class)
    @Bean
    public ConfigureRedisAction configureRedisAction() {
        return ConfigureRedisAction.NO_OP;
    }

    @Bean
    @Override
    public KeyGenerator keyGenerator() {
        return (target, method, params) -> {
            StringBuilder sb = new StringBuilder();
            sb.append(target.getClass().getName());
            sb.append(".");
            sb.append(method.getName());
            for (Object obj : params) {
                if (obj != null) {
                    sb.append(":");
                    sb.append(obj.toString());
                }
            }
            return sb.toString();
        };
    }

    @Bean
    public CacheManager cacheManager(RedisTemplate<String, Object> redisTemplate) {
        CacheManager cacheManager = null;
        //设置前缀使用redis缓存
        if (StringUtils.isNotBlank(cachePrefix)) {
            RedisCacheManager redisCacheManager = new RedisCacheManager(redisTemplate);
            redisCacheManager.setCachePrefix(new DefaultRedisCachePrefix(cachePrefix + "."));
            redisCacheManager.setUsePrefix(true);
            cacheManager = redisCacheManager;
        } else {// 本地内存缓存
            cacheManager = new ConcurrentMapCacheManager();
        }
        return cacheManager;
    }

    @Bean
    public RedisLockRegistry createRedisLock(RedisConnectionFactory factory) {
        RedisLockRegistry lock = new RedisLockRegistry(factory, CacheKeyCons.REDIS_LOCK);
        RedisCacheUtils.setRedisLockRegistry(lock);
        return lock;
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {
        RedisSerializer<Object> serializer = new CustomizeGenericFastJsonRedisSerializer();
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        RedisTemplate<String, Object> template = new RedisTemplate<String, Object>();
        template.setConnectionFactory(factory);
        template.setValueSerializer(serializer);
        template.setHashValueSerializer(serializer);
        template.setKeySerializer(stringRedisSerializer);
        template.setDefaultSerializer(serializer);
        template.afterPropertiesSet();
        RedisCacheUtils.setRedisTemplate(template);
        return template;
    }

    public static class CustomizeGenericFastJsonRedisSerializer extends GenericFastJsonRedisSerializer {

        @Override
        public Object deserialize(byte[] bytes) throws SerializationException {
            Object object = null;
            try {
                object = super.deserialize(bytes);
            } catch (SerializationException e) {
                object = new String(bytes, IOUtils.UTF8);
            }
            return object;
        }

    }


}
