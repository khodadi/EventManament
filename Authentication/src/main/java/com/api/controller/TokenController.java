package com.api.controller;

import com.form.OutputAPIForm;
import com.basedata.generalcode.CodeException;
import com.service.services.IEvnUsersSrv;
import com.service.services.IMessageBundleSrv;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

/**
 * @Creator 8/9/2023
 * @Project IntelliJ IDEA
 * @Author k.khodadi
 **/

@RestController
@RequestMapping("/api/v1/token")
@RequiredArgsConstructor
@Slf4j
public class TokenController {

    @Autowired
    private IEvnUsersSrv evnUsersSrv;
    @Autowired
    private IMessageBundleSrv messageBundleSrv;

    @PostMapping("/user")
    public ResponseEntity<OutputAPIForm<UserDetails>> getUserByToken(@RequestParam(required = true) String token){
        OutputAPIForm<UserDetails> retVal = new OutputAPIForm();
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/v1/token/user").toUriString());
        try{
            retVal = evnUsersSrv.loadUserByToken(token);
        }catch (Exception e){
            log.error("Error in save user",e);
            retVal.setSuccess(false);
            retVal.getErrors().add(CodeException.SYSTEM_EXCEPTION);
        }
        messageBundleSrv.createMsg(retVal);
        return ResponseEntity.ok().body(retVal);
    }
}
