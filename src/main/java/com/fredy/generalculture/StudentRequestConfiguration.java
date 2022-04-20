package com.fredy.generalculture;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class StudentRequestConfiguration {

    @Bean
    ReactiveRedisOperations<String, StudentRequest> redisOperations(ReactiveRedisConnectionFactory factory) {
        Jackson2JsonRedisSerializer<StudentRequest> serializer = new Jackson2JsonRedisSerializer<>(StudentRequest.class);

        RedisSerializationContext.RedisSerializationContextBuilder<String, StudentRequest> builder = RedisSerializationContext.newSerializationContext(new StringRedisSerializer());

        RedisSerializationContext<String, StudentRequest> context = builder.value(serializer).build();

        return new ReactiveRedisTemplate<>(factory, context);
    }
}
