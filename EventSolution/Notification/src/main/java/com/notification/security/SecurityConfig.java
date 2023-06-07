package com.notification.security;


import com.infra.exception.exceptions.CustomAccessDeniedException;
import com.notification.security.filter.ValidateTokenFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.bind.Bindable;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Collections;

@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final Environment environment;



    public void configure(HttpSecurity http) throws Exception {
        http.cors();
        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.authorizeRequests().antMatchers("/soap/**", "/register/**", "/approve/**", "/forget/**", "/cdn/**", "/cus1/**",
                "/hystrix.stream", "/swagger-ui.html", "/webjars/springfox-swagger-ui/**", "/configuration/ui",
                "/swagger-resources", "/v3/api-docs/**", "/swagger-resources/configuration/ui",
                "/swagger-resources/configuration/security", "/css/**", "/js/**", "/images/jcaptcha",
                "/templates/doc/**",
                "/oauth/token",
                "/swagger-ui/**"
        ).permitAll();
        http.authorizeRequests().antMatchers(HttpMethod.GET,"/api/v1/setting/load").hasAnyAuthority("notification.setting.load");
        http.authorizeRequests().antMatchers(HttpMethod.POST,"/api/v1/setting/update").hasAnyAuthority("notification.setting.update");

        http.authorizeRequests().antMatchers(HttpMethod.POST,"/api/v1/request/load").hasAnyAuthority("notification.request.load");
        http.authorizeRequests().antMatchers(HttpMethod.POST,"/api/v1/request/save").hasAnyAuthority("notification.request.save");
        http.authorizeRequests().antMatchers(HttpMethod.PUT,"/api/v1/request/update").hasAnyAuthority("notification.request.save");
        http.authorizeRequests().antMatchers(HttpMethod.DELETE,"/api/v1/request/delete").hasAnyAuthority("notification.request.delete");
        http.authorizeRequests().antMatchers(HttpMethod.POST,"/api/v1/request/cancelNotification").hasAnyAuthority("notification.request.cancelNotification");
        http.authorizeRequests().antMatchers(HttpMethod.POST,"/api/v1/request/pauseAndResume").hasAnyAuthority("notification.request.pauseAndResume");

        http.authorizeRequests().antMatchers(HttpMethod.POST,"/api/v1/dashboard/load").hasAnyAuthority("notification.dashboard.load");
        http.exceptionHandling().accessDeniedHandler(new CustomAccessDeniedException());

        http.authorizeRequests().anyRequest().authenticated();
        http.addFilterBefore(new ValidateTokenFilter(getApplicationContext()), BasicAuthenticationFilter.class);
    }


    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Binder.get(environment).bind("cors.allowed-origins", Bindable.listOf(String.class)).get());
        configuration.setAllowedMethods(Collections.singletonList("*"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }


}
