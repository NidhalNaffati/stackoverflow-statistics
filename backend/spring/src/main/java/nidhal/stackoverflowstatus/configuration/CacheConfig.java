package nidhal.stackoverflowstatus.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;

import java.time.Duration;


@Slf4j
@Configuration
@EnableCaching
public class CacheConfig {

    @Value("${spring.data.redis.host}")
    private String REDIS_HOSTNAME;

    @Value("${spring.data.redis.port}")
    private int REDIS_PORT;

    @Value("${spring.data.redis.timeout}")
    private int REDIS_TIME_TO_LIVE;

    // Redis connection factory
    @Bean
    public LettuceConnectionFactory lettuceConnectionFactory() {
        log.info("REDIS_HOSTNAME: " + REDIS_HOSTNAME);
        log.info("REDIS_PORT: " + REDIS_PORT);
        log.info("REDIS_TIME_TO_LIVE: " + REDIS_TIME_TO_LIVE);

        RedisStandaloneConfiguration redisConfig = new RedisStandaloneConfiguration();
        redisConfig.setHostName(REDIS_HOSTNAME);
        redisConfig.setPort(REDIS_PORT);
        return new LettuceConnectionFactory(redisConfig);
    }

    // Redis template for working with Redis
    @Bean
    public RedisTemplate<String, Object> redisTemplate() {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(lettuceConnectionFactory());
        template.setKeySerializer(new JdkSerializationRedisSerializer()); // StringRedisSerializer
        template.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        return template;
    }

    // Redis cache manager
    @Bean
    public CacheManager cacheManager() {
        RedisCacheConfiguration cacheConfiguration = RedisCacheConfiguration
                .defaultCacheConfig()
                .entryTtl(Duration.ofSeconds(REDIS_TIME_TO_LIVE));

        return RedisCacheManager
                .builder(lettuceConnectionFactory())
                .cacheDefaults(cacheConfiguration)
                .build();
    }


    // Clear the cache
    public void clearCache() {
        log.info("Clearing cache...");
        cacheManager()
                .getCacheNames()
                .stream()
                .map(cacheManager()::getCache)
                .forEach(cache -> {
                    if (cache != null) {
                        cache.clear();
                    }
                });
    }

}
