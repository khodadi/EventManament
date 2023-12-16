package com.env.service.services;

import com.basedata.generalcode.CodeException;
import com.env.basedata.StateRequest;
import com.env.dao.entity.*;
import com.env.dao.repository.*;
import com.env.service.dto.*;
import com.env.utility.Utility;
import com.form.OutputAPIForm;
import com.utility.InfraSecurityUtils;
import com.utility.StringUtility;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

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
                Optional<OccasionType> occasionType = occasionTypeRepo.findById(dto.getOccasionTypeId());
                if(occasionType.isPresent()){
                    Pic pic = new Pic(dto.getPic(), dto.getOccasionName());
                    picRepo.save(pic);
                    Occasion occasion = new Occasion(dto,pic.getPicId());
                    occasionRepo.save(occasion);
                    OccasionUsers occasionUser = new OccasionUsers(null,occasion.getCreatorUserId(),occasion.getOccasionId(),StateRequest.Accepted);
                    occasionUsersRepo.save(occasionUser);
                    retVal.setData(new OccasionDto(occasion, saveDefaultTabs(occasionType.get(),dto,occasion.getOccasionId()),false));
                }else{
                    retVal.setSuccess(false);
                    retVal.getErrors().add(CodeException.NOT_FIND_REFERENCE);
                }
            }
        }catch (Exception e){
            retVal.setSuccess(false);
            retVal.getErrors().add(CodeException.UNDEFINED);
        }
        return retVal;
    }

    public OutputAPIForm<OccasionDto> editOccasion(OccasionDto dto){
        OutputAPIForm<OccasionDto> retVal = new OutputAPIForm<>();
        Optional<Occasion> occasion = occasionRepo.getOccasionByUserIdForEdit(InfraSecurityUtils.getCurrentUser(),dto.getOccasionId(),StateRequest.Accepted);
        if(occasion.isPresent()){
            if(Objects.nonNull(dto.getPic())){
                picRepo.deleteById(occasion.get().getPicId());
                Pic pic = new Pic(dto.getPic(),Objects.isNull(dto.getOccasionName()) ?occasion.get().getOccasionName():dto.getOccasionName());
                picRepo.save(pic);
                dto.setPicId(pic.getPicId());
            }
            dto.updateEnt(occasion.get());
            occasionRepo.save(occasion.get());
            retVal.setData(new OccasionDto(occasion.get(),editDefaultTabs(occasion,dto),false));
        }

        return retVal;
    }

    public OutputAPIForm<ArrayList<OccasionDto>> listOccasion(CriOccasionDto criOccasion){
        OutputAPIForm<ArrayList<OccasionDto>> retVal = new OutputAPIForm<>();
        ArrayList<OccasionDto> occasionDtos = new ArrayList<>();
        OccasionDto occasionDto;
        List<Occasion> occasions;
        if(InfraSecurityUtils.checkLogin()){
            occasions = occasionRepo.getOccasionByUserId(criOccasion.getUserId(),
                                                         criOccasion.getOccasionId(),
                                                         StateRequest.Accepted,
                                                         PageRequest.of(criOccasion.getPage(), pageSize+1, Sort.by("startDate")));
        }else{
            occasions = occasionRepo.getOccasionPublic(criOccasion.getOccasionId(),
                                                       PageRequest.of(criOccasion.getPage(), pageSize+1, Sort.by("startDate")));

        }

        if(occasions!= null && occasions.size()> pageSize){
            retVal.setNextPage(true);
        }
        if(Objects.nonNull(occasions)){
            for(int index= 0; index<=Math.min(occasions.size()-1,pageSize-1);index++){
                occasionDto = new OccasionDto(occasions.get(index),getTabsEvents(occasions.get(index)),true);
                occasionDto.setEditable(editableOccasion(occasions.get(index)));
                occasionDtos.add(occasionDto);
            }
        }
        retVal.setData(occasionDtos);
        return retVal;
    }

    private boolean editableOccasion(Occasion ent){
        boolean retVal = false;
        for(OccasionUsers occasionUsers:ent.getOccasionUsers()){
            if(occasionUsers.getUserId().equals(InfraSecurityUtils.getCurrentUser())){
                retVal = true;
                break;
            }
        }
        return retVal;
    }

    public ArrayList<ComponentEventDto> getTabsEvents(Occasion event){
        ArrayList<ComponentEventDto> retVal= new ArrayList<>();
        ComponentEventDto componentEvent;
        for(OccasionComponent occasionComponent:event.getOccasionType().getOccasionComponents()){
            if(InfraSecurityUtils.checkLogin() || (!InfraSecurityUtils.checkLogin() && !occasionComponent.isNeedLogin()) ){
                componentEvent = new ComponentEventDto( occasionComponent.getComponent().getComponentName(),
                        occasionComponent.getComponent().getComponentNameFa(),
                        occasionComponent.getOrder());
                setOccasionItinerary(event,componentEvent);
                setOccasionParticipant(event,componentEvent);
                retVal.add(componentEvent);
            }
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
            Collections.sort(itineraryDtos);
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

    private ArrayList<ComponentEventDto> saveDefaultTabs(OccasionType occasionType, BaseOccasionDto dto, Long occasionId ){
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

    private ArrayList<ComponentEventDto> editDefaultTabs(Optional<Occasion> occasion,OccasionDto dto ){
        ArrayList<ComponentEventDto> retVal =new ArrayList<>();
        OutputAPIForm<ArrayList<ItineraryDto>> defaultItinerary;
        ComponentEventDto componentEvent;
        for(OccasionComponent occasionComponent:occasion.get().getOccasionType().getOccasionComponents()){
            try{
                componentEvent = new ComponentEventDto( occasionComponent.getComponent().getComponentName(),
                        occasionComponent.getComponent().getComponentNameFa(),
                        occasionComponent.getOrder());
                if(componentEvent.getComponentName().equals("Itinerary")){
                    defaultItinerary = itinerarySrv.editDefaultItinerary(occasion,dto);
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
        retVal = retVal.isSuccess() ? Utility.checkOccasionDateTime(dto.getOccasionLengthType(), dto.getStartDate(), dto.getEndDate()):retVal;
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

    public OutputAPIForm<OccasionPicDto> saveOccasionPic(OccasionPicDto dto){
        OutputAPIForm<OccasionPicDto> retVal =new OutputAPIForm();
        try{
            if(hasAccessInsOccasionCost(dto.getOccasionId())){
                Pic pic = new Pic(dto.getPic(), dto.getName());
                picRepo.save(pic);
                OccasionPic ent = new OccasionPic(dto,pic);
                occasionPicRepo.save(ent);
                dto.setPicId(pic.getPicId());
                dto.setOccasionPicId(ent.getOccasionPicId());
                dto.setPic(null);
                retVal.setData(dto);
            }else{
                retVal.setSuccess(false);
                retVal.getErrors().add(CodeException.ACCESS_DENIED);
            }
        }catch (Exception e){
            retVal.setSuccess(false);
            retVal.getErrors().add(CodeException.DATA_BASE_EXCEPTION);
        }
        return retVal;
    }

    public OutputAPIForm deleteOccasionPic(OccasionPicDto dto){
        OutputAPIForm retVal = new OutputAPIForm();
        try{
            OccasionPic ent = occasionPicRepo.getOne(dto.getOccasionPicId());
            if(Objects.nonNull(ent) && hasAccessDelUpdateOccasionPic(ent)){
                occasionPicRepo.deleteById(ent.getOccasionPicId());
            }else{
                retVal.setSuccess(false);
                retVal.getErrors().add(CodeException.ACCESS_DENIED);
            }
        }catch (Exception e){
            retVal.setSuccess(false);
            retVal.getErrors().add(CodeException.DATA_BASE_EXCEPTION);
            log.error(e.getMessage());
        }
        return retVal;
    }

    public OutputAPIForm<ArrayList<OccasionCostDto>> listOccasionCost(CriOccasionDto criOccasion){
        OutputAPIForm<ArrayList<OccasionCostDto>> retVal = new OutputAPIForm<>();
        ArrayList<OccasionCostDto> dtos = new ArrayList<>();
        List<Occasion> occasions = occasionRepo.getOccasionByUserId(InfraSecurityUtils.getCurrentUser(),
                                                                    criOccasion.getOccasionId(),
                                                                    StateRequest.Accepted,
                                                                    PageRequest.of(0, pageSize+1, Sort.by("startDate")));
        if(Objects.nonNull(occasions) &&
           occasions.size() == 1 &&
           Objects.nonNull(occasions.get(0)) &&
           Objects.nonNull(occasions.get(0).getOccasionCosts())){
               for(OccasionCost ent:occasions.get(0).getOccasionCosts()){
                   dtos.add(new OccasionCostDto(ent.getOccasionCostId(),ent.getOccasionCost(),ent.getUserId(),ent.getOccasionId(),ent.getDescription()));
               }
        }
        retVal.setData(dtos);
        return retVal;
    }

    public OutputAPIForm<OccasionCostDto> saveOccasionCost(OccasionCostDto dto){
        OutputAPIForm<OccasionCostDto> retVal = new OutputAPIForm<>();
        try{
            if(hasAccessInsOccasionCost(dto.getOccasionId())){
                OccasionCost ent = new OccasionCost(dto);
                occasionCostRepo.save(ent);
                dto.setOccasionCostId(ent.getOccasionCostId());
                retVal.setData(dto);
            }else{
                retVal.setSuccess(false);
                retVal.getErrors().add(CodeException.ACCESS_DENIED);
            }

        }catch (Exception e){
            retVal.setSuccess(false);
            retVal.getErrors().add(CodeException.DATA_BASE_EXCEPTION);
        }
        return retVal;
    }

    public OutputAPIForm<OccasionCostDto> updateOccasionCost(OccasionCostDto dto){
        OutputAPIForm<OccasionCostDto> retVal = new OutputAPIForm<>();
        try{
            OccasionCost ent = occasionCostRepo.getOne(dto.getOccasionCostId());
            if(Objects.nonNull(ent) && hasAccessDelUpdateOccasionCost(ent)){
                ent.updateEnt(dto);
                occasionCostRepo.save(ent);
            }else{
                retVal.setSuccess(false);
                retVal.getErrors().add(CodeException.ACCESS_DENIED);
            }

        }catch (Exception e){
            retVal.setSuccess(false);
            retVal.getErrors().add(CodeException.DATA_BASE_EXCEPTION);
        }
        return retVal;
    }

    public OutputAPIForm deleteOccasionCost(OccasionCostDto dto){
        OutputAPIForm retVal = new OutputAPIForm<>();
        try{
            OccasionCost ent = occasionCostRepo.getOne(dto.getOccasionCostId());
            if(Objects.nonNull(ent) && hasAccessDelUpdateOccasionCost(ent)){
                occasionRepo.deleteById(dto.getOccasionCostId());
            }else{
                retVal.setSuccess(false);
                retVal.getErrors().add(CodeException.ACCESS_DENIED);
            }
        }catch (Exception e){
            retVal.setSuccess(false);
            retVal.getErrors().add(CodeException.DATA_BASE_EXCEPTION);
            log.error(e.getMessage());
        }
        return retVal;
    }

    public boolean hasAccessDelUpdateOccasionCost(OccasionCost ent){
        boolean retVal = false;
        try{
            if(ent.getCreatorUserId() != null && ent.getCreatorUserId().equals(InfraSecurityUtils.getCurrentUser())
                    ||
              (Objects.nonNull(ent.getOccasion()) &&
               Objects.nonNull(ent.getOccasion().getCreatorUserId()) &&
               ent.getOccasion().getCreatorUserId().equals(InfraSecurityUtils.getCurrentUser())
              )
            ){
                retVal = true;
            }
        }catch (Exception e){
            log.error(e.getMessage());
        }
        return retVal;

    }

    public boolean hasAccessInsOccasionCost(Long occasionId){
        boolean retVal = false;
        try{
            Occasion occasion = occasionRepo.getOne(occasionId);
            if((Objects.nonNull(occasion) && Objects.nonNull(occasion.getCreatorUserId()) && occasion.getCreatorUserId().equals(InfraSecurityUtils.getCurrentUser()))){
                retVal = true;
            }else if(Objects.nonNull(occasion) && Objects.nonNull(occasion.getOccasionUsers())){
                for(OccasionUsers usr: occasion.getOccasionUsers()){
                    if(Objects.nonNull(usr) && Objects.nonNull(usr.getUserId()) && usr.getUserId().equals(InfraSecurityUtils.getCurrentUser())){
                        retVal = true;
                        break;
                    }
                }
            }
        }catch (Exception e){
            log.error(e.getMessage());
        }
        return retVal;
    }

    public boolean hasAccessDelUpdateOccasionPic(OccasionPic ent){
        boolean retVal = false;
        try{
            if(ent.getCreatorUserId() != null && ent.getCreatorUserId().equals(InfraSecurityUtils.getCurrentUser())
                    ||
                    (Objects.nonNull(ent.getOccasion()) &&
                            Objects.nonNull(ent.getOccasion().getCreatorUserId()) &&
                            ent.getOccasion().getCreatorUserId().equals(InfraSecurityUtils.getCurrentUser())
                    )
            ){
                retVal = true;
            }
        }catch (Exception e){
            log.error(e.getMessage());
        }
        return retVal;

    }



}
