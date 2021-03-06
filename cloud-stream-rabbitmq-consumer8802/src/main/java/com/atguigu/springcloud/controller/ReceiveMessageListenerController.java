package com.atguigu.springcloud.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

/**
 * @Description ReceiveMessageListenerController
 * @Author pangh
 * @Date 2020年04月01日 10:03 上午
 * @Version v1.0.0
 */
@Component
@EnableBinding(Sink.class) // 消费者使用 Sink
@Slf4j
public class ReceiveMessageListenerController {

    @Value("${server.port}")
    private String serverPort;

    @StreamListener(Sink.INPUT)
    public void input(Message<String> message) {
        log.info("消费者1，-------" + message.getPayload() + "\t port:" + serverPort);
    }

}