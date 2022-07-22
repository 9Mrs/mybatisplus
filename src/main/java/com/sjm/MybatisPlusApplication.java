package com.sjm;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//用于扫描mapper接口的包
@MapperScan("com.sjm.mapper")
@MapperScan("com.sjm.dao")
@MapperScan("com.sjm.utils")

public class MybatisPlusApplication {

    public static void main(String[] args) {
        SpringApplication.run(MybatisPlusApplication.class, args);
    }

}
