package com.api.controller;

import com.basedata.generalcode.CodeException;
import com.form.OutputAPIForm;
import com.service.dto.*;
import com.service.services.IItinerarySrv;
import com.service.services.IMessageBundleSrv;
import com.service.services.IOccasionSrv;
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
    @Autowired
    private IItinerarySrv iItinerarySrv;

    @PostMapping("/save")
    public ResponseEntity<OutputAPIForm> saveOccasion(@RequestBody BaseOccasionDto occasion){
        OutputAPIForm retVal = new OutputAPIForm();
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/v1/occasion/save").toUriString());
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

    @GetMapping("")
    public ResponseEntity<OutputAPIForm<ArrayList<OccasionDto>>> listOccasion(@RequestBody CriOccasionDto criOccasion){
        OutputAPIForm retVal = new OutputAPIForm();
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/v1/occasion").toUriString());
        try{
            retVal = occasionSrv.listOccasion(criOccasion);
        }catch (Exception e){
            log.error("Error in save user",e);
            retVal.setSuccess(false);
            retVal.getErrors().add(CodeException.SYSTEM_EXCEPTION);
        }
        messageBundleSrv.createMsg(retVal);
        return ResponseEntity.ok().body(retVal);
    }

    @PostMapping("/pic/save")
    public ResponseEntity<OutputAPIForm> saveOccasionPic(@RequestBody OccasionPicDto occasionPic){
        OutputAPIForm retVal = new OutputAPIForm();
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/v1/occasion/save").toUriString());
        try{
            retVal = occasionSrv.saveOccasionPic(occasionPic);
        }catch (Exception e){
            log.error("Error in save user",e);
            retVal.setSuccess(false);
            retVal.getErrors().add(CodeException.SYSTEM_EXCEPTION);
        }
        messageBundleSrv.createMsg(retVal);
        return ResponseEntity.ok().body(retVal);
    }

    @PostMapping("/cost/save")
    public ResponseEntity<OutputAPIForm> saveOccasionCost(@RequestBody OccasionCostDto occasionCost){
        OutputAPIForm<OccasionCostDto> retVal = new OutputAPIForm();
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/v1/occasion/cost/save").toUriString());
        try{
            retVal = occasionSrv.saveOccasionCost(occasionCost);
        }catch (Exception e){
            log.error("Error in save cost occasion",e);
            retVal.setSuccess(false);
            retVal.getErrors().add(CodeException.SYSTEM_EXCEPTION);
        }
        messageBundleSrv.createMsg(retVal);
        return ResponseEntity.ok().body(retVal);
    }

    @PostMapping("/user/save")
    public ResponseEntity<OutputAPIForm> saveOccasionUser(@RequestBody OccasionUsersDto occasionUsers){
        OutputAPIForm<OccasionUsersDto> retVal = new OutputAPIForm();
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/v1/occasion/user/save").toUriString());
        try{
            retVal = occasionSrv.saveOccasionUsers(occasionUsers);
        }catch (Exception e){
            log.error("Error in save occasion user",e);
            retVal.setSuccess(false);
            retVal.getErrors().add(CodeException.SYSTEM_EXCEPTION);
        }
        messageBundleSrv.createMsg(retVal);
        return ResponseEntity.ok().body(retVal);
    }

    @PostMapping("/itinerary/detail/save")
    public ResponseEntity<OutputAPIForm> saveItineraryDetail(@RequestBody BaseItineraryDetailDto dto){
        OutputAPIForm retVal = new OutputAPIForm();
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/v1/occasion/save").toUriString());
        try{
            retVal = iItinerarySrv.saveItineraryDetail(dto);
        }catch (Exception e){
            log.error("Error in save user",e);
            retVal.setSuccess(false);
            retVal.getErrors().add(CodeException.SYSTEM_EXCEPTION);
        }
        messageBundleSrv.createMsg(retVal);
        return ResponseEntity.created(uri).body(retVal);
    }
}
