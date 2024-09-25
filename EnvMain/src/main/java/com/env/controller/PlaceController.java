package com.env.controller;

import com.basedata.generalcode.CodeException;
import com.env.service.dto.CriPlaceDto;
import com.form.OutputAPIForm;
import com.env.service.dto.PlaceDto;
import com.env.service.dto.PlacePicDto;
import com.service.services.IMessageBundleSrv;
import com.env.service.services.IPlaceSrv;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;

@RestController
@RequestMapping("/api/v1/place")
@RequiredArgsConstructor
@Slf4j
public class PlaceController {
    @Autowired
    private IMessageBundleSrv messageBundleSrv;
    @Autowired
    private IPlaceSrv placeSrv;

    @GetMapping("")
    public ResponseEntity<OutputAPIForm> ListPlace(@RequestParam String nameFa,@RequestParam Long eventId){
        OutputAPIForm<ArrayList<PlaceDto>> retVal = new OutputAPIForm();
        try{
            retVal = placeSrv.listOfPlace(new CriPlaceDto(eventId,nameFa));
        }catch (Exception e){
            log.error("Error in save PLace",e);
            retVal.setSuccess(false);
            retVal.getErrors().add(CodeException.SYSTEM_EXCEPTION);
        }
        messageBundleSrv.createMsg(retVal);
        return ResponseEntity.ok().body(retVal);
    }


    @PostMapping("")
    public ResponseEntity<OutputAPIForm> savePlace(@RequestBody PlaceDto dto){
        OutputAPIForm<PlaceDto> retVal = new OutputAPIForm();
        try{
            retVal = placeSrv.savePlace(dto);
        }catch (Exception e){
            log.error("Error in save PLace",e);
            retVal.setSuccess(false);
            retVal.getErrors().add(CodeException.SYSTEM_EXCEPTION);
        }
        messageBundleSrv.createMsg(retVal);
        return ResponseEntity.ok().body(retVal);
    }

    @PutMapping("")
    public ResponseEntity<OutputAPIForm> updatePlace(@RequestBody PlaceDto dto){
        OutputAPIForm<PlaceDto> retVal = new OutputAPIForm();
        try{
            retVal = placeSrv.updatePlace(dto);
        }catch (Exception e){
            log.error("Error in save PLace",e);
            retVal.setSuccess(false);
            retVal.getErrors().add(CodeException.SYSTEM_EXCEPTION);
        }
        messageBundleSrv.createMsg(retVal);
        return ResponseEntity.ok().body(retVal);
    }

    @PostMapping("/pic")
    public ResponseEntity<OutputAPIForm> savePlacePic(@RequestBody PlacePicDto dto){
        OutputAPIForm<PlacePicDto> retVal = new OutputAPIForm();
        try{
            retVal = placeSrv.savePlacePic(dto);
        }catch (Exception e){
            log.error("Error in save PLace Pic",e);
            retVal.setSuccess(false);
            retVal.getErrors().add(CodeException.SYSTEM_EXCEPTION);
        }
        messageBundleSrv.createMsg(retVal);
        return ResponseEntity.ok().body(retVal);
    }


}
