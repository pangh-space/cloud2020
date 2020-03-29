package com.atguigu.springcloud.entitys;

import lombok.Data;

import java.io.Serializable;

/**
 * @Description TODO
 * @Author pangh
 * @Date 2020年03月29日 3:34 下午
 * @Version v1.0.0
 */
@Data
public class Payment implements Serializable {

    private Long id;
    private String serial;

}