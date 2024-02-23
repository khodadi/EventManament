package com.shp.service.dto;

import com.shp.basedata.CartStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CartDto {
    private Long cardId;
    private CartStatus cartStatus;
    private ArrayList<CartItemDto> cardItemDtos;
}
