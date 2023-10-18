package com.fengxi.auth.service.impl;

import com.alibaba.fastjson.JSON;
import com.fengxi.auth.dto.CommonSettingDTO;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * WebSocketServer服务类(建议关于webstocket的业务层都写这里)
 */
@Log4j2
@Component
@ServerEndpoint("/notice/{userId}")
public class WebSocketServer {
    //    /**
//     * 解决无法注入bean：定义静态service对象，通过@Autowired在系统启动时为静态变量赋值
//     * @Autowired 注解作用在方法上，如果方法没有参数，spring容器会在类加载完后执行一次这个方法，
//     * 如果方法中有参数的话，还会从容器中自动注入这个方法的参数，然后执行一次这个方法。
//     */
//    public static XxService xxService;
//
//    @Autowired
//    public void setXxService(XxService xxService){
//        WebSocketServer.xxService = xxService;
//    }
    public static StringRedisTemplate stringRedisTemplate;

    public static CommonSettingDTO commonSettingDTO;

    @Autowired
    public void setStringRedisTemplate(StringRedisTemplate stringRedisTemplate) {
        WebSocketServer.stringRedisTemplate = stringRedisTemplate;
    }

    @Autowired
    public void setCommonSettingDTO(CommonSettingDTO commonSettingDTO) {
        WebSocketServer.commonSettingDTO = commonSettingDTO;
    }

    //存储客户端session信息
    public static Map<String, Session> clients = new ConcurrentHashMap<>();

    //存储把不同用户的客户端session信息集合
    public static Map<String, Set<String>> connection = new ConcurrentHashMap<>();

    //会话id
    private String sid = null;

    //建立连接的用户id
    private String userId;

    /**
     * @description: 当与前端的websocket连接成功时，执行该方法
     * @PathParam 获取ServerEndpoint路径中的占位符信息类似 控制层的 @PathVariable注解
     **/
    @OnOpen
    public void onOpen(Session session, @PathParam("userId") String userId) {
        this.sid = UUID.randomUUID().toString();
        this.userId = userId;
        clients.put(this.sid, session);
        //判断该用户是否存在会话信息，不存在则添加
        Set<String> clientSet = connection.get(userId);
        if (clientSet == null) {
            clientSet = new HashSet<>();
            connection.put(userId, clientSet);
        }
        clientSet.add(this.sid);
        log.info(this.userId + "用户建立连接，" + this.sid + "连接开启！");
    }

    /**
     * @description: 当连接失败时，执行该方法
     **/
    @OnClose
    public void onClose() {
        clients.remove(this.sid);
        // 删除connection内的数据
        //判断该用户是否存在会话信息，不存在则添加
        Set<String> clientSet = connection.get(userId);
        if (Objects.nonNull(clientSet)) {
            clientSet.remove(this.sid);
            if (clientSet.size() == 0)
                connection.remove(userId);
        }

        log.info(this.sid + "连接断开");
    }

    /**
     * @description: 当连接发生错误时，执行该方法
     **/
    @OnError
    public void onError(Throwable error) {
        log.info("系统错误");
        error.printStackTrace();
    }


    /**
     * @description: 当收到前台发送的消息时，执行该方法
     **/
    @OnMessage
    public void onMessage(String message, Session session) {
        log.info("收到来自用户：" + this.userId + "的信息   " + message);
        //判断该次请求的消息类型是心跳检测还是获取信息
//        sendMessageByUserId(userId, message);
    }

    /**
     * @description: 通过userId向用户发送信息
     * 该类定义成静态可以配合定时任务实现定时推送
     **/
    public static void sendMessageByUserId(String userId, String message) {
        if (!StringUtils.isEmpty(userId)) {
            Set<String> clientSet = connection.get(userId);
            //用户是否存在客户端连接
            if (Objects.nonNull(clientSet)) {
                Iterator<String> iterator = clientSet.iterator();
                while (iterator.hasNext()) {
                    String sid = iterator.next();
                    Session session = clients.get(sid);
                    //向每个会话发送消息
                    if (Objects.nonNull(session)) {
                        try {
                            String jsonString = JSON.toJSONString(message);
                            //同步发送数据，需要等上一个sendText发送完成才执行下一个发送
                            session.getBasicRemote().sendText(message);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }

    /**
     * @param userId
     * @param message
     * @description: 通过userId向用户发送信息 传入redis(建议发信息到前端都使用这个)
     */
    public static void sendMessageByUserIdForRedis(String userId, String message) {
        HashMap<String, Object> hashMap = new HashMap<String, Object>();
        hashMap.put("id", userId);
        hashMap.put("message", message);
        stringRedisTemplate.convertAndSend(commonSettingDTO.getRedisWebStocketChanne(), JSON.toJSONString(hashMap));
    }

}
