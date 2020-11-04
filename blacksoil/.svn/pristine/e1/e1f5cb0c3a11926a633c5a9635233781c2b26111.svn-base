package com.ali.retail.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import redis.clients.jedis.Jedis;

import java.util.Set;

public class RedisConfig {

    //密码, 给了没用
    private static String REQUIRE_PASS = "kys_redis_db_lock";
    private static final Logger LOGGER = LoggerFactory.getLogger(RedisConfig.class);

    /**
     * 获取redis封装实例
     * @return
     */
    public static StringRedisTemplate stringRedisTemplate() {
        LOGGER.info("[ RedisConfig begin init StringRedisTemplate. ]");
        JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory();
        jedisConnectionFactory.setHostName("127.0.0.1");
        jedisConnectionFactory.setPort(6379);
        jedisConnectionFactory.afterPropertiesSet();
//        jedisConnectionFactory.setPassword(REQUIRE_PASS);
        StringRedisTemplate stringRedisTemplate = new StringRedisTemplate();
        stringRedisTemplate.setConnectionFactory(jedisConnectionFactory);
        stringRedisTemplate.afterPropertiesSet();
        return stringRedisTemplate;
    }

    public static void clearAll() {
        StringRedisTemplate redisTemplate = stringRedisTemplate();
        Set<String> keys = redisTemplate.keys("*");
        redisTemplate.delete(keys);
    }




    public static void main(String[] args) {

    }
}
