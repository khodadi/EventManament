package com.basedata.exceptions;

import com.basedata.generalcode.CodeException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.form.OutputAPIForm;
import com.service.services.IMessageBundleSrv;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import java.io.IOException;
import java.util.Locale;

/**
 * @Creator 8/9/2023
 * @Project IntelliJ IDEA
 * @Author k.khodadi
 **/


public class CustomAccessDeniedException implements AccessDeniedHandler {


    private final IMessageBundleSrv messageBundleSrv;



    public CustomAccessDeniedException(IMessageBundleSrv messageBundleSrv) {
        this.messageBundleSrv = messageBundleSrv;
    }

    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse response, AccessDeniedException e)  {
        OutputAPIForm dto = new OutputAPIForm();
        dto.getErrors().add(CodeException.ACCESS_DENIED);
        dto.setMessage(messageBundleSrv.getMessage("error.access_denied", new Locale("fa")));
        dto.setSuccess(false);
        response.setStatus(HttpStatus.FORBIDDEN.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        try {
            new ObjectMapper().writeValue(response.getOutputStream(), dto);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
