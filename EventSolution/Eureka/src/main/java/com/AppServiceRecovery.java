package com;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * @author m.shahrestanaki @createDate 11/5/2022
 */

@EnableEurekaServer
@SpringBootApplication
public class AppServiceRecovery {
    public static void main(String[] args) {
        SpringApplication.run(AppServiceRecovery.class, args);
    }

}

