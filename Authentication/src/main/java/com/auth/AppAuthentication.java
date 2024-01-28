package com.auth;

import com.utility.InfraSecurityUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.weaver.bcel.Utility;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import java.util.Locale;

/**
 * @Creator 5/7/2023
 * @Project IntelliJ IDEA
 * @Author k.khodadi
 **/

@Slf4j
@EnableDiscoveryClient
@SpringBootApplication
@ComponentScan(basePackages = "com")
public class AppAuthentication extends SpringBootServletInitializer {
    public static void main(String[] args) {
        SpringApplication.run(AppAuthentication.class, args);
    }
    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }


    @Bean
    public LocaleResolver localeResolver() {
        SessionLocaleResolver slr = new SessionLocaleResolver();
        slr.setDefaultLocale(new Locale("fa"));
        return slr;
    }



}
