package com.service.services;

import com.basedata.StateRequest;
import com.basedata.generalcode.CodeException;
import com.dao.entity.*;
import com.dao.repository.*;
import com.form.OutputAPIForm;
import com.service.dto.*;
import com.utility.StringUtility;
import com.utility.Utility;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collections;

@Service
@Transactional
@Slf4j
public class OccasionSrv implements IOccasionSrv{

    private final IOccasionRepo occasionRepo;
    private final IPicRepo picRepo;
    private final IOccasionTypeRepo occasionTypeRepo;
    private final ItinerarySrv itinerarySrv;
    private final IOccasionPicRepo occasionPicRepo;

    private final IOccasionUsersRepo occasionUsersRepo;

    private final IOccasionCostRepo occasionCostRepo;

    public OccasionSrv(IOccasionRepo occasionRepo, IPicRepo picRepo, IOccasionTypeRepo occasionTypeRepo, ItinerarySrv itinerarySrv, IOccasionPicRepo occasionPicRepo, IOccasionUsersRepo occasionUsersRepo, IOccasionCostRepo occasionCostRepo) {
        this.occasionRepo = occasionRepo;
        this.picRepo = picRepo;
        this.occasionTypeRepo = occasionTypeRepo;
        this.itinerarySrv = itinerarySrv;
        this.occasionPicRepo = occasionPicRepo;
        this.occasionUsersRepo = occasionUsersRepo;
        this.occasionCostRepo = occasionCostRepo;
    }

    public OutputAPIForm<OccasionDto> saveOccasion(BaseOccasionDto dto){
        OutputAPIForm<OccasionDto> retVal = new OutputAPIForm<>();
        OutputAPIForm<ArrayList<ItineraryDto>> defaultItinerary = new OutputAPIForm<>();
        try{
            retVal = validateBaseOccasionDto(dto);
            if(retVal.isSuccess()){
                OccasionType occasionType = occasionTypeRepo.getOne(dto.getOccasionTypeId());
                if(occasionType != null && occasionType.getOccasionComponents()!= null){
                    Pic pic = new Pic(dto.getPic(), dto.getOccasionName());
                    picRepo.save(pic);
                    Occasion occasion = new Occasion(dto,pic.getPicId());
                    occasionRepo.save(occasion);
                    retVal.setData(new OccasionDto(occasion, getTabs(occasionType,dto,occasion.getOccasionId())));
                }
            }
        }catch (Exception e){
            retVal.setSuccess(false);
            retVal.getErrors().add(CodeException.UNDEFINED);
        }
        return retVal;
    }

    private ArrayList<ComponentEventDto> getTabs(OccasionType occasionType,BaseOccasionDto dto,Long occasionId ){
        ArrayList<ComponentEventDto> retVal =new ArrayList<>();
        OutputAPIForm<ArrayList<ItineraryDto>> defaultItinerary;
        ComponentEventDto componentEvent;
        for(OccasionComponent occasionComponent:occasionType.getOccasionComponents()){
            try{
                componentEvent = new ComponentEventDto( occasionComponent.getComponent().getComponentName(),
                                                        occasionComponent.getComponent().getComponentNameFa(),
                                                        occasionComponent.getOrder());
                if(componentEvent.getComponentName().equals("Itinerary")){
                    defaultItinerary = itinerarySrv.saveDefaultItinerary(dto, occasionId);
                    componentEvent.setItineraries(defaultItinerary.getData());
                }
                retVal.add(componentEvent);
            }catch (Exception e){
                log.error(e.getMessage());
            }
        }
        Collections.sort(retVal);
        return retVal;
    }

    public OutputAPIForm validateBaseOccasionDto(BaseOccasionDto dto){
        OutputAPIForm retVal = Utility.checkNull(dto);
        retVal = retVal.isSuccess() ? StringUtility.checkString(dto.getOccasionName(),false,1,50,false):retVal;
        retVal = retVal.isSuccess() ? Utility.checkMandatoryBaseOccasion(dto):retVal;
        retVal = retVal.isSuccess() ? Utility.checkPic(dto.getPic(),true):retVal;
        retVal = retVal.isSuccess() ? Utility.checkOccasionDateTime(dto.getOccasionLengthType(),dto.getStartDate(),dto.getEndDate()):retVal;
        return retVal;

    }

    public OutputAPIForm<OccasionPicDto> saveOccasionPic(OccasionPicDto dto){

        OutputAPIForm<OccasionPicDto> retVal =new OutputAPIForm();
        try{
            Pic pic = new Pic(dto.getPic(), dto.getName());
            picRepo.save(pic);
            OccasionPic ent = new OccasionPic(dto,pic);
            occasionPicRepo.save(ent);
            dto.setPicId(pic.getPicId());
            dto.setOccasionPicId(ent.getOccasionPicId());
            dto.setPic(null);
            retVal.setData(dto);
        }catch (Exception e){
            retVal.setSuccess(false);
            retVal.getErrors().add(CodeException.DATA_BASE_EXCEPTION);
        }
        return retVal;

    }

    public OutputAPIForm<OccasionUsersDto> saveOccasionUsers(OccasionUsersDto dto){
        OutputAPIForm<OccasionUsersDto> retVal = new OutputAPIForm<>();
        try{
            OccasionUsers ent = new OccasionUsers(null,dto.getUserId(),dto.getOccasionId(), StateRequest.Requested);
            occasionUsersRepo.save(ent);
            dto.setOccasionUserId(ent.getOccasionUserId());
            retVal.setData(dto);
        }catch (Exception e){
            retVal.setSuccess(false);
            retVal.getErrors().add(CodeException.DATA_BASE_EXCEPTION);
        }
        return retVal;
    }

    public OutputAPIForm<OccasionCostDto> saveOccasionCost(OccasionCostDto dto){
        OutputAPIForm<OccasionCostDto> retVal = new OutputAPIForm<>();
        try{
            OccasionCost ent = new OccasionCost(dto);
            occasionCostRepo.save(ent);
            dto.setOccasionId(ent.getOccasionCostId());
            retVal.setData(dto);
        }catch (Exception e){
            retVal.setSuccess(false);
            retVal.getErrors().add(CodeException.DATA_BASE_EXCEPTION);
        }
        return retVal;
    }
}
