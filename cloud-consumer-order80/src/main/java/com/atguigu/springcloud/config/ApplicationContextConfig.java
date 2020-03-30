package com.atguigu.springcloud.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @Description 将RestTemplate 注入到Sring的容器中
 * @Author pangh
 * @Date 2020年03月29日 7:53 下午
 * @Version v1.0.0
 */
@Configuration
public class ApplicationContextConfig {

    /**
     * 通过 @LoadBalanced 注解，赋予restTemplate 负载均衡的能力
     * @return
     */
    @Bean
//    @LoadBalanced
    public RestTemplate getRestTemplate(){
        return new RestTemplate();
    }

}