package com.env.controller;

import com.basedata.generalcode.CodeException;
import com.env.service.dto.ActivityDto;
import com.env.service.dto.EquipmentDto;
import com.env.service.dto.EventDto;
import com.env.service.dto.OccasionTypeDto;
import com.env.service.services.IOccasionBaseSrv;
import com.form.OutputAPIForm;
import com.service.dto.*;
import com.service.services.IMessageBundleSrv;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.Locale;

/**
 * @Creator 8/12/2023
 * @Project IntelliJ IDEA
 * @Author k.khodadi
 **/

@RestController
@RequestMapping("/api/v1/baseData")
@RequiredArgsConstructor
@Slf4j
public class BaseDateController {

    private IOccasionBaseSrv occasionBaseSrv;
    private IMessageBundleSrv messageBundleSrv;

    @Autowired
    public BaseDateController(IOccasionBaseSrv occasionBaseSrv, IMessageBundleSrv messageBundleSrv) {
        this.occasionBaseSrv = occasionBaseSrv;
        this.messageBundleSrv = messageBundleSrv;
    }

    @PostMapping("/occasionType/list")
    public ResponseEntity<OutputAPIForm> listOfOccasionType(){
        OutputAPIForm<ArrayList<OccasionTypeDto>> retVal = new OutputAPIForm();
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/v1/occasionType/list").toUriString());
        try{
            retVal = occasionBaseSrv.getAllOccasionTypes();
        }catch (Exception e){
            log.error("Error in save user",e);
            retVal.setSuccess(false);
            retVal.getErrors().add(CodeException.SYSTEM_EXCEPTION);
        }
        messageBundleSrv.createMsg(retVal);
        return ResponseEntity.ok().body(retVal);
    }

    @PostMapping("/activity/list")
    public ResponseEntity<OutputAPIForm> listOfActivity(){
        OutputAPIForm<ArrayList<ActivityDto>> retVal = new OutputAPIForm();
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/v1/activity/List").toUriString());
        try{
            retVal = occasionBaseSrv.getAllActivity();
        }catch (Exception e){
            log.error("Error in save user",e);
            retVal.setSuccess(false);
            retVal.getErrors().add(CodeException.SYSTEM_EXCEPTION);
        }
        messageBundleSrv.createMsg(retVal);
        return ResponseEntity.ok().body(retVal);
    }

    @PostMapping("/eventType/list")
    public ResponseEntity<OutputAPIForm> listOfEventType(){
        OutputAPIForm<EventDto> retVal = new OutputAPIForm();
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/v1/eventType").toUriString());
        try{
            retVal = occasionBaseSrv.getAllEvent();
        }catch (Exception e){
            log.error("Error in save user",e);
            retVal.setSuccess(false);
            retVal.getErrors().add(CodeException.SYSTEM_EXCEPTION);
        }
        messageBundleSrv.createMsg(retVal);
        return ResponseEntity.ok().body(retVal);
    }

    @PostMapping("/equipment/list")
    public ResponseEntity<OutputAPIForm> listOfEquipment(){
        OutputAPIForm<EquipmentDto> retVal = new OutputAPIForm();
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/v1/equipment").toUriString());
        try{
            retVal = occasionBaseSrv.getAllEquipment();
        }catch (Exception e){
            log.error("Error in save user",e);
            retVal.setSuccess(false);
            retVal.getErrors().add(CodeException.SYSTEM_EXCEPTION);
        }
        messageBundleSrv.createMsg(retVal);
        return ResponseEntity.ok().body(retVal);
    }

    @GetMapping("/length")
    public ResponseEntity<OutputAPIForm> listOfBaseData(Locale locale){
        OutputAPIForm<ArrayList<BaseData>> retVal = new OutputAPIForm();
        try{
            retVal = occasionBaseSrv.getBaseData();
        }catch (Exception e){
            log.error("Error in save user",e);
            retVal.setSuccess(false);
            retVal.getErrors().add(CodeException.SYSTEM_EXCEPTION);
        }
        messageBundleSrv.createMsg(retVal);
        return ResponseEntity.ok().body(retVal);
    }
    @PostMapping("occasionType/save")
    public ResponseEntity<OutputAPIForm> saveOccasionType(@RequestBody OccasionTypeDto occasionTypeDto){
        OutputAPIForm retVal = new OutputAPIForm();
        try{
            retVal = occasionBaseSrv.saveOccasionType(occasionTypeDto);
        }catch (Exception e){
            log.error("Error in save user",e);
            retVal.setSuccess(false);
            retVal.getErrors().add(CodeException.SYSTEM_EXCEPTION);
        }
        messageBundleSrv.createMsg(retVal);
        return ResponseEntity.ok().body(retVal);
    }
    @PostMapping("activity/save")
    public ResponseEntity<OutputAPIForm> saveActivity(@RequestBody ActivityDto dto){
        OutputAPIForm retVal = new OutputAPIForm();
        try{
            retVal = occasionBaseSrv.saveActivity(dto);
        }catch (Exception e){
            log.error("Error in save user",e);
            retVal.setSuccess(false);
            retVal.getErrors().add(CodeException.SYSTEM_EXCEPTION);
        }
        messageBundleSrv.createMsg(retVal);
        return ResponseEntity.ok().body(retVal);
    }

}
