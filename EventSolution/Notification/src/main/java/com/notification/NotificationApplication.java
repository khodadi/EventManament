package com.notification;

import com.externalservice.service.implsrv.PushNotificationService;
import com.externalservice.service.implsrv.SendEmailService;
import com.externalservice.service.implsrv.SendSmsService;
import com.externalservice.service.isrv.IPushNotificationService;
import com.externalservice.service.isrv.ISendEmailService;
import com.externalservice.service.isrv.ISendSmsService;
import com.infra.service.BeanCreatorService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;

@ComponentScan(basePackages = { "com.notification","com.infra"})
@EnableRedisRepositories(basePackages = {"com.infra.repo","com.notification.repo.irepo"})
@SpringBootApplication()
@EnableScheduling
@EnableDiscoveryClient
@EnableCircuitBreaker
public class NotificationApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(NotificationApplication.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return super.configure(builder);
	}


	@Bean("messageSource")
	public MessageSource messageSource() {
		ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
		messageSource.setBasename("classpath:messages_notification");
		messageSource.setDefaultEncoding("UTF-8");
		return messageSource;
	}

	@Bean("localMessageSource")
	public MessageSource localMessageSource() {
		ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
		messageSource.setBasename("classpath:messages_infra");
		messageSource.setDefaultEncoding("UTF-8");
		return messageSource;
	}

	@PostConstruct
	public void setMessageSources(){
		BeanCreatorService.setMessageSources(messageSource() , localMessageSource());
	}

	@Bean
	public RestTemplate getRestTemplate() {
		return new RestTemplate();
	}

	@Bean
	public ISendEmailService getEmailService(){
		return new SendEmailService();
	}

	@Bean
	public ISendSmsService getServiceSendSms(){
		return new SendSmsService();
	}

	@Bean
	public IPushNotificationService getPushNotificationService(RestTemplate  restTemplate){
		return new PushNotificationService(restTemplate);
	}

}
