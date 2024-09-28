package com.env.service.services;

import com.basedata.generalcode.CodeException;
import com.env.basedata.StateRequest;
import com.env.dao.entity.*;
import com.env.dao.repository.*;
import com.env.service.dto.*;
import com.env.service.services.tab.ITabSrv;
import com.env.utility.Utility;
import com.form.OutputAPIForm;
import com.utility.DateUtils;
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
import java.sql.Timestamp;
import java.util.*;

@Service
@Transactional
@Slf4j
public class OccasionSrv implements IOccasionSrv{

    private final IOccasionRepo occasionRepo;
    private final IPicRepo picRepo;
    private final IOccasionTypeRepo occasionTypeRepo;
    private final IOccasionPicRepo occasionPicRepo;
    private final IOccasionUsersRepo occasionUsersRepo;
    private final IFactory<ITabSrv,String> tabFactory;
    private final IItineraryRepo itineraryRepo;
    private final IPlaceSrv placeSrv;
    private final IPlaceRepo placeRepo;
    private final IOccasionUserSrv occasionUserSrv;

    public final static int pageSize = 100;
    @Autowired
    private ApplicationContext ctx;

    @Value("${imagePath:./files/images/}")
    private String imagePath;

    public OccasionSrv(IOccasionRepo occasionRepo,
                       IPicRepo picRepo,
                       IOccasionTypeRepo occasionTypeRepo,
                       IItineraryRepo itineraryRepo,
                       IOccasionPicRepo occasionPicRepo,
                       IOccasionUsersRepo occasionUsersRepo,
                       IFactory tabFactory,
                       IPlaceSrv placeSrv,
                       IPlaceRepo placeRepo,
                       IOccasionUserSrv occasionUserSrv) {
        this.occasionRepo = occasionRepo;
        this.picRepo = picRepo;
        this.occasionTypeRepo = occasionTypeRepo;
        this.itineraryRepo = itineraryRepo;
        this.occasionPicRepo = occasionPicRepo;
        this.occasionUsersRepo = occasionUsersRepo;
        this.tabFactory = tabFactory;
        this.placeSrv = placeSrv;
        this.placeRepo = placeRepo;
        this.occasionUserSrv = occasionUserSrv;
    }

    public OutputAPIForm<OccasionDto> saveOccasion(BaseOccasionDto dto){
        OutputAPIForm<OccasionDto> retVal = new OutputAPIForm<>();
        try{
            retVal = validateBaseOccasionDto(dto);
            if(retVal.isSuccess()){
                Optional<OccasionType> occasionType = occasionTypeRepo.findById(dto.getOccasionTypeId());
                if(occasionType.isPresent()){
                    Pic pic = new Pic(dto.getPic(), dto.getOccasionName());
                    picRepo.save(pic);
                    Occasion occasion = new Occasion(dto,pic.getPicId());
                    occasionRepo.save(occasion);
                    OccasionUsers occasionUser = new OccasionUsers(null,occasion.getCreatorUserId(),occasion.getOccasionId(),StateRequest.Accepted,null,false);
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
        OutputAPIForm<OccasionDto> retVal = dto.checkMandatoryForUpdate();
        retVal = retVal.isSuccess() ? dto.checkFormatData(): retVal;
        retVal =  retVal.isSuccess()?occasionUserSrv.hasAccessOccasion(dto.getOccasionId()):retVal;
        if(retVal.isSuccess()){
            Optional<Occasion> occasion = occasionRepo.findById(dto.getOccasionId());
            if(occasion.isPresent()){
                retVal = Objects.nonNull(dto.getSourceId()) ? validatePlaceId(dto.getSourceId()): retVal;
                retVal = retVal.isSuccess()?updatePicOccasion(occasion.get(),dto):retVal;
                dto.updateEnt(occasion.get());
                occasionRepo.save(occasion.get());
                retVal.setData(new OccasionDto(occasion.get(),editDefaultTabs(occasion,dto),false));
            }else{
                retVal.getErrors().add(CodeException.NOT_FIND_REFERENCE);
                retVal.setSuccess(false);
            }
        }
        return retVal;
    }

    public OutputAPIForm updatePicOccasion(Occasion occasion,OccasionDto dto){
        OutputAPIForm retVal = new OutputAPIForm();
        try{
            if(Objects.nonNull(dto.getPic())){
                picRepo.deleteById(occasion.getPicId());
                Pic pic = new Pic(dto.getPic(),Objects.isNull(dto.getOccasionName()) ?occasion.getOccasionName():dto.getOccasionName());
                picRepo.save(pic);
                dto.setPicId(pic.getPicId());
            }
        }catch (Exception e){
            log.error("Error in edit pic" ,e);
            retVal.setSuccess(false);
            retVal.getErrors().add(CodeException.DATA_BASE_EXCEPTION);
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
                                                         PageRequest.of(criOccasion.getPage(), pageSize+1, Sort.by("startDate"))) ;
        }else{
            log.info("list sharable occasion ");
            occasions = occasionRepo.getOccasionPublic(criOccasion.getOccasionId(), PageRequest.of(criOccasion.getPage(), pageSize+1, Sort.by("startDate")));
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
                    defaultItinerary = saveDefaultItinerary(dto, occasionId);
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

    public OutputAPIForm<ArrayList<ItineraryDto>> saveDefaultItinerary(BaseOccasionDto baseOccasionDto, Long occasionId) {
        OutputAPIForm<ArrayList<ItineraryDto>> retVal = new OutputAPIForm<>();
        ArrayList<ItineraryDto> itineraries = new ArrayList<>();
        try{
            long numberOfDate = DateUtils.diffTwoDateDay(DateUtils.getBeginOfDay(baseOccasionDto.getStartDate()),DateUtils.getBeginOfDay(baseOccasionDto.getEndDate()));
            for(int i = 0;i <= numberOfDate;i++){
                Itinerary itinerary = new Itinerary(occasionId,DateUtils.addDays(DateUtils.getBeginOfDay(baseOccasionDto.getStartDate()),i));
                itineraryRepo.save(itinerary);
                itineraries.add(new ItineraryDto(itinerary.getItineraryId(),itinerary.getOccasionId(),itinerary.getItineraryDate(),null));
            }
            retVal.setData(itineraries);
        }catch (Exception e){
            retVal.setSuccess(false);
            retVal.getErrors().add(CodeException.DATA_BASE_EXCEPTION);
            log.error(e.getMessage());
            e.printStackTrace();
        }
        return retVal;
    }

    private ArrayList<ComponentEventGeneralDto> editDefaultTabs(Optional<Occasion> occasion,OccasionDto dto ){
        ArrayList<ComponentEventGeneralDto> retVal =new ArrayList<>();
        OutputAPIForm<ArrayList<ItineraryDto>> defaultItinerary;
        ComponentEventGeneralDto componentEvent;
        for(OccasionComponent occasionComponent:occasion.get().getOccasionType().getOccasionComponents()){
            try{
                if(occasionComponent.getComponent().getComponentName().equals("Itinerary")){
                    defaultItinerary = editDefaultItinerary(occasion,dto);
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

    public  OutputAPIForm<ArrayList<ItineraryDto>> editDefaultItinerary(Optional<Occasion> occasion,OccasionDto dto){
        OutputAPIForm<ArrayList<ItineraryDto>> retVal = deleteForEditDefaultItinerary(occasion,dto);
        if(retVal.isSuccess()){
            insertForEditDefaultItinerary(occasion,dto,retVal);
        }
        return retVal;
    }

    public OutputAPIForm<ArrayList<ItineraryDto>> deleteForEditDefaultItinerary(Optional<Occasion> occasion,OccasionDto dto){
        OutputAPIForm<ArrayList<ItineraryDto>> retVal = new OutputAPIForm();
        ArrayList<ItineraryDto> itineraryDtos = new ArrayList<>();
        boolean delItinerary = true;
        Timestamp startDate;
        if(occasion.isPresent()){
            ArrayList<Itinerary> delItineraries = new ArrayList<>();
            for(Itinerary itinerary:occasion.get().getItineraries()){
                startDate = DateUtils.getBeginOfDay(new Timestamp(dto.getStartDate().getTime()));
                while (startDate.before(DateUtils.getBeginOfDay(dto.getEndDate()))){
                    delItinerary = true;
                    if(itinerary.getItineraryDate().equals(startDate)){
                        delItinerary = false;
                        break;
                    }
                    startDate = DateUtils.addDays(startDate,1);
                }
                if(delItinerary){
                    delItineraries.add(itinerary);
                }else{
                    itineraryDtos.add(new ItineraryDto(itinerary));
                }
            }
            itineraryRepo.deleteAll(delItineraries);
        }
        retVal.setData(itineraryDtos);
        return retVal;
    }

    public void insertForEditDefaultItinerary(Optional<Occasion> occasion, OccasionDto dto, OutputAPIForm<ArrayList<ItineraryDto>> retVal){
        boolean insetItinerary ;
        Timestamp startDate;
        if(occasion.isPresent()){
            Itinerary addItinerary;
            startDate = DateUtils.getBeginOfDay(new Timestamp(dto.getStartDate().getTime()));
            while (startDate.before(DateUtils.getBeginOfDay(dto.getEndDate()))){
                insetItinerary = true;
                for(Itinerary itinerary:occasion.get().getItineraries()){
                    if(itinerary.getItineraryDate().equals(startDate)){
                        insetItinerary = false;
                        break;
                    }
                }
                if(insetItinerary){
                    addItinerary = new Itinerary(occasion.get().getOccasionId(), startDate);
                    itineraryRepo.save(addItinerary);
                    retVal.getData().add(new ItineraryDto(addItinerary));
                }
                startDate = DateUtils.addDays(startDate,1);
            }
        }
    }

    public OutputAPIForm validateBaseOccasionDto(BaseOccasionDto dto){
        OutputAPIForm retVal = Utility.checkNull(dto);
        retVal = retVal.isSuccess() ? dto.checkMandatoryForInsert():retVal;
        retVal = retVal.isSuccess() ? dto.checkFormatData():retVal;
        retVal = retVal.isSuccess() ? validatePlaceId(dto.getSourceId()):retVal;
        return retVal;
    }

    public OutputAPIForm validatePlaceId(Long placeId ){
        OutputAPIForm retVal = new OutputAPIForm();
        Optional<Place> place = placeRepo.findById(placeId);
        if(!place.isPresent()){
            retVal.setSuccess(false);
            retVal.getErrors().add(CodeException.NOT_FIND_REFERENCE);
        }
        return retVal;
    }

    public OutputAPIForm<OccasionPicDto> saveOccasionPic(OccasionPicDto dto){
        OutputAPIForm<OccasionPicDto> retVal = occasionUserSrv.hasAccessOccasion(dto.getOccasionId());
        try{
            if(retVal.isSuccess()){
                Pic pic = new Pic(dto.getPic(), dto.getName());
                picRepo.save(pic);
                OccasionPic ent = new OccasionPic(dto,pic);
                occasionPicRepo.save(ent);
                dto.setPicId(pic.getPicId());
                dto.setOccasionPicId(ent.getOccasionPicId());
                dto.setPic(null);
                retVal.setData(dto);
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
            if(Objects.nonNull(ent) && occasionUserSrv.hasAccessOccasion(ent.getOccasionId()).isSuccess()){
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

    public OutputAPIForm<ArrayList<OccasionPicDto>> listOccasionPic(CriOccasionDto criOccasion){
        OutputAPIForm<ArrayList<OccasionPicDto>> retVal = new OutputAPIForm<>();
        ArrayList<OccasionPicDto> dtos = new ArrayList<>();
        if(Objects.nonNull(criOccasion.getOccasionId()) && occasionUserSrv.hasAccessOccasion(criOccasion.getOccasionId()).isSuccess()){
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
