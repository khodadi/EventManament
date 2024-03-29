package com.auth.security.filter;

import com.auth.security.UserSecurity;
import com.auth.service.services.IEvnUsersSrv;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.basedata.generalcode.CodeException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.form.OutputAPIForm;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.LocaleResolver;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.Date;
import java.util.Base64;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

import static com.utility.InfraSecurityUtils.generateResponse;

@Slf4j
public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    public static Long EXPIRE_TOKEN = 3 * 60 * 60 * 1000L;
    public static Long EXPIRE_REFRESH_TOKEN = 6 * 60 * 60 * 1000L;
    public static String defaultLocaleConfig = "fa";

    private AuthenticationManager authenticationManager;
    private IEvnUsersSrv evnUsersSrv;
    private LocaleResolver localeResolver;

    public CustomAuthenticationFilter(AuthenticationManager authenticationManager,
                                      IEvnUsersSrv evnUsersSrv,
                                      LocaleResolver localeResolver) {

        this.authenticationManager = authenticationManager;
        this.evnUsersSrv = evnUsersSrv;
        this.localeResolver = localeResolver;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        Authentication retVal = null;
        try{
            String base64Credentials = request.getHeader("Authorization").substring("Basic".length()).trim();
            byte[] credDecoded = Base64.getDecoder().decode(base64Credentials);
            String credentials = new String(credDecoded, StandardCharsets.UTF_8);
            final String[] values = credentials.split(":", 2);
            String username = values[0];
            String password = values[1];
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username,password);
            retVal = authenticationManager.authenticate(authenticationToken);
        }catch (Exception e){
            generateResponse(response, CodeException.BAD_USER_PASS,true);
            retVal = null;
        }
        return retVal;
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException, ServletException {
        com.auth.security.UserSecurity user = (UserSecurity)authentication.getPrincipal();
        Algorithm algorithm = Algorithm.HMAC256("Secret".getBytes());
        String access_token = JWT.create()
                .withSubject(user.getEnvUser().getUserId().toString())
                .withExpiresAt(new Date(System.currentTimeMillis() + (EXPIRE_TOKEN)))
                .withIssuer(request.getRequestURL().toString())
                .withClaim("role",user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .sign(algorithm);
        String refresh_token = JWT.create()
                .withSubject(user.getEnvUser().getUserId().toString())
                .withExpiresAt(new Date(System.currentTimeMillis() + (EXPIRE_REFRESH_TOKEN)))
                .withIssuer(request.getRequestURL().toString())
//                .withClaim("role",user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .sign(algorithm);
//        response.setHeader("access_token",access_token);
//        response.setHeader("refresh_token",refresh_token);
        Map<String,String> tokens = new HashMap<>();
        tokens.put("access_token",access_token);
        tokens.put("refresh_token",refresh_token);

//        LocaleContextHolder.setLocale(new Locale(user.getEnvUser().getDefaultLocale()== null ? defaultLocaleConfig:user.getEnvUser().getDefaultLocale()));
        localeResolver.setLocale(request,response,new Locale(user.getEnvUser().getDefaultLocale()));
        OutputAPIForm outputAPIForm = evnUsersSrv.saveToken(user,tokens);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        if(outputAPIForm.isSuccess()){
            new ObjectMapper().writeValue(response.getOutputStream(),tokens);
        }else{
            new ObjectMapper().writeValue(response.getOutputStream(), "Problem in Write Token");
        }
    }
}
