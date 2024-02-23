package com.shp.service.services;

import com.form.OutputAPIForm;
import com.shp.service.dto.CartDto;

public interface ICartSrv {
    OutputAPIForm<CartDto> addCardItem(CartDto dto);
}
