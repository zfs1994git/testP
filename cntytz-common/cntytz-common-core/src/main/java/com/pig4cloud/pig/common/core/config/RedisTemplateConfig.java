/*
 *
 *  *  Copyright (c) 2019-2020, 冷冷 (wangiegie@gmail.com).
 *  *  <p>
 *  *  Licensed under the GNU Lesser General Public License 3.0 (the "License");
 *  *  you may not use this file except in compliance with the License.
 *  *  You may obtain a copy of the License at
 *  *  <p>
 *  * https://www.gnu.org/licenses/lgpl.html
 *  *  <p>
 *  * Unless required by applicable law or agreed to in writing, software
 *  * distributed under the License is distributed on an "AS IS" BASIS,
 *  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  * See the License for the specific language governing permissions and
 *  * limitations under the License.
 *
 */

package com.pig4cloud.pig.common.core.config;

import com.google.common.collect.Maps;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.RedisCache;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.*;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;
import java.util.Map;

/**
 * @author lengleng
 * @date 2019/2/1
 * Redis 配置类
 */
@Slf4j
@EnableCaching
@Configuration
@RequiredArgsConstructor
@AutoConfigureBefore(RedisAutoConfiguration.class)
public class RedisTemplateConfig {
    private final RedisConnectionFactory factory;

    @Bean
    public RedisTemplate<String, Object> redisTemplate() {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new JdkSerializationRedisSerializer());
        redisTemplate.setHashValueSerializer(new JdkSerializationRedisSerializer());
        redisTemplate.setConnectionFactory(factory);
        return redisTemplate;
    }

    @Bean
    public HashOperations<String, String, Object> hashOperations(RedisTemplate<String, Object> redisTemplate) {
        return redisTemplate.opsForHash();
    }

    @Bean
    public ValueOperations<String, String> valueOperations(RedisTemplate<String, String> redisTemplate) {
        return redisTemplate.opsForValue();
    }

    @Bean
    public ListOperations<String, Object> listOperations(RedisTemplate<String, Object> redisTemplate) {
        return redisTemplate.opsForList();
    }

    @Bean
    public SetOperations<String, Object> setOperations(RedisTemplate<String, Object> redisTemplate) {
        return redisTemplate.opsForSet();
    }

    @Bean
    public ZSetOperations<String, Object> zSetOperations(RedisTemplate<String, Object> redisTemplate) {
        return redisTemplate.opsForZSet();
    }

    @Primary
    @Bean("cacheManager")
    public CacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {
        final RedisCacheConfiguration defaultRedisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofSeconds(3600));
        final RedisCacheWriter redisCacheWriter = RedisCacheWriter.nonLockingRedisCacheWriter(redisConnectionFactory);
        Map<String, RedisCacheConfiguration> cacheConfigurations = Maps.newHashMap();
        cacheConfigurations.put("base:test", RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofSeconds(1800)));
        cacheConfigurations.put("base:test2", defaultRedisCacheConfiguration);

//        RedisCacheConfiguration saasBaseCacheConfig = RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofSeconds(7200));
//        cacheConfigurations.put("baseInfo:tableDetail", saasBaseCacheConfig.serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new Jackson2JsonRedisSerializer<>(Object.class))));
//        cacheConfigurations.put("baseInfo:orgDetail", saasBaseCacheConfig.serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new Jackson2JsonRedisSerializer<>(Object.class))));

        RedisCacheManager cacheManager = new RedisCacheManager(redisCacheWriter, defaultRedisCacheConfiguration, cacheConfigurations, true) {
            /**
             * 重写createRedisCache的方法，使用自定义的redisCache实现
             * 解决在使用@Cacheable注解时出现redis异常，导致影响正常业务流程的问题
             */
            @Override
            protected RedisCache createRedisCache(String name, RedisCacheConfiguration cacheConfig) {
                return new RedisCache(name, redisCacheWriter, cacheConfig != null ? cacheConfig : defaultRedisCacheConfiguration) {
                    /**
                     * 重写lookup方法，处理异常
                     */
                    @Override
                    protected Object lookup(Object key) {
                        try {
                            return super.lookup(key);
                        } catch (Exception e) {
                            log.error("redis cache [lookup] exception: {}", e.getMessage());
                            return null;
                        }
                    }

                    /**
                     * 重写put方法，处理异常
                     */
                    @Override
                    public void put(Object key, Object value) {
                        try {
                            super.put(key, value);
                        } catch (Exception e) {
                            log.error("redis cache [put] exception: {}", e.getMessage());
                        }
                    }
                };
            }
        };
        cacheManager.setTransactionAware(false);
        log.info("Init CacheManager: [{}]", cacheManager.toString());
        return cacheManager;
    }
}
