package com.env.controller;

import com.basedata.generalcode.CodeException;
import com.env.service.dto.CriOccasionDto;
import com.env.service.dto.OccasionCostDto;
import com.env.service.services.IOccasionCostSrv;
import com.env.service.services.IOccasionSrv;
import com.form.OutputAPIForm;
import com.service.services.IMessageBundleSrv;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;

/**
 * @Creator 9/15/2024
 * @Project IntelliJ IDEA
 * @Author k.khodadi
 **/

@RestController
@RequestMapping("/api/v1/occasion/cost")
@RequiredArgsConstructor
@Slf4j
public class OccasionCostController {
    @Autowired
    private IOccasionCostSrv occasionCostSrv;
    @Autowired
    private IMessageBundleSrv messageBundleSrv;

    @GetMapping("")
    public ResponseEntity<OutputAPIForm> listOccasionCost(@RequestParam(required = false) Long page,
                                                          @RequestParam(required = false) Long occasionId){
        OutputAPIForm<ArrayList<OccasionCostDto>> retVal = new OutputAPIForm();
        try{
            retVal = occasionCostSrv.listOccasionCost(new CriOccasionDto(page == null ?0:page.intValue(),occasionId,"",null));
        }catch (Exception e){
            log.error("Error in save cost occasion",e);
            retVal.setSuccess(false);
            retVal.getErrors().add(CodeException.SYSTEM_EXCEPTION);
        }
        messageBundleSrv.createMsg(retVal);
        return ResponseEntity.ok().body(retVal);
    }
    @PostMapping("")
    public ResponseEntity<OutputAPIForm> saveOccasionCost(@RequestBody OccasionCostDto occasionCost){
        OutputAPIForm<OccasionCostDto> retVal = new OutputAPIForm();
        try{
            retVal = occasionCostSrv.saveOccasionCost(occasionCost);
        }catch (Exception e){
            log.error("Error in save cost occasion",e);
            retVal.setSuccess(false);
            retVal.getErrors().add(CodeException.SYSTEM_EXCEPTION);
        }
        messageBundleSrv.createMsg(retVal);
        return ResponseEntity.ok().body(retVal);
    }

    @PutMapping("")
    public ResponseEntity<OutputAPIForm> updateOccasionCost(@RequestBody OccasionCostDto occasionCost){
        OutputAPIForm<OccasionCostDto> retVal = new OutputAPIForm();
        try{
            retVal = occasionCostSrv.updateOccasionCost(occasionCost);
        }catch (Exception e){
            log.error("Error in save cost occasion",e);
            retVal.setSuccess(false);
            retVal.getErrors().add(CodeException.SYSTEM_EXCEPTION);
        }
        messageBundleSrv.createMsg(retVal);
        return ResponseEntity.ok().body(retVal);
    }

    @DeleteMapping("")
    public ResponseEntity deleteOccasionCost(@RequestBody OccasionCostDto occasionCost){
        OutputAPIForm retVal = new OutputAPIForm();
        try{
            retVal = occasionCostSrv.deleteOccasionCost(occasionCost);
        }catch (Exception e){
            log.error("Error in save cost occasion",e);
            retVal.setSuccess(false);
            retVal.getErrors().add(CodeException.SYSTEM_EXCEPTION);
        }
        messageBundleSrv.createMsg(retVal);
        return ResponseEntity.ok().body(retVal);
    }

}
