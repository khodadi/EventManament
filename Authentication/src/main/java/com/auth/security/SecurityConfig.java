package com.auth.security;


import com.auth.service.services.IEvnUsersSrv;
import com.auth.dao.repository.IEnvUserTokenRepo;
import com.auth.security.filter.CustomAuthenticationFilter;
import com.auth.security.filter.CustomAuthorizationFilter;
import com.service.services.IMessageBundleSrv;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;

import static org.springframework.security.config.Customizer.withDefaults;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final UserDetailsService userDetailsService;

    private final CustomAuthorizationFilter customAuthorizationFilter;

    private final IEvnUsersSrv evnUsersSrv;

    private final IEnvUserTokenRepo envUserTokenRepo;

    private final PasswordEncoder bCryptPasswordEncoder;

    private final IMessageBundleSrv messageBundleSrv;

    private final LocaleResolver localeResolver;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.csrf(AbstractHttpConfigurer::disable);
        http.authorizeHttpRequests(auth ->{
                            auth.requestMatchers(
                                    "/api/v1/**",
                                    "/api/v1/user/guest/forgot-password/**",
                                    "/api/v1/auth/get-captcha",
                                    "/api/v1/auth/register/**",
                                    "/api/v1/auth/login/**",
                                    "/api/v1/auth/**",
                                    "/api/v1/user",
                                    "/api/v1/auth/module-login",
                                    "/api/v1/auth/index.html/**",
                                    "/api/v1/auth/swagger-ui/**",
                                    "/authentication/**",
                                    "/api/login",
                                    "/api/token/refresh",
                                    "/api/v1/user/save",
                                    "/favicon.ico").permitAll();
                            auth.anyRequest().authenticated();
                        }
                )
                .httpBasic(withDefaults());
        CustomAuthenticationFilter customAuthenticationFilter = new CustomAuthenticationFilter(authenticationManager(), evnUsersSrv,localeResolver);
        customAuthenticationFilter.setFilterProcessesUrl("/api/login");
        http.addFilter(customAuthenticationFilter);
        http.addFilterBefore(customAuthorizationFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(){
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(evnUsersSrv.userDetailsService());
        authProvider.setPasswordEncoder(bCryptPasswordEncoder);
        return new ProviderManager(authProvider);
    }

}
