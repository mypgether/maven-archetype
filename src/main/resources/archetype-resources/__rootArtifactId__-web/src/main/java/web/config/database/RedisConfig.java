package ${package}.web.config.database;

import java.io.Serializable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * redis的配置
 *
 * @author myp
 * @date 2019/5/29 10:14 AM
 */
@Configuration
public class RedisConfig {

  @Bean
  public RedisTemplate<String, Serializable> redisTemplate(
      LettuceConnectionFactory connectionFactory) {
    RedisTemplate<String, Serializable> redisTemplate = new RedisTemplate<>();
    redisTemplate.setKeySerializer(new StringRedisSerializer());
    redisTemplate.setValueSerializer(new StringRedisSerializer());
    redisTemplate.setHashKeySerializer(new StringRedisSerializer());
    redisTemplate.setHashValueSerializer(new StringRedisSerializer());
    redisTemplate.setConnectionFactory(connectionFactory);
    return redisTemplate;
  }
}