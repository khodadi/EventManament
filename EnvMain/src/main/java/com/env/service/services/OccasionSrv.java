package com.env.service.services;

import com.basedata.generalcode.CodeException;
import com.env.basedata.StateRequest;
import com.env.dao.entity.*;
import com.env.dao.repository.*;
import com.env.service.dto.*;
import com.env.service.services.tab.ITabSrv;
import com.env.utility.Utility;
import com.form.OutputAPIForm;
import com.utility.GeneralUtility;
import com.utility.InfraSecurityUtils;
import com.utility.StringUtility;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
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
    private final IFactory<ITabSrv,String> tabFactory;
    private final IPlaceSrv placeSrv;

    public final static int pageSize = 100;
    @Autowired
    private ApplicationContext ctx;

    @Value("${imagePath:./files/images/}")
    private String imagePath;

    public OccasionSrv(IOccasionRepo occasionRepo, IPicRepo picRepo, IOccasionTypeRepo occasionTypeRepo, ItinerarySrv itinerarySrv, IOccasionPicRepo occasionPicRepo, IOccasionUsersRepo occasionUsersRepo, IOccasionCostRepo occasionCostRepo, IFactory tabFactory, IPlaceSrv placeSrv) {
        this.occasionRepo = occasionRepo;
        this.picRepo = picRepo;
        this.occasionTypeRepo = occasionTypeRepo;
        this.itinerarySrv = itinerarySrv;
        this.occasionPicRepo = occasionPicRepo;
        this.occasionUsersRepo = occasionUsersRepo;
        this.occasionCostRepo = occasionCostRepo;
        this.tabFactory = tabFactory;
        this.placeSrv = placeSrv;
    }

    public OutputAPIForm<OccasionDto> saveOccasion(BaseOccasionDto dto){
        OutputAPIForm<OccasionDto> retVal = new OutputAPIForm<>();
        try{
            retVal = validateBaseOccasionDto(dto);
            if(retVal.isSuccess()){
                Optional<OccasionType> occasionType = occasionTypeRepo.findById(dto.getOccasionTypeId());
                if(occasionType.isPresent()){
                    OutputAPIForm<PlaceDto> outputPlaceDto = placeSrv.checkAndSavePlace(dto.getSourceDto());
                    if(outputPlaceDto.isSuccess()){
                        Pic pic = new Pic(dto.getPic(), dto.getOccasionName());
                        picRepo.save(pic);
                        dto.setSourceDto(outputPlaceDto.getData());
                        Occasion occasion = new Occasion(dto,pic.getPicId());
                        occasionRepo.save(occasion);
                        OccasionUsers occasionUser = new OccasionUsers(null,occasion.getCreatorUserId(),occasion.getOccasionId(),StateRequest.Accepted,null,false);
                        occasionUsersRepo.save(occasionUser);
                        retVal.setData(new OccasionDto(occasion,outputPlaceDto.getData(), saveDefaultTabs(occasionType.get(),dto,occasion.getOccasionId()),false));
                    }else{
                        retVal.getErrors().addAll(outputPlaceDto.getErrors());
                        retVal.setSuccess(false);
                    }
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
            OutputAPIForm<PlaceDto> outputPlaceDto = placeSrv.checkAndSavePlace(dto.getSourceDto());
            if(outputPlaceDto.isSuccess()){
                dto.setSourceDto(outputPlaceDto.getData());
                if(Objects.nonNull(dto.getPic())){
                    picRepo.deleteById(occasion.get().getPicId());
                    Pic pic = new Pic(dto.getPic(),Objects.isNull(dto.getOccasionName()) ?occasion.get().getOccasionName():dto.getOccasionName());
                    picRepo.save(pic);
                    dto.setPicId(pic.getPicId());
                }
                dto.updateEnt(occasion.get());
                occasionRepo.save(occasion.get());
                retVal.setData(new OccasionDto(occasion.get(),outputPlaceDto.getData(),editDefaultTabs(occasion,dto),false));
            }else{
                retVal.getErrors().addAll(outputPlaceDto.getErrors());
                retVal.setSuccess(false);
            }
        }

        return retVal;
    }

    public OutputAPIForm<ArrayList<OccasionDto>> listOccasion(CriOccasionDto criOccasion){
        OutputAPIForm<ArrayList<OccasionDto>> retVal = new OutputAPIForm<>();
        ArrayList<OccasionDto> occasionDtos = new ArrayList<>();
        OccasionDto occasionDto;
        List<Occasion> occasions;
        if(InfraSecurityUtils.checkLogin()){
            log.info("list occasion user : " + InfraSecurityUtils.getCurrentUser());
            occasions = occasionRepo.getOccasionByUserId(InfraSecurityUtils.getCurrentUser(),
                                                         criOccasion.getOccasionId(),
                                                         StateRequest.Accepted,
                                                         PageRequest.of(criOccasion.getPage(), pageSize+1, Sort.by("startDate")));
        }else{
            log.info("list sharable occasion ");
            occasions = occasionRepo.getOccasionPublic(criOccasion.getOccasionId(), PageRequest.of(criOccasion.getPage(), pageSize+1, Sort.by("startDate")));
        }
        if(occasions!= null && occasions.size()> pageSize){
            retVal.setNextPage(true);
        }
        if(Objects.nonNull(occasions)){
            for(int index= 0; index<=Math.min(occasions.size()-1,pageSize-1);index++){
                occasionDto = new OccasionDto(occasions.get(index),new PlaceDto(occasions.get(index).getPlace()),getTabsEvents(occasions.get(index)),true);
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

    public ArrayList<ComponentEventGeneralDto> getTabsEvents(Occasion event){
        ArrayList<ComponentEventGeneralDto> retVal= new ArrayList<>();
        for(OccasionComponent occasionComponent:event.getOccasionType().getOccasionComponents()){
            if((InfraSecurityUtils.checkLogin() || (!InfraSecurityUtils.checkLogin() && !occasionComponent.isNeedLogin()))
               &&
               tabFactory.factory(occasionComponent.getComponent().getComponentName()) != null){
                retVal.add(tabFactory.factory(occasionComponent.getComponent().getComponentName()).createTab(event,occasionComponent));
            }
        }
        Collections.sort(retVal);
        return retVal;
    }

    private ArrayList<ComponentEventGeneralDto> saveDefaultTabs(OccasionType occasionType, BaseOccasionDto dto, Long occasionId ){
        ArrayList<ComponentEventGeneralDto> retVal =new ArrayList<>();
        OutputAPIForm<ArrayList<ItineraryDto>> defaultItinerary;
        ComponentEventGeneralDto componentEvent;
        for(OccasionComponent occasionComponent:occasionType.getOccasionComponents()){
            try{
                if(occasionComponent.getComponent().getComponentName().equals("Itinerary")){
                    defaultItinerary = itinerarySrv.saveDefaultItinerary(dto, occasionId);
                    componentEvent = new ComponentEventGeneralDto( occasionComponent.getComponent().getComponentName(),
                            GeneralUtility.getMessageSrv()!=null?GeneralUtility.getMessageSrv().getMessage(("table.component"+"."+occasionComponent.getComponent().getComponentName()).toLowerCase()):occasionComponent.getComponent().getComponentNameFa(),
                            occasionComponent.getOrder(),defaultItinerary.getData());
                }else{
                    componentEvent = new ComponentEventGeneralDto( occasionComponent.getComponent().getComponentName(),
                            GeneralUtility.getMessageSrv()!=null?GeneralUtility.getMessageSrv().getMessage(("table.component"+"."+occasionComponent.getComponent().getComponentName()).toLowerCase()):occasionComponent.getComponent().getComponentNameFa(),
                            occasionComponent.getOrder(),null);
                }
                retVal.add(componentEvent);
            }catch (Exception e){
                log.error(e.getMessage());
            }
        }
        Collections.sort(retVal);
        return retVal;
    }

    private ArrayList<ComponentEventGeneralDto> editDefaultTabs(Optional<Occasion> occasion,OccasionDto dto ){
        ArrayList<ComponentEventGeneralDto> retVal =new ArrayList<>();
        OutputAPIForm<ArrayList<ItineraryDto>> defaultItinerary;
        ComponentEventGeneralDto componentEvent;
        for(OccasionComponent occasionComponent:occasion.get().getOccasionType().getOccasionComponents()){
            try{
                if(occasionComponent.getComponent().getComponentName().equals("Itinerary")){
                    defaultItinerary = itinerarySrv.editDefaultItinerary(occasion,dto);
                    componentEvent = new ComponentEventGeneralDto( occasionComponent.getComponent().getComponentName(),
                            GeneralUtility.getMessageSrv()!=null?GeneralUtility.getMessageSrv().getMessage(("table.component"+"."+occasionComponent.getComponent().getComponentName()).toLowerCase()):occasionComponent.getComponent().getComponentNameFa(),
                            occasionComponent.getOrder(),defaultItinerary.getData());
                }else{
                    componentEvent = new ComponentEventGeneralDto( occasionComponent.getComponent().getComponentName(),
                            GeneralUtility.getMessageSrv()!=null?GeneralUtility.getMessageSrv().getMessage(("table.component"+"."+occasionComponent.getComponent().getComponentName()).toLowerCase()):occasionComponent.getComponent().getComponentNameFa(),
                            occasionComponent.getOrder(),null);
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
        retVal = retVal.isSuccess() ? StringUtility.checkString(dto.getOccasionName(),false,1,50,false):retVal;
        retVal = retVal.isSuccess() ? Utility.checkMandatoryBaseOccasion(dto):retVal;
        retVal = retVal.isSuccess() ? Utility.checkPic(dto.getPic(),true):retVal;
        retVal = retVal.isSuccess() ? Utility.checkOccasionDateTime(dto.getOccasionLengthType(), dto.getStartDate(), dto.getEndDate()):retVal;
        return retVal;
    }

    public OutputAPIForm<OccasionUsersDto> saveOccasionUsers(OccasionUsersDto dto){
        OutputAPIForm<OccasionUsersDto> retVal = new OutputAPIForm<>();
        try{
            if(hasAccessInsOccasionCost(dto.getOccasionId())){
                OccasionUsers ent = new OccasionUsers(dto);
                occasionUsersRepo.save(ent);
                dto.setOccasionUserId(ent.getOccasionUserId());
                dto.setStateRequest(ent.getStateRequest());
                retVal.setData(dto);
            }else{
                retVal.setSuccess(false);
                retVal.getErrors().add(CodeException.NOT_FIND_REFERENCE);
            }
        }catch (Exception e){
            retVal.setSuccess(false);
            retVal.getErrors().add(CodeException.DATA_BASE_EXCEPTION);
        }
        return retVal;
    }

    public OutputAPIForm<ArrayList<OccasionUsersDto>> listOccasionRequest(CriOccasionDto criOccasion){
        OutputAPIForm<ArrayList<OccasionUsersDto>> retVal = new OutputAPIForm<>();
        ArrayList<OccasionUsersDto> dto = new ArrayList<>();
        try{
            List<OccasionUsers> requestUsers = occasionUsersRepo.getByUserId(InfraSecurityUtils.getCurrentUser(),PageRequest.of(criOccasion.getPage(), pageSize+1, Sort.by("creationDate")));
            if(Objects.nonNull(requestUsers)){
                requestUsers.forEach(requestUser -> dto.add(new OccasionUsersDto(requestUser)));
            }
            retVal.setData(dto);
        }catch (Exception e){
            retVal.setSuccess(false);
            retVal.getErrors().add(CodeException.ACCESS_DENIED);
        }
        return retVal;
    }

    private void checkMobileNumber(OccasionUsersDto dto){
        if(Objects.nonNull(dto) && StringUtils.hasLength(dto.getMobileNumber())){

        }
    }

    private boolean isValidUserForEditOccasion(List<OccasionUsers> occasionUsers){
        boolean retVal = false;
        if(Objects.nonNull(occasionUsers)){
            for(OccasionUsers occasionUser:occasionUsers){
                if(Objects.nonNull(occasionUser) &&
                   Objects.nonNull(occasionUser.getUserId()) &&
                   occasionUser.getUserId().equals(InfraSecurityUtils.getCurrentUser()) &&
                   occasionUser.getStateRequest().equals(StateRequest.Accepted)
                ){
                    retVal = true;
                    break;
                }
            }
        }
        return retVal;
    }

    public OutputAPIForm<OccasionUsersDto> updateOccasionUser(OccasionUsersDto dto){
        OutputAPIForm<OccasionUsersDto> retVal = new OutputAPIForm<>();
        try{
            OccasionUsers ent = occasionUsersRepo.getReferenceById(dto.getOccasionUserId());
            if(ent != null && InfraSecurityUtils.getCurrentUser().equals(ent.getUserId()) && ent.getStateRequest().equals(StateRequest.Requested) ){
                ent.setStateRequest(dto.getStateRequest());
                occasionUsersRepo.save(ent);
                retVal.setData(new OccasionUsersDto(ent));
            }else{
                retVal.setSuccess(false);
                retVal.getErrors().add(CodeException.NOT_FIND_REFERENCE);
            }
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
            OccasionPic ent = occasionPicRepo.getReferenceById(dto.getOccasionPicId());
            if(Objects.nonNull(ent) && hasAccessInsOccasionCost(ent.getOccasionId())){
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
        if(hasAccessInsOccasionCost(criOccasion.getOccasionId())){
            List<OccasionCost> occasionCosts = occasionCostRepo.getAllByOccasionId(criOccasion.getOccasionId(), PageRequest.of(0, pageSize+1, Sort.by("creationDate")));
            if(Objects.nonNull(occasionCosts)){
                for(OccasionCost ent:occasionCosts){
                    dtos.add(new OccasionCostDto(ent.getOccasionCostId(),ent.getOccasionCost(),ent.getUserId(),ent.getOccasionId(),ent.getDescription()));
                }
            }
        }
        retVal.setData(dtos);
        return retVal;
    }

    public OutputAPIForm<ArrayList<OccasionPicDto>> listOccasionPic(CriOccasionDto criOccasion){
        OutputAPIForm<ArrayList<OccasionPicDto>> retVal = new OutputAPIForm<>();
        ArrayList<OccasionPicDto> dtos = new ArrayList<>();
        if(Objects.nonNull(criOccasion.getOccasionId()) && hasAccessInsOccasionCost(criOccasion.getOccasionId())){
            List<OccasionPic> occasionPics = occasionPicRepo.getAllByOccasionId(criOccasion.getOccasionId(), PageRequest.of(0, pageSize+1, Sort.by("creationDate")));
            if(Objects.nonNull(occasionPics)){
                for(OccasionPic ent:occasionPics){
                    dtos.add(new OccasionPicDto(ent.getOccasionPicId(),ent.getOccasionId(),ent.isSharable(), ent.getName(),ent.getPic().getPic(), ent.getPicId()));
                }
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
            OccasionCost ent = occasionCostRepo.getReferenceById(dto.getOccasionCostId());
            if(Objects.nonNull(ent) && hasAccessInsOccasionCost(ent.getOccasionId())){
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
            OccasionCost ent = occasionCostRepo.getReferenceById(dto.getOccasionCostId());
            if(Objects.nonNull(ent) && hasAccessInsOccasionCost(ent.getOccasionId())){
                occasionCostRepo.delete(ent);
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

    private boolean hasAccessInsOccasionCost(Long occasionId){
        boolean retVal = false;
        try{
            Occasion occasion = occasionRepo.getReferenceById(occasionId);
            if((Objects.nonNull(occasion) && Objects.nonNull(occasion.getCreatorUserId()) && occasion.getCreatorUserId().equals(InfraSecurityUtils.getCurrentUser()))){
                retVal = true;
            }else if(Objects.nonNull(occasion) && Objects.nonNull(occasion.getOccasionUsers())){
                for(OccasionUsers usr: occasion.getOccasionUsers()){
                    if(Objects.nonNull(usr) &&
                       Objects.nonNull(usr.getUserId()) &&
                       usr.getUserId().equals(InfraSecurityUtils.getCurrentUser()) &&
                       usr.getStateRequest().equals(StateRequest.Accepted)
                    ){
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

    public OutputAPIForm hasAccessOccasion(Long occasionId){
        OutputAPIForm retVal = new OutputAPIForm();
        if(Objects.isNull(occasionId) || !hasAccessInsOccasionCost(occasionId)){
            retVal.setSuccess(false);
            retVal.getErrors().add(CodeException.ACCESS_DENIED);
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

    public byte[] getImage(OccasionPicDto picDto){
        byte[] retVal ;
        Pic picEnt = picRepo.getPicByPicId(picDto.getPicId());
        if(Objects.nonNull(picEnt) && (picEnt.isPublicImage() || (!picEnt.isPublicImage() && hasAccessToImage(picDto)))){
            retVal = picEnt.getPic();
        }else{
            retVal = getDefaultImage();
        }
        return retVal;
    }

    public boolean hasAccessToImage(OccasionPicDto picDto){
        boolean retVal = false;
        OutputAPIForm<ArrayList<OccasionPicDto>> pics= listOccasionPic(new CriOccasionDto(picDto.getOccasionId()));
        if( Objects.nonNull(pics) && Objects.nonNull(pics.getData()) ){
            for (OccasionPicDto pic:pics.getData()) {
                if(pic.getPicId().equals(picDto.getPicId())){
                    retVal = true;
                    break;
                }
            }
        }
        return retVal;
    }

    public byte[] getDefaultImage(){
        byte[] retVal ;
        try {
            InputStream in = new FileInputStream(ctx.getResource(imagePath + "DefaultImage.png").getFile());
            retVal = IOUtils.toByteArray(in);
        } catch (IOException e) {
            log.error(e.getMessage());
            e.printStackTrace();
            retVal = null;
        }
        return retVal;
    }
}
