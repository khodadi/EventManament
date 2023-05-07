package servicerecovery;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @Creator 5/7/2023
 * @Project IntelliJ IDEA
 * @Author k.khodadi
 **/

@EnableEurekaClient
@SpringBootApplication
public class AppServiceRecovery extends SpringBootServletInitializer {
    public static void main(String[] args) {
        SpringApplication.run(AppServiceRecovery.class);
    }
}
