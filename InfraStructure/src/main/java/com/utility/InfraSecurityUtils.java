package com.utility;

import com.basedata.generalcode.CodeException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.form.OutputAPIForm;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Creator 8/9/2023
 * @Project IntelliJ IDEA
 * @Author k.khodadi
 **/

@Slf4j
@Component
public class InfraSecurityUtils {

    public static final Long defaultUser = -1L;

    public static void generateResponse(HttpServletResponse response, CodeException codeException, boolean returnHttpStatus){
        OutputAPIForm<Object> retval = new OutputAPIForm<>();
        try {
            if(codeException != null) {
                retval.setSuccess(false);
                retval.getErrors().add(codeException);
                GeneralUtility.getMessageSrv().createMsg(retval);
            }
            if (returnHttpStatus){
                response.setStatus(getHttpCode(retval.getErrors()));
            }
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            new ObjectMapper().writeValue(response.getOutputStream(), retval);
        } catch (IOException e) {
            log.error("Error writeValue in ObjectMapper! caused by : " + e.getMessage());
        }
    }

    public static Long getCurrentUser(){
        Long retVal;
        try{
            retVal = Long.parseLong(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
            LocaleContextHolder.getLocale();
        }catch (Exception e){
            log.info("Current user is anonymous");
            retVal = defaultUser;
        }
        return retVal;
    }

    public static boolean checkLogin(){
        boolean retVal = false;
        try{
            if(!getCurrentUser().equals(defaultUser)){
                retVal = true;
            }
        }catch (Exception e){
            log.error("Error in get current user",e);
            retVal = false;
        }
        return retVal;
    }

    public static int getHttpCode(List<? extends CodeException> codeException){
        int result = 200;
        try {
            if (codeException != null && !codeException.isEmpty()){
                ArrayList<Integer> temp = new ArrayList<>();
                for (CodeException c : codeException) {
                    String httpCode = String.valueOf(c.getCodeException()).substring(0, 3);
                    temp.add(HttpStatus.valueOf(Integer.parseInt(httpCode)).value());
                }
                result = temp.get(0);
                for (int i = 1 ; i < temp.size() ; i++) {
                    if (result < temp.get(i))
                        result = temp.get(i);
                }
            }
        } catch (Exception e){
            result = 400;
            log.error(codeException + ":" + new Date() + " => " + e);
        }
        return result;
    }
}
