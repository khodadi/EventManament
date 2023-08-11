package com.security;

import com.basedata.CodeException;
import com.form.OutputAPIForm;
import com.service.services.IUserGeneralSrv;
import com.utility.InfraSecurityUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;
import java.util.Optional;

/**
 * @Creator 8/9/2023
 * @Project IntelliJ IDEA
 * @Author k.khodadi
 **/

@Slf4j
public class CustomAuthenticationFilter extends OncePerRequestFilter {

    private final IUserGeneralSrv userGeneralSrv;

    public CustomAuthenticationFilter(IUserGeneralSrv userGeneralSrv) {
        this.userGeneralSrv = userGeneralSrv;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        String authorizationHeader = httpServletRequest.getHeader(HttpHeaders.AUTHORIZATION);
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            try {
                String token = authorizationHeader.substring("Bearer ".length());
                //--------------- validate token itself
                OutputAPIForm<UserSecurity> obj = userGeneralSrv.getUserToken(token);
                if(Objects.nonNull(obj)){
                    log.info("*********** token found : " + obj.getData().toString());
                    Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
                    log.info("*********** token is valid!");
//                    (UserSecurity)obj.getData()forEach(item->{
//                        authorities.add(new SimpleGrantedAuthority(item));
//                    });
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
