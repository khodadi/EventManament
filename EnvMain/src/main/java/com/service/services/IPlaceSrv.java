package com.service.services;

import com.form.OutputAPIForm;
import com.service.dto.PlaceDto;

public interface IPlaceSrv {
    OutputAPIForm<PlaceDto> savePlace(PlaceDto dto);
}
