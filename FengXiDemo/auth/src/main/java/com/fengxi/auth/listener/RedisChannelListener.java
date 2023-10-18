package com.fengxi.auth.listener;

import com.alibaba.fastjson.JSON;
import com.fengxi.auth.dto.CommonSettingDTO;
import com.fengxi.auth.service.impl.WebSocketServer;
import com.fengxi.auth.utils.SpringContextUtils;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;

import java.util.HashMap;

/**
 * redis Pub/Sub消息队列监听
 */
public class RedisChannelListener implements MessageListener {

    CommonSettingDTO commonSettingDTO = SpringContextUtils.getBean("commonSettingDTO");

    @Override
    public void onMessage(Message message, byte[] pattern) {

        byte[] b_channel = message.getChannel();
        byte[] b_body = message.getBody();
        try {
            String channel = new String(b_channel);
            String body = new String(b_body);
            HashMap hashMap = JSON.parseObject(body, HashMap.class);
            if (channel.equals(commonSettingDTO.getRedisWebStocketChanne())) {
                WebSocketServer.sendMessageByUserId(hashMap.get("id").toString(), hashMap.get("message").toString());
            }
        } catch (Exception e) {
        }
    }
}
