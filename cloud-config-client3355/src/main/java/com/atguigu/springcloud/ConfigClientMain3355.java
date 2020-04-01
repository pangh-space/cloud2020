package com.atguigu.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @Description 演示SpringCloudConfig 创建3355，3355Client端，配置文件修改了以后，需要手动通知更新
 * @Author pangh
 * @Date 2020年03月31日 8:01 下午
 * @Version v1.0.0
 */
@SpringBootApplication
@EnableEurekaClient
public class ConfigClientMain3355 {
    public static void main(String[] args) {
        SpringApplication.run(ConfigClientMain3355.class, args);
    }
}