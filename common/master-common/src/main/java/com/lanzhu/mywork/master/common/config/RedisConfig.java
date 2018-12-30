package com.lanzhu.mywork.master.common.config;

import com.alibaba.fastjson.support.spring.GenericFastJsonRedisSerializer;
import com.alibaba.fastjson.util.IOUtils;
import com.lanzhu.mywork.master.common.utils.RedisCacheUtils;
import com.lanzhu.mywork.master.common.constant.CacheKeyCons;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.integration.redis.util.RedisLockRegistry;

/**
 * description:
 *
 * @author lanzhu259X
 * @date 2018-09-22
 */
@ConditionalOnClass(RedisTemplate.class)
@Configuration
public class RedisConfig {

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
