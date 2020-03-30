package com.atguigu.springcloud.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * @Description TODO
 * @Author pangh
 * @Date 2020年03月30日 11:45 上午
 * @Version v1.0.0
 */
@RestController
@RequestMapping("/payment")
public class PaymentController {

    @Value("${server.port}")
    private String SERVER_PORT;

    @RequestMapping("/zk")
    public String paymentZK() {
        return "com.com.springcloud with zookeeper :" + SERVER_PORT + "\t" + UUID.randomUUID().toString();
    }

}