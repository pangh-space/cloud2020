package com.atguigu.springcloud.alibaba.service;

/**
 * @Description StorageService
 * @Author pangh
 * @Date 2020年04月03日 10:58 上午
 * @Version v1.0.0
 */
public interface StorageService {
    /**
     * 扣减库存
     */
    void decrease(Long productId, Integer count);
}