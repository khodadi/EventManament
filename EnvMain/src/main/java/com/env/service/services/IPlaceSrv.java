package com.env.service.services;

import com.env.service.dto.CriPlaceDto;
import com.form.OutputAPIForm;
import com.env.service.dto.PlaceDto;
import com.env.service.dto.PlacePicDto;

import java.util.ArrayList;

public interface IPlaceSrv {
    OutputAPIForm<PlaceDto> savePlace(PlaceDto dto);
    OutputAPIForm<PlacePicDto> savePlacePic(PlacePicDto dto);
    OutputAPIForm<PlaceDto> checkAndSavePlace(PlaceDto dto);
    OutputAPIForm updatePlace(PlaceDto dto);
    OutputAPIForm<ArrayList<PlaceDto>> listOfPlace(CriPlaceDto criPlaceDto);
}
