package com.sky.skyaviation;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cache.annotation.EnableCaching;


@ServletComponentScan
@SpringBootApplication
@EnableCaching
public class SkyAviationApplication {

    public static void main(String[] args) {
        SpringApplication.run(SkyAviationApplication.class, args);
    }

}
