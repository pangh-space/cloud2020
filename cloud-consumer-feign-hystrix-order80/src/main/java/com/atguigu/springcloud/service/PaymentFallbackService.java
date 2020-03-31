package com.atguigu.springcloud.service;

import org.springframework.stereotype.Component;

/**
 * @Description TODO
 * @Author pangh
 * @Date 2020年03月31日 8:50 上午
 * @Version v1.0.0
 */
@Component
public class PaymentFallbackService implements PaymentHystrixService {
    @Override
    public String paymentInfo_OK(Integer id) {
        return "----PaymentFallbackService fall back--paymentInfo_OK";
    }

    @Override
    public String paymentInfo_TimeOut(Integer id) {
        return "----PaymentFallbackService fall back--paymentInfo_TimeOut";
    }
}