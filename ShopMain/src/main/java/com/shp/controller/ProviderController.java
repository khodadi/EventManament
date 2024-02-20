package com.shp.controller;

import com.basedata.generalcode.CodeException;
import com.form.OutputAPIForm;
import com.service.services.IMessageBundleSrv;
import com.shp.service.dto.ProviderDto;
import com.shp.service.services.IProviderSrv;
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
 * @Creator 2/20/2024
 * @Project IntelliJ IDEA
 * @Author k.khodadi
 **/


@RestController
@RequestMapping("/api/v1/provider")
@RequiredArgsConstructor
@Slf4j
public class ProviderController {

    private IProviderSrv providerSrv;
    private IMessageBundleSrv messageBundleSrv;

    @Autowired
    public ProviderController(IProviderSrv providerSrv, IMessageBundleSrv messageBundleSrv) {
        this.providerSrv = providerSrv;
        this.messageBundleSrv = messageBundleSrv;
    }

    @PostMapping("/add")
    public ResponseEntity<OutputAPIForm> listOfOccasionType(@RequestBody ProviderDto provider){
        OutputAPIForm<ProviderDto> retVal = new OutputAPIForm();

        try{
//            retVal = providerSrv.getAllOccasionTypes();
        }catch (Exception e){
            log.error("Error in save user",e);
            retVal.setSuccess(false);
            retVal.getErrors().add(CodeException.SYSTEM_EXCEPTION);
        }
        messageBundleSrv.createMsg(retVal);
        return ResponseEntity.ok().body(retVal);
    }
}
