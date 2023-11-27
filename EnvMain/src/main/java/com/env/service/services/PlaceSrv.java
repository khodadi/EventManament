package com.service.services;

import com.basedata.generalcode.CodeException;
import com.dao.entity.Pic;
import com.dao.entity.Place;
import com.dao.entity.PlacePic;
import com.dao.repository.IPicRepo;
import com.dao.repository.IPlacePicRepo;
import com.dao.repository.IPlaceRepo;
import com.form.OutputAPIForm;
import com.service.dto.PlaceDto;
import com.service.dto.PlacePicDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

@Service
@Transactional
@Slf4j
public class PlaceSrv implements IPlaceSrv{
    private final IPlaceRepo placeRepo;
    private final IPicRepo picRepo;
    private final IPlacePicRepo placPicRepo;


    public PlaceSrv(IPlaceRepo placeRepo, IPicRepo picRepo, IPlacePicRepo placPicRepo) {
        this.placeRepo = placeRepo;
        this.picRepo = picRepo;
        this.placPicRepo = placPicRepo;
    }

    public OutputAPIForm<PlaceDto> savePlace(PlaceDto dto){
        OutputAPIForm<PlaceDto> retVal = new OutputAPIForm<>();
        Pic pic;
        PlacePic placePic;
        try{
            Place place = new Place(dto);
            placeRepo.save(place);
            dto.setPlaceId(place.getPlaceId());
            for(byte[] p:dto.getPics()){
                pic = new Pic(p,dto.getName());
                picRepo.save(pic);
                dto.getPicIds().add(pic.getPicId());
                placePic = new PlacePic(null,place.getPlaceId(),pic.getPicId());
                placPicRepo.save(placePic);
                dto.getPlacePicIds().add(placePic.getPlaceId());
            }
            dto.setPics(new ArrayList<>());
            retVal.setData(dto);
        }catch (Exception e){
            retVal.setSuccess(false);
            retVal.getErrors().add(CodeException.DATA_BASE_EXCEPTION);
        }
        return retVal;
    }

    public OutputAPIForm<PlacePicDto> savePlacePic(PlacePicDto dto){
        OutputAPIForm<PlacePicDto> retVal = new OutputAPIForm<>();
        Pic pic;
        PlacePic placePic;
        try{
            pic = new Pic(dto.getPic(), dto.getName());
            picRepo.save(pic);
            dto.setPicId(pic.getPicId());
            placePic = new PlacePic(null,dto.getPlaceId(),pic.getPicId());
            placPicRepo.save(placePic);
            dto.setPlacePicId(placePic.getPlaceId());
            retVal.setData(dto);
        }catch (Exception e){
            retVal.setSuccess(false);
            retVal.getErrors().add(CodeException.DATA_BASE_EXCEPTION);
        }
        return retVal;
    }



}
