package com.env.service.services;

import com.basedata.generalcode.CodeException;
import com.env.dao.entity.Pic;
import com.env.dao.entity.Place;
import com.env.dao.entity.PlacePic;
import com.env.dao.repository.IPicRepo;
import com.env.dao.repository.IPlacePicRepo;
import com.env.dao.repository.IPlaceRepo;
import com.env.service.dto.CriPlaceDto;
import com.env.utility.Utility;
import com.form.OutputAPIForm;
import com.env.service.dto.PlaceDto;
import com.env.service.dto.PlacePicDto;
import com.utility.GeneralUtility;
import com.utility.InfraSecurityUtils;
import com.utility.StringUtility;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Optional;

@Service
@Transactional
@Slf4j
public class PlaceSrv implements IPlaceSrv{

    private final IPlaceRepo placeRepo;
    private final IPicRepo picRepo;
    private final IPlacePicRepo placPicRepo;

    @Value("${limitation.place,numberImage:6}")
    private Long MaxNumberImageOfPlace;

    @Value("${application.pageSize.maximum:10}")
    public int pageSize;

    public PlaceSrv(IPlaceRepo placeRepo, IPicRepo picRepo, IPlacePicRepo placPicRepo) {
        this.placeRepo = placeRepo;
        this.picRepo = picRepo;
        this.placPicRepo = placPicRepo;
    }

    public OutputAPIForm<PlaceDto> checkAndSavePlace(PlaceDto dto){
        OutputAPIForm<PlaceDto> retVal = new OutputAPIForm<>();
        if(Objects.nonNull(dto.getPlaceId())){
            Optional<Place> place = placeRepo.findById(dto.getPlaceId());
            if(place.isPresent()){
                retVal.setData(new PlaceDto(place.get()));
            }else{
                retVal.setSuccess(false);
                retVal.getErrors().add(CodeException.NOT_FIND_REFERENCE);
            }
        }else{
            retVal = savePlace(dto);
        }
        return retVal;
    }

    public OutputAPIForm<PlaceDto> savePlace(PlaceDto dto){
        OutputAPIForm<PlaceDto> retVal = validatePlace(dto);
        if(retVal.isSuccess()){
            try{
                Place place = new Place(dto);
                placeRepo.save(place);
                dto.setPlaceId(place.getPlaceId());
                savePics(dto,place.getPlaceId());
                dto.setPics(new ArrayList<>());
                retVal.setData(dto);
            }catch (Exception e){
                retVal.setSuccess(false);
                retVal.getErrors().add(CodeException.DATA_BASE_EXCEPTION);
            }
        }
        return retVal;
    }

    public void savePics(PlaceDto dto,Long placeId){
        if(Objects.nonNull(dto.getPics())){
            Pic pic;
            PlacePic placePic;
            for(byte[] p:dto.getPics()){
                pic = new Pic(p,dto.getName());
                picRepo.save(pic);
                dto.getPicIds().add(pic.getPicId());
                placePic = new PlacePic(null,placeId,pic.getPicId());
                placPicRepo.save(placePic);
                dto.getPlacePicIds().add(placePic.getPlaceId());
            }
        }
    }

    public OutputAPIForm updatePlace(PlaceDto dto){
        OutputAPIForm retVal = validateUpdatePlace(dto);
        try{
            if(retVal.isSuccess()){
                Optional<Place> ent = placeRepo.findById(dto.getPlaceId());
                if(ent.isPresent() && ent.get().getCreatorUserId().equals(InfraSecurityUtils.getCurrentUser())){
                    ent.get().update(dto);
                    placeRepo.save(ent.get());
                }
            }
        }catch (Exception e){
            log.error("Error in Update Place ");
            retVal.setSuccess(false);
            retVal.getErrors().add(CodeException.DATA_BASE_EXCEPTION);
        }
        return retVal;
    }

    public OutputAPIForm validatePlace(PlaceDto dto){
        OutputAPIForm retVal = StringUtility.checkString(dto.getName(),2,20,true);
        retVal = retVal.isSuccess() ? StringUtility.checkString(dto.getNameFa(),2,20,false):retVal;
        retVal = retVal.isSuccess() ? StringUtility.checkString(dto.getDescription(),0,20,false): retVal;
        retVal = retVal.isSuccess() ? Utility.checkNull(dto.getLatitude()) : retVal;
        retVal = retVal.isSuccess() ? Utility.checkNull(dto.getLongitude()) : retVal;
        retVal = retVal.isSuccess() ? validatePlacePic(dto.getPics()): retVal;
        return retVal;
    }

    public OutputAPIForm validatePlacePic(ArrayList<byte[]> pics){
        OutputAPIForm retVal = new OutputAPIForm();
        if(Objects.nonNull(pics)){
            if(pics.size() > MaxNumberImageOfPlace){
                retVal.setSuccess(false);
                retVal.getErrors().add(CodeException.MAX_DATA);
            }else{
                for(byte[] p:pics){
                    retVal = Utility.checkPic(p,false);
                    if(!retVal.isSuccess()){
                        break;
                    }
                }
            }
        }
        return retVal;
    }

    public OutputAPIForm validateUpdatePlace(PlaceDto dto){
        OutputAPIForm retVal = Utility.checkNull(dto.getName()).isSuccess() ?  StringUtility.checkString(dto.getName(),2,20,true): new OutputAPIForm();
        retVal = (retVal.isSuccess() && Utility.checkNull(dto.getName()).isSuccess()) ? StringUtility.checkString(dto.getNameFa(),2,20,false):retVal;
        retVal = (retVal.isSuccess() && Utility.checkNull(dto.getDescription()).isSuccess()) ? StringUtility.checkString(dto.getDescription(),0,20,false): retVal;
        return retVal;
    }

    public OutputAPIForm<PlacePicDto> savePlacePic(PlacePicDto dto){
        OutputAPIForm<PlacePicDto> retVal = new OutputAPIForm<>();
        Pic pic;
        PlacePic placePic;
        try{
            retVal = validatePlacePic(dto);
            if(retVal.isSuccess()){
                pic = new Pic(dto.getPic(), dto.getName());
                picRepo.save(pic);
                dto.setPicId(pic.getPicId());
                placePic = new PlacePic(null,dto.getPlaceId(),pic.getPicId());
                placPicRepo.save(placePic);
                dto.setPlacePicId(placePic.getPlaceId());
                retVal.setData(dto);
            }
        }catch (Exception e){
            retVal.setSuccess(false);
            retVal.getErrors().add(CodeException.DATA_BASE_EXCEPTION);
        }
        return retVal;
    }

    public OutputAPIForm validatePlacePic(PlacePicDto dto){
        OutputAPIForm retVal = Utility.checkNull(dto.getPlaceId());
        retVal = retVal.isSuccess() ?Utility.checkPic(dto.getPic(), true):retVal;
        if(retVal.isSuccess()){
            long countImage = placPicRepo.countByPlaceId(dto.getPlaceId());
            if(Objects.nonNull(countImage) && countImage >  MaxNumberImageOfPlace){
                retVal.setSuccess(false);
                retVal.getErrors().add(CodeException.MAX_DATA);
            }
        }
        return retVal;
    }

    public OutputAPIForm<ArrayList<PlaceDto>> listOfPlace(CriPlaceDto criPlaceDto){
        OutputAPIForm<ArrayList<PlaceDto>> retVal = new OutputAPIForm<>();
        try{
            ArrayList<PlaceDto> data = new ArrayList<>();
            ArrayList<Place> places = placeRepo.getPlaceByCri(criPlaceDto.getEventId(), criPlaceDto.getNameFa(),
                    PageRequest.of(criPlaceDto.getPage().intValue(), pageSize+1, Sort.by("creationDate")) );
            if(Objects.nonNull(places)){
                retVal.setNextPage(GeneralUtility.checkNextPage(places,pageSize));
                for(Place place:places){
                    data.add(new PlaceDto(place));
                }
                retVal.setData(data);
            }
        }catch (Exception e){
            log.error("Error in query in Place",e);
            retVal.setSuccess(false);
            retVal.getErrors().add(CodeException.DATA_BASE_EXCEPTION);
        }
        return retVal;
    }

    public OutputAPIForm deletePlacePic(PlacePicDto dto){
        OutputAPIForm retVal = new OutputAPIForm();
        try{
            Optional<Place> place = placeRepo.findById(dto.getPlaceId());
            if(place.isPresent() && place.get().getCreatorUserId().equals(InfraSecurityUtils.getCurrentUser())){
                placPicRepo.deleteByPlaceIdAndPlacePicId(dto.getPlaceId(), dto.getPlacePicId());
            }else{
                retVal.setSuccess(false);
                retVal.getErrors().add(CodeException.NOT_FIND_REFERENCE);
            }
        }catch (Exception e){
            e.printStackTrace();
            retVal.setSuccess(false);
            retVal.getErrors().add(CodeException.DATA_BASE_EXCEPTION);
        }
        return retVal;
    }
}
