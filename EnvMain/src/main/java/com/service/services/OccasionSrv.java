package com.service.services;

import com.basedata.StateRequest;
import com.basedata.generalcode.CodeException;
import com.dao.entity.*;
import com.dao.repository.*;
import com.form.OutputAPIForm;
import com.service.dto.*;
import com.utility.InfraSecurityUtils;
import com.utility.StringUtility;
import com.utility.Utility;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

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

    public final static int pageSize = 5;
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
                    OccasionUsers occasionUser = new OccasionUsers(null,occasion.getCreatorUserId(),occasion.getOccasionId(),StateRequest.Accepted);
                    occasionUsersRepo.save(occasionUser);
                    retVal.setData(new OccasionDto(occasion, saveDefaultTabs(occasionType,dto,occasion.getOccasionId()),false));
                }
            }
        }catch (Exception e){
            retVal.setSuccess(false);
            retVal.getErrors().add(CodeException.UNDEFINED);
        }
        return retVal;
    }
    public OutputAPIForm<ArrayList<OccasionDto>> listOccasion(CriOccasionDto criOccasion){
        OutputAPIForm<ArrayList<OccasionDto>> retVal = new OutputAPIForm<>();
        ArrayList<OccasionDto> occasionDtos = new ArrayList<>();
        List<Occasion> occasions = occasionRepo.getOccasionByUserId(criOccasion.getUserId() == null ?InfraSecurityUtils.getCurrentUser(): criOccasion.getUserId(),
                                                                    criOccasion.getOccasionId(),
                                                                    PageRequest.of(criOccasion.getPage(), pageSize+1));
        if(occasions!= null && occasions.size()> pageSize){
            retVal.setNextPage(true);
        }
        if(Objects.nonNull(occasions)){
            for(int index= 0; index<=Math.min(occasions.size()-1,pageSize-1);index++){
                occasionDtos.add(new OccasionDto(occasions.get(index),getTabsEvents(occasions.get(index)),true));
            }
        }
        retVal.setData(occasionDtos);
        return retVal;
    }

    public ArrayList<ComponentEventDto> getTabsEvents(Occasion event){
        ArrayList<ComponentEventDto> retVal= new ArrayList<>();
        ComponentEventDto componentEvent;
        for(OccasionComponent occasionComponent:event.getOccasionType().getOccasionComponents()){
            componentEvent = new ComponentEventDto( occasionComponent.getComponent().getComponentName(),
                    occasionComponent.getComponent().getComponentNameFa(),
                    occasionComponent.getOrder());
            setOccasionItinerary(event,componentEvent);
            setOccasionParticipant(event,componentEvent);
            retVal.add(componentEvent);
        }
        Collections.sort(retVal);
        return retVal;
    }
    private void setOccasionItinerary(Occasion event,ComponentEventDto componentEvent){
        if(componentEvent.getComponentName().equals("Itinerary")){
            ArrayList<ItineraryDto> itineraryDtos = new ArrayList<>();
            for(Itinerary itinerary:event.getItineraries()){
                itineraryDtos.add(new ItineraryDto(itinerary));
            }
            componentEvent.setItineraries(itineraryDtos);
        }
    }
    private void setOccasionParticipant(Occasion event,ComponentEventDto componentEvent){
        if(componentEvent.getComponentName().equals("Participant")){
            ArrayList<OccasionUsersDto> occasionUsersDtos = new ArrayList<>();
            for(OccasionUsers occasionUsers:event.getOccasionUsers()){
                occasionUsersDtos.add(new OccasionUsersDto(occasionUsers.getOccasionUserId(),
                        occasionUsers.getUserId(),
                        occasionUsers.getOccasionId(),
                        occasionUsers.getStateRequest()));
            }
            componentEvent.setOccasionUsersDtos(occasionUsersDtos);
        }
    }

    private ArrayList<ComponentEventDto> saveDefaultTabs(OccasionType occasionType,BaseOccasionDto dto,Long occasionId ){
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
            dto.setStateRequest(ent.getStateRequest());
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
            dto.setOccasionCostId(ent.getOccasionCostId());
            retVal.setData(dto);
        }catch (Exception e){
            retVal.setSuccess(false);
            retVal.getErrors().add(CodeException.DATA_BASE_EXCEPTION);
        }
        return retVal;
    }

}
