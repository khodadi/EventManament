package com.api.controller;

import com.basedata.generalcode.CodeException;
import com.form.OutputAPIForm;
import com.service.dto.PlaceDto;
import com.service.dto.PlacePicDto;
import com.service.services.IMessageBundleSrv;
import com.service.services.IPlaceSrv;
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

@RestController
@RequestMapping("/api/v1/place")
@RequiredArgsConstructor
@Slf4j
public class PlaceController {
    @Autowired
    private IMessageBundleSrv messageBundleSrv;
    @Autowired
    private IPlaceSrv placeSrv;
    @PostMapping("/save")
    public ResponseEntity<OutputAPIForm> savePlace(@RequestBody PlaceDto dto){
        OutputAPIForm<PlaceDto> retVal = new OutputAPIForm();
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/v1/place/save").toUriString());
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

    @PostMapping("/pic/save")
    public ResponseEntity<OutputAPIForm> savePlacePic(@RequestBody PlacePicDto dto){
        OutputAPIForm<PlacePicDto> retVal = new OutputAPIForm();
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/v1/place/pic/save").toUriString());
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
