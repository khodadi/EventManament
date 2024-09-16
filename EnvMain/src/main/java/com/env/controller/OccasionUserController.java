package com.env.controller;

import com.basedata.generalcode.CodeException;
import com.env.basedata.StateRequest;
import com.env.service.dto.OccasionUsersDto;
import com.env.service.services.IOccasionSrv;
import com.form.OutputAPIForm;
import com.service.services.IMessageBundleSrv;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

/**
 * @Creator 9/16/2024
 * @Project IntelliJ IDEA
 * @Author k.khodadi
 **/

@RestController
@RequestMapping("/api/v1/occasion/user")
@RequiredArgsConstructor
@Slf4j
public class OccasionUserController {
    @Autowired
    private IOccasionSrv occasionSrv;
    @Autowired
    private IMessageBundleSrv messageBundleSrv;

    @PostMapping("")
    public ResponseEntity<OutputAPIForm> saveOccasionUser(@RequestBody OccasionUsersDto occasionUsers){
        OutputAPIForm<OccasionUsersDto> retVal = new OutputAPIForm();
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/v1/occasion/user/save").toUriString());
        try{
            occasionUsers.setStateRequest(StateRequest.Requested);
            retVal = occasionSrv.saveOccasionUsers(occasionUsers);
        }catch (Exception e){
            log.error("Error in save occasion user",e);
            retVal.setSuccess(false);
            retVal.getErrors().add(CodeException.SYSTEM_EXCEPTION);
        }
        messageBundleSrv.createMsg(retVal);
        return ResponseEntity.ok().body(retVal);
    }

    @PutMapping("/state/exchange")
    public ResponseEntity<OutputAPIForm> exchangeOccasionUserState(@RequestBody OccasionUsersDto occasionUsers){
        OutputAPIForm<OccasionUsersDto> retVal = new OutputAPIForm();
        try{
            retVal = occasionSrv.updateOccasionUser(occasionUsers);
        }catch (Exception e){
            log.error("Error in save occasion user",e);
            retVal.setSuccess(false);
            retVal.getErrors().add(CodeException.SYSTEM_EXCEPTION);
        }
        messageBundleSrv.createMsg(retVal);
        return ResponseEntity.ok().body(retVal);
    }

}
