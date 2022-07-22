package com.sjm.pojo;

import com.baomidou.mybatisplus.annotation.Version;
import lombok.Data;

@Data
public class Product {
    private Long id;
    private String name;
    private Integer price;
    @Version//标识乐观锁的版本号
    private Integer version;
}