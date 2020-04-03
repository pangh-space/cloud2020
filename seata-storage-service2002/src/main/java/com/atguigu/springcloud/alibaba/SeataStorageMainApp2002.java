package com.atguigu.springcloud.alibaba;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;


/**
 * @Description SeataStorageServiceApplication2002
 * @Author pangh
 * @Date 2020年04月03日 10:51 上午
 * @Version v1.0.0
 */
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@EnableDiscoveryClient
@EnableFeignClients
public class SeataStorageMainApp2002
{
    public static void main(String[] args)
    {
        SpringApplication.run(SeataStorageMainApp2002.class, args);
    }
}