package com.fengxi.auth.config;


import com.fengxi.auth.dto.CommonSettingDTO;
import com.fengxi.auth.listener.RedisChannelListener;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * redis消息队列配置
 */
@Component
public class RedisConfig {
    @Resource
    CommonSettingDTO commonSettingDTO;

    @Bean
    public MessageListenerAdapter messageListenerAdapter() {
        return new MessageListenerAdapter(new RedisChannelListener());
    }

    @Bean
    public RedisMessageListenerContainer container(RedisConnectionFactory connectionFactory, MessageListenerAdapter messageListenerAdapter) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        // 监听那个频道
        container.addMessageListener(messageListenerAdapter, new PatternTopic(commonSettingDTO.getRedisWebStocketChanne()));
        return container;
    }

}
