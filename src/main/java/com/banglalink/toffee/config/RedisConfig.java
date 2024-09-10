package com.banglalink.toffee.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.beans.factory.annotation.Value;

@Configuration
//@EnableRedisRepositories
public class RedisConfig {

    @Value("${spring.data.redis.host}")
    private String redisHostName;

    @Value("${spring.data.redis.port}")
    private int redisPort;

    @Value("${spring.data.redis.database}")
    private int redisDataBase;

    @Value("${spring.data.redis.password}")
    private String password;

    @Bean(name = "authRedisConnectionFactory")
    public JedisConnectionFactory authRedisConnectionFactory() {
        RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration();
        configuration.setHostName(redisHostName);
        //configuration.setPassword(password);
        configuration.setPort(redisPort);
        configuration.setDatabase(0);
        return new JedisConnectionFactory(configuration);
    }

    @Bean(name = "paymentRedisConnectionFactory")
    @Primary
    public JedisConnectionFactory paymentRedisConnectionFactory() {
        RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration();
        configuration.setHostName(redisHostName);
        //configuration.setPassword(password);
        configuration.setPort(redisPort);
        configuration.setDatabase(redisDataBase);
        return new JedisConnectionFactory(configuration);
    }

    @Bean(name = "authRedisTemplate")
    public RedisTemplate<String, Object> redisTemplateFirst() {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(authRedisConnectionFactory());
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new StringRedisSerializer());
        template.setEnableTransactionSupport(true);
        template.afterPropertiesSet();
        return template;
    }

    @Bean(name = "paymentRedisTemplate")
    public RedisTemplate<String, Object> template() {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(paymentRedisConnectionFactory());
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new StringRedisSerializer());
        template.setEnableTransactionSupport(true);
        template.afterPropertiesSet();
        return template;
    }

}