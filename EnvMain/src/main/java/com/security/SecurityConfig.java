package com.security;


import com.basedata.exceptions.CustomAccessDeniedException;
import com.service.services.IMessageBundleSrv;
import com.service.services.IUserGeneralSrv;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.bind.BindResult;
import org.springframework.boot.context.properties.bind.Bindable;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
import java.util.ArrayList;
import java.util.List;

/**
 * @Creator 8/9/2023
 * @Project IntelliJ IDEA
 * @Author k.khodadi
 **/


@EnableWebSecurity
@RequiredArgsConstructor
@Configuration
public class SecurityConfig  extends WebSecurityConfigurerAdapter {
    private final Environment environment;
    private final ApplicationContext context;
    private final IMessageBundleSrv messageBundleSrv;

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
        http.authorizeRequests().antMatchers(HttpMethod.POST,"/eventmanagment/api/v1/occasion/save").hasRole("ordinary");
        http.exceptionHandling().accessDeniedHandler(new CustomAccessDeniedException(messageBundleSrv));

        http.authorizeRequests().anyRequest().authenticated();
        http.addFilterBefore(new CustomAuthenticationFilter(getApplicationContext().getBean(IUserGeneralSrv.class)), BasicAuthenticationFilter.class);
    }
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