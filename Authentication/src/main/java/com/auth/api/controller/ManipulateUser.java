package com.auth.api.controller;

import com.auth.service.dto.CriEnvUser;
import com.form.OutputAPIForm;
import com.basedata.generalcode.CodeException;
import com.auth.service.dto.EnvUserSaveDto;
import com.auth.service.services.IEvnUsersSrv;
import com.service.services.IMessageBundleSrv;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
@Slf4j
public class ManipulateUser {
    private IEvnUsersSrv iEvnUsersSrv;
    private IMessageBundleSrv messageBundleSrv;

    @Autowired
    public ManipulateUser(IEvnUsersSrv iEvnUsersSrv, IMessageBundleSrv messageBundleSrv) {
        this.iEvnUsersSrv = iEvnUsersSrv;
        this.messageBundleSrv = messageBundleSrv;
    }

    @PostMapping("/save")
    public ResponseEntity<OutputAPIForm> saveUser(@RequestBody EnvUserSaveDto user){
        OutputAPIForm retVal = new OutputAPIForm();
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/user/save").toUriString());
        try{
            retVal = iEvnUsersSrv.insertUser(user);
        }catch (Exception e){
            log.error("Error in save user",e);
            retVal.setSuccess(false);
            retVal.getErrors().add(CodeException.SYSTEM_EXCEPTION);
        }
        messageBundleSrv.createMsg(retVal);
        return ResponseEntity.created(uri).body(retVal);
    }

    @GetMapping("")
    public ResponseEntity<OutputAPIForm> listUsers(@RequestParam(required = false) String lastName,
                                                   @RequestParam(required = false) String mobileNumber,
                                                   @RequestParam(required = false) String userName){
        OutputAPIForm retVal = new OutputAPIForm();
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/user/save").toUriString());
        try{
            retVal = iEvnUsersSrv.listUsers(new CriEnvUser(0L,lastName,mobileNumber,userName));
        }catch (Exception e){
            log.error("Error in List user",e);
            retVal.setSuccess(false);
            retVal.getErrors().add(CodeException.SYSTEM_EXCEPTION);
        }
        messageBundleSrv.createMsg(retVal);
        return ResponseEntity.created(uri).body(retVal);
    }


}
