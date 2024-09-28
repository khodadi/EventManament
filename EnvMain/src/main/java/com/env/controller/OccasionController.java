package com.env.controller;

import com.basedata.generalcode.CodeException;
import com.env.basedata.SearchTypeEnum;
import com.env.service.dto.BaseOccasionDto;
import com.env.service.dto.CriOccasionDto;
import com.env.service.dto.OccasionDto;
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
import java.util.ArrayList;

@RestController
@RequestMapping("/api/v1/occasion")
@RequiredArgsConstructor
@Slf4j
public class OccasionController {

    @Autowired
    private IOccasionSrv occasionSrv;
    @Autowired
    private IMessageBundleSrv messageBundleSrv;

    @PostMapping("")
    public ResponseEntity<OutputAPIForm> saveOccasion(@RequestBody BaseOccasionDto occasion){
        OutputAPIForm retVal = new OutputAPIForm();
        try{
            retVal = occasionSrv.saveOccasion(occasion);
        }catch (Exception e){
            log.error("Error in save user",e);
            retVal.setSuccess(false);
            retVal.getErrors().add(CodeException.SYSTEM_EXCEPTION);
        }
        messageBundleSrv.createMsg(retVal);
        return ResponseEntity.ok().body(retVal);
    }

    @PutMapping("")
    public ResponseEntity<OutputAPIForm> updateOccasion(@RequestBody OccasionDto occasion){
        OutputAPIForm retVal = new OutputAPIForm();
        try{
            retVal = occasionSrv.editOccasion(occasion);
        }catch (Exception e){
            log.error("Error in save user",e);
            retVal.setSuccess(false);
            retVal.getErrors().add(CodeException.SYSTEM_EXCEPTION);
        }
        messageBundleSrv.createMsg(retVal);
        return ResponseEntity.ok().body(retVal);
    }

    @GetMapping(value = "")
    public ResponseEntity<OutputAPIForm<ArrayList<OccasionDto>>> listOccasion(@RequestParam(required = false) Long page,
                                                                              @RequestParam(required = false) Long occasionId,
                                                                              @RequestParam(required = false) SearchTypeEnum searchType){
        log.info(" List Occasion ");
        OutputAPIForm retVal = new OutputAPIForm();
        try{
            retVal = occasionSrv.listOccasion(new CriOccasionDto(page == null ?0:page.intValue(),occasionId,"",searchType));
        }catch (Exception e){
            log.error("Error in save user",e);
            retVal.setSuccess(false);
            retVal.getErrors().add(CodeException.SYSTEM_EXCEPTION);
        }
        messageBundleSrv.createMsg(retVal);
        return ResponseEntity.ok().body(retVal);
    }

}
