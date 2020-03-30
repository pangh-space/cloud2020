package com.atguigu.springcloud.config;

import feign.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Description Feign 日志配置类
 * @Author pangh
 * @Date 2020年03月30日 10:18 下午
 * @Version v1.0.0
 */
@Configuration
public class FeignConfig {

    /**
     * feignClient日志级别配置
     *
     * @return
     */
    @Bean
    public Logger.Level feignLoggerLevel() {
        // 请求和响应的头信息,请求和响应的正文及元数据
        return Logger.Level.FULL;
    }
}