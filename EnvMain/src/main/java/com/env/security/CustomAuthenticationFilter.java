package com.env.security;

import com.basedata.generalcode.CodeException;
import com.form.OutputAPIForm;
import com.security.UserSecurity;
import com.service.services.IUserGeneralSrv;
import com.utility.InfraSecurityUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.LocaleResolver;

import java.io.IOException;
import java.util.*;

/**
 * @Creator 8/9/2023
 * @Project IntelliJ IDEA
 * @Author k.khodadi
 **/

@Slf4j
public class CustomAuthenticationFilter extends OncePerRequestFilter {

    private final IUserGeneralSrv userGeneralSrv;

    private final LocaleResolver localeResolver;

    public CustomAuthenticationFilter(IUserGeneralSrv userGeneralSrv, LocaleResolver localeResolver) {
        this.userGeneralSrv = userGeneralSrv;
        this.localeResolver = localeResolver;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        String authorizationHeader = httpServletRequest.getHeader(HttpHeaders.AUTHORIZATION);
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            try {
                String token = authorizationHeader.substring("Bearer ".length());
                //--------------- validate token itself
                OutputAPIForm<UserSecurity> obj = userGeneralSrv.getUserToken(token);
                if(Objects.nonNull(obj) && obj.isSuccess()){
                    log.info("*********** token found : " + obj.getData().toString());
                    localeResolver.setLocale(httpServletRequest,httpServletResponse,new Locale(obj.getData().getEnvUser().getDefaultLocale()));
                    log.info("*********** token is valid!");
                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(obj.getData().getEnvUser().getUserId(), null, obj.getData().getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }else{
                    log.error("authentication failed! \ncaused by : token not found!");
                    InfraSecurityUtils.generateResponse(httpServletResponse, CodeException.EXPIRED_TOKEN, true);
                    return;
                }
                filterChain.doFilter(httpServletRequest, httpServletResponse);

            } catch (Exception e) {
                e.printStackTrace();
                log.error("error in validate token! \ncaused by : " + e);
                InfraSecurityUtils.generateResponse(httpServletResponse, CodeException.SYSTEM_EXCEPTION, false);
            }
        }else{
            filterChain.doFilter(httpServletRequest, httpServletResponse);
        }
    }
}
