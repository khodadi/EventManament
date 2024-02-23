package com.shp.controller;

import com.basedata.generalcode.CodeException;
import com.form.OutputAPIForm;
import com.service.services.IMessageBundleSrv;
import com.shp.service.dto.ItemAttributeDto;
import com.shp.service.dto.ItemCriteria;
import com.shp.service.dto.ItemDto;
import com.shp.service.dto.ProviderDto;
import com.shp.service.services.IItemSrv;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
@RequestMapping("/api/v1/item")
@RequiredArgsConstructor
@Slf4j
public class ItemController {

    private IItemSrv itemSrv;
    private IMessageBundleSrv messageBundleSrv;
    @Autowired
    public ItemController(IItemSrv itemSrv, IMessageBundleSrv messageBundleSrv) {
        this.itemSrv = itemSrv;
        this.messageBundleSrv = messageBundleSrv;
    }

    @PostMapping("/add")
    public ResponseEntity<OutputAPIForm> addItem(@RequestBody ItemDto itemDto){
        OutputAPIForm<ItemDto> retVal = new OutputAPIForm();
        try{
            retVal = itemSrv.insertItem(itemDto);
        }catch (Exception e){
            log.error("Error in save user",e);
            retVal.setSuccess(false);
            retVal.getErrors().add(CodeException.SYSTEM_EXCEPTION);
        }
        messageBundleSrv.createMsg(retVal);
        return ResponseEntity.ok().body(retVal);
    }


    @PostMapping("/attribute/add")
    public ResponseEntity<OutputAPIForm> addItemAttribute(@RequestBody ArrayList<ItemAttributeDto> itemAttributeDtos){
        OutputAPIForm<ArrayList<ItemAttributeDto>> retVal = new OutputAPIForm();
        try{
            retVal = itemSrv.addItemAttributes(itemAttributeDtos);
        }catch (Exception e){
            log.error("Error in save user",e);
            retVal.setSuccess(false);
            retVal.getErrors().add(CodeException.SYSTEM_EXCEPTION);
        }
        messageBundleSrv.createMsg(retVal);
        return ResponseEntity.ok().body(retVal);
    }

    @PostMapping("/list")
    public ResponseEntity<OutputAPIForm> listItem(@RequestBody ItemCriteria cri){
        OutputAPIForm<ArrayList<ItemDto>> retVal = new OutputAPIForm();
        try{
            retVal = itemSrv.listItemProvider(cri);
        }catch (Exception e){
            log.error("Error in save user",e);
            retVal.setSuccess(false);
            retVal.getErrors().add(CodeException.SYSTEM_EXCEPTION);
        }
        messageBundleSrv.createMsg(retVal);
        return ResponseEntity.ok().body(retVal);
    }

}

