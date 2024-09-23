package com.env.service.services;

import com.form.OutputAPIForm;
import com.env.service.dto.PlaceDto;
import com.env.service.dto.PlacePicDto;

public interface IPlaceSrv {
    OutputAPIForm<PlaceDto> savePlace(PlaceDto dto);
    OutputAPIForm<PlacePicDto> savePlacePic(PlacePicDto dto);
    OutputAPIForm<PlaceDto> checkAndSavePlace(PlaceDto dto);
}
