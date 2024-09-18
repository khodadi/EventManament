package com.env.controller;

import com.basedata.generalcode.CodeException;
import com.env.service.dto.CriOccasionDto;
import com.env.service.dto.OccasionPicDto;
import com.env.service.services.IOccasionSrv;
import com.form.OutputAPIForm;
import com.service.services.IMessageBundleSrv;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;

/**
 * @Creator 9/15/2024
 * @Project IntelliJ IDEA
 * @Author k.khodadi
 **/

@RestController
@RequestMapping("/api/v1/occasion/pic")
@RequiredArgsConstructor
@Slf4j
public class OccasionPicController {

    @Autowired
    private IOccasionSrv occasionSrv;

    @Autowired
    private IMessageBundleSrv messageBundleSrv;

    @GetMapping("")
    public ResponseEntity<OutputAPIForm> listOccasionPic(@RequestBody CriOccasionDto criOccasion){
        OutputAPIForm<ArrayList<OccasionPicDto>> retVal = new OutputAPIForm();
        try{
            retVal = occasionSrv.listOccasionPic(criOccasion);
        }catch (Exception e){
            log.error("Error in save cost occasion",e);
            retVal.setSuccess(false);
            retVal.getErrors().add(CodeException.SYSTEM_EXCEPTION);
        }
        messageBundleSrv.createMsg(retVal);
        return ResponseEntity.ok().body(retVal);
    }

    @PostMapping("")
    public ResponseEntity<OutputAPIForm> saveOccasionPic(@RequestBody OccasionPicDto occasionPic){
        OutputAPIForm retVal = new OutputAPIForm();
        try{
            retVal = occasionSrv.saveOccasionPic(occasionPic);
        }catch (Exception e){
            log.error("Error in save Pic",e);
            retVal.setSuccess(false);
            retVal.getErrors().add(CodeException.SYSTEM_EXCEPTION);
        }
        messageBundleSrv.createMsg(retVal);
        return ResponseEntity.ok().body(retVal);
    }

    @DeleteMapping("")
    public ResponseEntity deleteOccasionPic(@RequestBody OccasionPicDto occasionPic){
        OutputAPIForm retVal = new OutputAPIForm();
        try{
            retVal = occasionSrv.deleteOccasionPic(occasionPic);
        }catch (Exception e){
            log.error("Error in save cost occasion",e);
            retVal.setSuccess(false);
            retVal.getErrors().add(CodeException.SYSTEM_EXCEPTION);
        }
        messageBundleSrv.createMsg(retVal);
        return ResponseEntity.ok().body(retVal);
    }

    @GetMapping(value = "/showImage", produces = "image/jpg")
    public ResponseEntity<byte[]> getImageAsResponseEntity(@RequestBody OccasionPicDto picDto) {
        byte[] retVal;
        try {
            retVal = occasionSrv.getImage(picDto);
        } catch (Exception e) {
            retVal = null;
            e.printStackTrace();
        }
        ResponseEntity<byte[]> responseEntity = new ResponseEntity<>(retVal,  HttpStatus.OK);
        return responseEntity;
    }

}
