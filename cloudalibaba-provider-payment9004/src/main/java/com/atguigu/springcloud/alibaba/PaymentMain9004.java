package com.atguigu.springcloud.alibaba;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @Description PaymentMain9004
 * @Author pangh
 * @Date 2020年04月02日 8:41 下午
 * @Version v1.0.0
 */
@SpringBootApplication
@EnableDiscoveryClient
public class PaymentMain9004 {

    public static void main(String[] args) {
        SpringApplication.run(PaymentMain9004.class, args);
    }
}