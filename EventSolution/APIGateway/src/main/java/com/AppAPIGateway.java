package com;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

import java.net.InetAddress;


/**
 * @Creator 5/7/2023
 * @Project IntelliJ IDEA
 * @Author k.khodadi
 **/

@EnableEurekaClient
@SpringBootApplication
public class AppAPIGateway extends SpringBootServletInitializer {
    public static void main(String[] args){
        SpringApplication.run(AppAPIGateway.class);
        try{
            System.out.println(InetAddress.getLocalHost().getHostName());
        }catch (Exception e){
            e.printStackTrace();
        }

    }


}
