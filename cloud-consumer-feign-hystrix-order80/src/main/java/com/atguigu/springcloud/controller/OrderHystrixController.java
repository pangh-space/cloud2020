package com.atguigu.springcloud.controller;

import com.atguigu.springcloud.service.PaymentHystrixService;
import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Description TODO
 * @Author pangh
 * @Date 2020年03月31日 7:45 上午
 * @Version v1.0.0
 */
@RestController
@Slf4j
// 配置全局通用的fallback
@DefaultProperties(defaultFallback = "payment_Global_FallbackMethod")
public class OrderHystrixController {

    @Resource
    private PaymentHystrixService paymentHystrixService;

    @GetMapping("/consumer/payment/hystrix/ok/{id}")
    public String paymentInfo_OK(@PathVariable("id") Integer id) {
        String result = paymentHystrixService.paymentInfo_OK(id);
        return result;
    }

    @GetMapping("/consumer/payment/hystrix/timeout/{id}")
    // 独享fallback 方法配置
//    @HystrixCommand(fallbackMethod = "paymentTimeOutFallbackMethod", commandProperties = {
//            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "1500")
//    })
    // 不配置详细，会使用统一的
    @HystrixCommand
    public String paymentInfo_TimeOut(@PathVariable("id") Integer id) {
        String result = paymentHystrixService.paymentInfo_TimeOut(id);
        return result;
    }

    /**
     * 私人定制fallback 方法
     *
     * @param id
     * @return
     */
    private String paymentTimeOutFallbackMethod(@PathVariable("id") Integer id) {
        return "消费者80，对方支付系统繁忙，或自己运行出错请检查自己，请10秒后再试。";
    }

    /**
     * 全局fallback方法
     *
     * @return
     */
    private String payment_Global_FallbackMethod() {
        return "Global异常处理信息,请稍后再试。";
    }
}