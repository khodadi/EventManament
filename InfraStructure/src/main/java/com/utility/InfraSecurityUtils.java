package com.utility;

import com.basedata.CodeException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.form.OutputAPIForm;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.result.Output;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * @Creator 8/9/2023
 * @Project IntelliJ IDEA
 * @Author k.khodadi
 **/

@Slf4j
@Component
public class InfraSecurityUtils {
    public static void generateResponse(HttpServletResponse response, CodeException codeException, boolean returnHttpStatus){
        OutputAPIForm<Object> retval = new OutputAPIForm<>();
        try {
            if(codeException != null) {
                retval.setSuccess(false);
                retval.getErrors().add(codeException);
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
        Long retVal = -1L;
        try{
            retVal = new Long(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
        }catch (Exception e){
            retVal = -1L;
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
