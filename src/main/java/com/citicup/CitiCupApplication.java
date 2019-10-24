package com.citicup;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//@MapperScan("com.citicup.dao")
public class CitiCupApplication {

    public static void main(String[] args) {
        SpringApplication.run(CitiCupApplication.class, args);
    }

}
