package com.env.controller;

import com.basedata.generalcode.CodeException;
import com.env.service.dto.BaseItineraryDetailDto;
import com.env.service.dto.OccasionItineraryDetailDto;
import com.env.service.services.IItinerarySrv;
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
@RequestMapping("/api/v1/occasion/itinerary")
@RequiredArgsConstructor
@Slf4j
public class OccasionItineraryController {

    @Autowired
    private IMessageBundleSrv messageBundleSrv;

    @Autowired
    private IItinerarySrv iItinerarySrv;

    @PostMapping("/detail")
    public ResponseEntity<OutputAPIForm> saveItineraryDetail(@RequestBody BaseItineraryDetailDto dto){
        OutputAPIForm retVal = new OutputAPIForm();
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/v1/occasion/save").toUriString());
        try{
            retVal = iItinerarySrv.saveItineraryDetail(dto);
        }catch (Exception e){
            log.error("Error in save Itinerary detail",e);
            retVal.setSuccess(false);
            retVal.getErrors().add(CodeException.SYSTEM_EXCEPTION);
        }
        messageBundleSrv.createMsg(retVal);
        return ResponseEntity.created(uri).body(retVal);
    }

    @PutMapping("/detail")
    public ResponseEntity<OutputAPIForm> updateItineraryDetail(@RequestBody BaseItineraryDetailDto dto){
        OutputAPIForm retVal = new OutputAPIForm();
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/v1/occasion/save").toUriString());
        try{
            retVal = iItinerarySrv.updateItineraryDetail(dto);
        }catch (Exception e){
            log.error("Error in delete Itinerary detail",e);
            retVal.setSuccess(false);
            retVal.getErrors().add(CodeException.SYSTEM_EXCEPTION);
        }
        messageBundleSrv.createMsg(retVal);
        return ResponseEntity.created(uri).body(retVal);
    }

    @DeleteMapping("/detail")
    public ResponseEntity<OutputAPIForm> deleteItineraryDetail(@RequestBody OccasionItineraryDetailDto dto){
        OutputAPIForm retVal = new OutputAPIForm();
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/v1/occasion/save").toUriString());
        try{
            retVal = iItinerarySrv.deleteItineraryDetail(dto);
        }catch (Exception e){
            log.error("Error in delete Itinerary detail",e);
            retVal.setSuccess(false);
            retVal.getErrors().add(CodeException.SYSTEM_EXCEPTION);
        }
        messageBundleSrv.createMsg(retVal);
        return ResponseEntity.created(uri).body(retVal);
    }

}
