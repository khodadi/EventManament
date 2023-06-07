package com.notification.security.filter;

import com.infra.dto.Output;
import com.infra.exception.CodeException;
import com.infra.utils.MessageUtils;
import lombok.extern.slf4j.Slf4j;



import com.fasterxml.jackson.databind.ObjectMapper;
import com.infra.entity.UserRedisCache;
import com.infra.service.UserCacheService;
import com.infra.utils.InfraUtility;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@Slf4j
public class ValidateTokenFilter extends OncePerRequestFilter {
    Locale currentLocale = new Locale("fa");
    private final UserCacheService tokenCacheService;

    public ValidateTokenFilter(ApplicationContext ctx) {
        this.tokenCacheService= ctx.getBean(UserCacheService.class);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            try {
                String token = authorizationHeader.substring("Bearer ".length());
                //--------------- validate token itself
                Optional<UserRedisCache> obj = tokenCacheService.findByTokenId(token);
                log.info("\n *********** token found : " + obj.isPresent());
                if(obj.isPresent()  && InfraUtility.validateTokenDate(obj.get().getLengthExpire())){
                    Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
                    obj.get().getPermissions().forEach(item->{
                        authorities.add(new SimpleGrantedAuthority(item));
                    });
                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(obj.get(), null, authorities);
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }else{
                    log.error("info authentication failed for token");
                    generateResponse(response,"error.Authentication.notLogin",HttpServletResponse.SC_UNAUTHORIZED,CodeException.BAD_AUTHENTICATION);
                    return;
                }
                filterChain.doFilter(request, response);

            } catch (Exception e) {
                e.printStackTrace();
                log.error("Error Log in is {}", e.getMessage());
                generateResponse(response,"error.system_exception",HttpServletResponse.SC_UNAUTHORIZED,CodeException.BAD_AUTHENTICATION);
            }
        }else{
            filterChain.doFilter(request, response);
        }
    }

    private void generateResponse(HttpServletResponse response,String messageKey,int httpErrorCode,CodeException codeException ){
        Output dto = new Output();
        if(codeException !=null) {
            dto.setSuccess(false);
            dto.getErrors().add(codeException);
        }
        dto.setMessage( MessageUtils.getLocalizedMessage(messageKey, currentLocale));
        response.setStatus(httpErrorCode);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        try {
            new ObjectMapper().writeValue(response.getOutputStream(), dto);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
