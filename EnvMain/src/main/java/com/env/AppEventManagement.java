package com.env;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import java.util.Locale;

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
/*^^^^^^^^^^^^^^^*/
//@EnableRedisRepositories(basePackages = "")
@EnableScheduling
@ComponentScan(basePackages = "com")
public class AppEventManagement extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(AppEventManagement.class, args);
    }

    @Bean
    public LocaleResolver localeResolver() {
        SessionLocaleResolver slr = new SessionLocaleResolver();
        slr.setDefaultLocale(new Locale("fa"));
        return slr;
    }
}
