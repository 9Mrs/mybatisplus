package com.sjm;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.util.Collections;

public class FastAutoGeneratorTest {
    public static void main(String[] args) {
        FastAutoGenerator.create("jdbc:mysql://127.0.0.1:3306/testdb? characterEncoding=utf-8&userSSL=false", "root", "123456")
        .globalConfig(builder -> {builder.author("sjm").fileOverride().outputDir("F://mybatis_plus");

        })
                .packageConfig(builder -> {builder.parent("com").moduleName("sjm")
                .pathInfo(Collections.singletonMap(OutputFile.mapper,"F://mybatis_plus"));
                })
                .strategyConfig(builder -> {builder.addInclude("t_user")
                .addTablePrefix("t_","c_");
                })
                .templateEngine(new FreemarkerTemplateEngine()).execute();
    }

}
