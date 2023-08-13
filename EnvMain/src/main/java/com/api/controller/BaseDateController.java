package com.api.controller;

import com.basedata.CodeException;
import com.form.OutputAPIForm;
import com.service.dto.OccasionTypeDto;
import com.service.services.IMessageBundleSrv;
import com.service.services.IOccasionBaseSrv;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;

/**
 * @Creator 8/12/2023
 * @Project IntelliJ IDEA
 * @Author k.khodadi
 **/

@RestController
@RequestMapping("/api/v1/occasionType")
@RequiredArgsConstructor
@Slf4j
public class BaseDateController {

    @Autowired
    private IOccasionBaseSrv occasionBaseSrv;
    @Autowired
    private IMessageBundleSrv messageBundleSrv;

    @PostMapping("/list")
    public ResponseEntity<OutputAPIForm> saveUser(@RequestBody OccasionTypeDto occasionType){
        OutputAPIForm<ArrayList<OccasionTypeDto>> retVal = new OutputAPIForm();
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/v1/occasion/save").toUriString());
        try{
            retVal = occasionBaseSrv.getAllOccasionTypes();
        }catch (Exception e){
            log.error("Error in save user",e);
            retVal.setSuccess(false);
            retVal.getErrors().add(CodeException.SYSTEM_EXCEPTION);
        }
        messageBundleSrv.createMsg(retVal);
        return ResponseEntity.created(uri).body(retVal);
    }
}
