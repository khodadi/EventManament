package com;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * @Creator 5/7/2023
 * @Project IntelliJ IDEA
 * @Author k.khodadi
 **/

@EnableEurekaServer
@SpringBootApplication
public class AppServiceRecovery {
    public static void main(String[] args) {
        SpringApplication.run(AppServiceRecovery.class, args);
    }
}

