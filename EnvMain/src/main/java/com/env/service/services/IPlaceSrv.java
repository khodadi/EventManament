package com.service.services;

import com.form.OutputAPIForm;
import com.service.dto.PlaceDto;
import com.service.dto.PlacePicDto;

public interface IPlaceSrv {
    OutputAPIForm<PlaceDto> savePlace(PlaceDto dto);
    OutputAPIForm<PlacePicDto> savePlacePic(PlacePicDto dto);
}
