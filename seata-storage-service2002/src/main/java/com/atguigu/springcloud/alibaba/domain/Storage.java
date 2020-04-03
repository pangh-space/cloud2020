package com.atguigu.springcloud.alibaba.domain;

import lombok.Data;

/**
 * @Description Storage
 * @Author pangh
 * @Date 2020年04月03日 10:54 上午
 * @Version v1.0.0
 */
@Data
public class Storage {
    private Long id;

    /**
     * 产品id
     */
    private Long productId;

    /**
     * 总库存
     */
    private Integer total;

    /**
     * 已用库存
     */
    private Integer used;

    /**
     * 剩余库存
     */
    private Integer residue;

}