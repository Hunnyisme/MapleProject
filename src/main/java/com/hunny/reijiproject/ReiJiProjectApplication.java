package com.hunny.reijiproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class ReiJiProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(ReiJiProjectApplication.class, args);
    }

}
