package com.atguigu.springcloud.alibaba.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

/**
 * @Description 演示Sentinel 的一般限流规则
 * @Author pangh
 * @Date 2020年04月02日 3:37 下午
 * @Version v1.0.0
 */
@RestController
@Slf4j
public class FlowLimitController {
    /**
     * 流量控制测试方法
     * @return
     */
    @GetMapping("/testA")
    public String testA() {
        return "------testA";
    }

    /**
     * 流量控制测试方法
     * @return
     */
    @GetMapping("/testB")
    public String testB() {
        log.info(Thread.currentThread().getName() + "\t" + "...testB");
        return "------testB";
    }


    @GetMapping("/testD")
    public String testD() {
//        try { TimeUnit.SECONDS.sleep(1); } catch (InterruptedException e) { e.printStackTrace(); }
//        log.info("testD 测试RT");

        log.info("testD 异常比例");
        int age = 10 / 0;
        return "------testD";
    }

    @GetMapping("/testE")
    public String testE() {
        log.info("testE 测试异常数");
        int age = 10 / 0;
        return "------testE 测试异常数";
    }

    /**
     * 热点限流
     * 如果用到了热点限流，如果不加blockHandler，那么就会抛出异常页面。所以必须配置blockHandler的兜底方法
     *
     * @param p1
     * @param p2
     * @return
     */
    @GetMapping("/testHotKey")
    @SentinelResource(value = "testHotKey", blockHandler = "deal_testHotKey")
    public String testHotKey(@RequestParam(value = "p1", required = false) String p1,
                             @RequestParam(value = "p2", required = false) String p2) {
        //int age = 10/0;
        log.info("调用热点测试...");
        return "------testHotKey";
    }

    public String deal_testHotKey(String p1, String p2, BlockException exception) {
        return "------deal_testHotKey,o(╥﹏╥)o";  //sentinel系统默认的提示：Blocked by Sentinel (flow limiting)
    }

}