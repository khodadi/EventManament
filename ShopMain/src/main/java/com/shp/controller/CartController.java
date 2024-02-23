package com.shp.controller;

import com.basedata.generalcode.CodeException;
import com.form.OutputAPIForm;
import com.service.services.IMessageBundleSrv;
import com.shp.service.dto.CartDto;
import com.shp.service.dto.ItemDto;
import com.shp.service.services.ICartSrv;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/cart")
@RequiredArgsConstructor
@Slf4j
public class CartController {

    private ICartSrv cartSrv;
    private IMessageBundleSrv messageBundleSrv;
    @Autowired
    public CartController(ICartSrv cartSrv, IMessageBundleSrv messageBundleSrv) {
        this.cartSrv = cartSrv;
        this.messageBundleSrv = messageBundleSrv;
    }

    @PostMapping("/add")
    public ResponseEntity<OutputAPIForm> addItem(@RequestBody CartDto cartDto){
        OutputAPIForm<CartDto> retVal = new OutputAPIForm();
        try{
            retVal = cartSrv.addCardItem(cartDto);
        }catch (Exception e){
            log.error("Error in save user",e);
            retVal.setSuccess(false);
            retVal.getErrors().add(CodeException.SYSTEM_EXCEPTION);
        }
        messageBundleSrv.createMsg(retVal);
        return ResponseEntity.ok().body(retVal);
    }
}
