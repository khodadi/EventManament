package com.env.security;


import com.basedata.exceptions.CustomAccessDeniedException;
import com.service.component.ApplicationContextProvider;
import com.service.services.IMessageBundleSrv;
import com.service.services.IUserGeneralSrv;
import com.service.services.MessageBundleSrv;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.bind.BindResult;
import org.springframework.boot.context.properties.bind.Bindable;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.security.config.Customizer.withDefaults;

/**
 * @Creator 8/9/2023
 * @Project IntelliJ IDEA
 * @Author k.khodadi
 **/


@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity
@Configuration
public class SecurityConfig {
    private final Environment environment;
    private final ApplicationContext context;
    private final IMessageBundleSrv messageBundleSrv;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.cors();
        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.authorizeHttpRequests(auth -> {
                    auth.requestMatchers(
                            "/api/v1/**",
                            "/api/v1/dashboard/index.html/**",
                            "/api/v1/dashboard/swagger-ui/**"
                    ).permitAll();
                    auth.anyRequest().authenticated();
                }
        );
        http.exceptionHandling().accessDeniedHandler(new CustomAccessDeniedException(ApplicationContextProvider.getApplicationContext().getBean(MessageBundleSrv.class)));
        http.addFilterBefore(new CustomAuthenticationFilter(ApplicationContextProvider.getApplicationContext().getBean(IUserGeneralSrv.class)), BasicAuthenticationFilter.class);
        http.httpBasic(withDefaults());
        http.headers().frameOptions().disable();
        return http.build();
    }

//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http.authorizeHttpRequests(auth -> {
//                    auth.requestMatchers(
//                            "/soap/**",
//                            "/register/**",
//                            "/approve/**",
//                            "/forget/**",
//                            "/cdn/**",
//                            "/cus1/**",
//                            "/hystrix.stream",
//                            "/swagger-ui.html",
//                            "/webjars/springfox-swagger-ui/**",
//                            "/configuration/ui",
//                            "/swagger-resources",
//                            "/v3/api-docs/**",
//                            "/swagger-resources/configuration/ui",
//                            "/swagger-resources/configuration/security",
//                            "/css/**",
//                            "/js/**",
//                            "/images/jcaptcha",
//                            "/templates/doc/**",
//                            "/oauth/token",
//                            "/swagger-ui/**"
//                    ).permitAll();
//                    auth.anyRequest().authenticated();
//                }
//        );
//
////        http.authorizeHttpRequests(auth -> {
////            auth.requestMatchers(
////                    "/eventmanagment/api/v1/occasion/save",
////                    "/eventmanagment/api/v1/occasion/update",
////                    "/eventmanagment/api/v1/occasion/pic/save",
////                    "/eventmanagment/api/v1/occasion/itinerary/detail/save",
////                    "/eventmanagment/api/v1/occasion/cost",
////                    "/eventmanagment/api/v1/occasion/cost",
////                    "/eventmanagment/api/v1/occasion/cost",
////                    "/eventmanagment/api/v1/occasion/cost",
////                    "/eventmanagment/api/v1/occasion/user/save",
////                    "/eventmanagment/api/v1/baseData/occasionType/list",
////                    "/eventmanagment/api/v1/baseData/occasionType/save",
////                    "/eventmanagment/api/v1/baseData/activity/list",
////                    "/eventmanagment/api/v1/baseData/activity/save",
////                    "/eventmanagment/api/v1/baseData/eventType/length",
////                    "/eventmanagment/api/v1/baseData/equipment/list",
////                    "/eventmanagment/api/v1/baseData/length",
////                    "/eventmanagment/api/v1/place/save",
////                    "/eventmanagment/api/v1/place/pic/save"
////            ).hasRole("ordinary");
////            auth.anyRequest().authenticated();
////        });
////        http.authorizeRequests().anyRequest().authenticated();
//        http.addFilterBefore(new CustomAuthenticationFilter(ApplicationContextProvider.getApplicationContext().getBean(IUserGeneralSrv.class)), BasicAuthenticationFilter.class);
//        http.httpBasic(withDefaults());
//        http.headers().frameOptions().disable();
//        return http.build();
//    }
//
//    public void configure(HttpSecurity http) throws Exception {
//
//        http.authorizeHttpRequests(auth -> {
//                    auth.requestMatchers(
//                            "/soap/**",
//                            "/register/**",
//                            "/approve/**",
//                            "/forget/**",
//                            "/cdn/**",
//                            "/cus1/**",
//                            "/hystrix.stream",
//                            "/swagger-ui.html",
//                            "/webjars/springfox-swagger-ui/**",
//                            "/configuration/ui",
//                            "/swagger-resources",
//                            "/v3/api-docs/**",
//                            "/swagger-resources/configuration/ui",
//                            "/swagger-resources/configuration/security",
//                            "/css/**",
//                            "/js/**",
//                            "/images/jcaptcha",
//                            "/templates/doc/**",
//                            "/oauth/token",
//                            "/swagger-ui/**"
//                    ).permitAll();
//                    auth.anyRequest().authenticated();
//                }
//        );
//
////        http.authorizeHttpRequests(auth -> {
////            auth.requestMatchers(
////                    "/eventmanagment/api/v1/occasion/save",
////                    "/eventmanagment/api/v1/occasion/update",
////                    "/eventmanagment/api/v1/occasion/pic/save",
////                    "/eventmanagment/api/v1/occasion/itinerary/detail/save",
////                    "/eventmanagment/api/v1/occasion/cost",
////                    "/eventmanagment/api/v1/occasion/cost",
////                    "/eventmanagment/api/v1/occasion/cost",
////                    "/eventmanagment/api/v1/occasion/cost",
////                    "/eventmanagment/api/v1/occasion/user/save",
////                    "/eventmanagment/api/v1/baseData/occasionType/list",
////                    "/eventmanagment/api/v1/baseData/occasionType/save",
////                    "/eventmanagment/api/v1/baseData/activity/list",
////                    "/eventmanagment/api/v1/baseData/activity/save",
////                    "/eventmanagment/api/v1/baseData/eventType/length",
////                    "/eventmanagment/api/v1/baseData/equipment/list",
////                    "/eventmanagment/api/v1/baseData/length",
////                    "/eventmanagment/api/v1/place/save",
////                    "/eventmanagment/api/v1/place/pic/save"
////            ).hasRole("ordinary");
////            auth.anyRequest().authenticated();
////        });
////        http.authorizeRequests().anyRequest().authenticated();
//        http.addFilterBefore(new CustomAuthenticationFilter(ApplicationContextProvider.getApplicationContext().getBean(IUserGeneralSrv.class)), BasicAuthenticationFilter.class);
//        http.httpBasic(withDefaults());
//        http.headers().frameOptions().disable();
//    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        BindResult<List<String>> origins = Binder.get(environment).bind("server.cors.origins", Bindable.listOf(String.class));
        BindResult<List<String>> methods = Binder.get(environment).bind("server.cors.methods", Bindable.listOf(String.class));
        configuration.setAllowedOrigins(origins.isBound() ? origins.get() : new ArrayList<>());
        configuration.setAllowedMethods(methods.isBound() ? methods.get() : new ArrayList<>());
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

}