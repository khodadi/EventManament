package com.shp.service.services;

import com.basedata.generalcode.CodeException;
import com.form.OutputAPIForm;
import com.shp.dao.entity.Cart;
import com.shp.dao.entity.CartItem;
import com.shp.dao.repository.ICartItemRepo;
import com.shp.dao.repository.ICartRepo;
import com.shp.service.dto.CartDto;
import com.shp.service.dto.CartItemDto;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class CartSrv implements ICartSrv {

    private final ICartRepo cartRepo;
    private final ICartItemRepo cartItemRepo;

    public CartSrv(ICartRepo cartRepo, ICartItemRepo cartItemRepo) {
        this.cartRepo = cartRepo;
        this.cartItemRepo = cartItemRepo;
    }

    public OutputAPIForm<CartDto> addCardItem(CartDto dto){
        OutputAPIForm<CartDto> retVal = new OutputAPIForm<>();
        CartItem cartItem;
        try{
            Cart cart = new Cart(dto);
            cartRepo.save(cart);
            for(CartItemDto cartItemDto:dto.getCardItemDtos()){
                cartItem = new CartItem(cartItemDto);
                cartItemRepo.save(cartItem);
            }
        }catch (Exception e){
            retVal.setSuccess(false);
            retVal.getErrors().add(CodeException.DATA_BASE_EXCEPTION);
        }
        return retVal;
    }


    public OutputAPIForm<ArrayList<CartDto>> listCardItem(CartDto dto){
        OutputAPIForm<ArrayList<CartDto>> retVal = new OutputAPIForm<>();
        return retVal;

    }
}
