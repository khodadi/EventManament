package com.jwt;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @Creator 5/7/2023
 * @Project IntelliJ IDEA
 * @Author k.khodadi
 **/


//@EnableFeignClients
//@EnableZuulProxy
@Slf4j
@SpringBootApplication
/*for develop mode - don't delete*/
@EnableDiscoveryClient
@EnableCircuitBreaker
/*^^^^^^^^^^^^^^^*/
//@EnableRedisRepositories(basePackages = "")
@EnableScheduling
public class AppAuthentication extends SpringBootServletInitializer {
    public static void main(String[] args) {
        SpringApplication.run(AppAuthentication.class, args);
    }

}
