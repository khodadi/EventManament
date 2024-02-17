package com.env.service.services;

import com.basedata.generalcode.CodeException;
import com.env.basedata.OccasionLengthTypeEnum;
import com.env.basedata.StateRequest;
import com.env.dao.entity.*;
import com.env.dao.repository.*;
import com.env.service.dto.ActivityDto;
import com.env.service.dto.EquipmentDto;
import com.env.service.dto.EventDto;
import com.env.service.dto.OccasionTypeDto;
import com.env.utility.Utility;
import com.form.OutputAPIForm;
import com.service.dto.BaseData;
import com.service.services.IMessageBundleSrv;
import com.utility.GeneralUtility;
import com.utility.StringUtility;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

/**
 * @Creator 8/12/2023
 * @Project IntelliJ IDEA
 * @Author k.khodadi
 **/

@Service
@Transactional
@Slf4j
public class OccasionBaseSrv  implements IOccasionBaseSrv{

    private final IOccasionTypeRepo occasionTypeRepo;
    private final IPicRepo picRepo;
    private final IEventRepo eventRepo;
    private final IEquipmentRepo equipmentRepo;
    private final IActivityRepo activityRepo;
    private IMessageBundleSrv messageBundleSrv;

    public OccasionBaseSrv(IOccasionTypeRepo occasionTypeRepo, IPicRepo picRepo, IEventRepo eventRepo, IEquipmentRepo equipmentRepo, IActivityRepo activityRepo, IMessageBundleSrv messageBundleSrv) {
        this.occasionTypeRepo = occasionTypeRepo;
        this.picRepo = picRepo;
        this.eventRepo = eventRepo;
        this.equipmentRepo = equipmentRepo;
        this.activityRepo = activityRepo;
        this.messageBundleSrv = messageBundleSrv;
    }

    public OutputAPIForm saveOccasionType(OccasionTypeDto dto) {
        OutputAPIForm retVal = validateOccasionTypeDto(dto);
        try{
            if(retVal.isSuccess()){
                Pic pic = new Pic(dto.getPic(), dto.getOccasionTypeName());
                picRepo.save(pic);
                OccasionType ent = new OccasionType(null,dto.getOccasionTypeName(),dto.getOccasionTypeNameFa(),pic.getPicId(),pic,null);
                occasionTypeRepo.save(ent);
                dto.setOccasionTypeId(ent.getOccasionTypeId());
                dto.setPicId(pic.getPicId());
                dto.setPic(null);
                retVal.setData(dto);
            }
        }catch (Exception e){
            retVal.setSuccess(false);
            retVal.getErrors().add(CodeException.DATA_BASE_EXCEPTION);
        }
        return retVal;
    }

    public OutputAPIForm<ActivityDto> saveActivity(ActivityDto dto){
        OutputAPIForm<ActivityDto> retVal = new OutputAPIForm<>();
        try{
            if(retVal.isSuccess()){
                Pic pic = new Pic(dto.getPic(), dto.getName());
                picRepo.save(pic);
                Activity ent = new Activity(null,dto.getName(),dto.getNameFa(),pic.getPicId(),pic);
                activityRepo.save(ent);
                dto.setPic(null);
                dto.setActivityId(ent.getActivityId());
                dto.setPicId(ent.getPicId());
                retVal.setData(dto);
            }
        }catch (Exception e){
            retVal.setSuccess(false);
            retVal.getErrors().add(CodeException.DATA_BASE_EXCEPTION);
        }
        return retVal;
    }

    public OutputAPIForm<ArrayList<OccasionTypeDto>> getAllOccasionTypes(){
        OutputAPIForm<ArrayList<OccasionTypeDto>> retVal = new OutputAPIForm<>();
        try{
            String nameTrl ;
            ArrayList<OccasionTypeDto> occasionTypeDtos = new ArrayList<>();
            List<OccasionType> occasionTypes = occasionTypeRepo.findAll();
            for(OccasionType occasionType:occasionTypes){
                try{
                    nameTrl =  messageBundleSrv.getMessage("table.occasionType."+occasionType.getOccasionTypeName()) ;
                    occasionTypeDtos.add(new OccasionTypeDto(occasionType.getOccasionTypeId(),
                                                            occasionType.getOccasionTypeName(),
                                                            StringUtility.hasLength(nameTrl)?nameTrl : occasionType.getOccasionTypeNameFa(),
                                                            occasionType.getPic().getPic(),
                                                            occasionType.getPicId()));
                }catch (Exception e){
                    log.error(e.getMessage());
                }
            }
            retVal.setData(occasionTypeDtos);
        }catch (Exception e){
            retVal.setSuccess(false);
            retVal.getErrors().add(CodeException.DATA_BASE_EXCEPTION);
        }
        return retVal;
    }

    public OutputAPIForm<ArrayList<ActivityDto>> getAllActivity(){
        OutputAPIForm<ArrayList<ActivityDto>> retVal = new OutputAPIForm<>();
        try{
            String nameTrl;
            ArrayList<ActivityDto> activityDtos = new ArrayList<>();
            List<Activity> activities = activityRepo.findAll();
            for(Activity activity:activities){
                try{
                    nameTrl = messageBundleSrv.getMessage("table.activity"+"."+activity.getName());
                    activityDtos.add(new ActivityDto(activity.getActivityId(),
                                                        StringUtility.hasLength(nameTrl)?nameTrl: activity.getNameFa()  ,
                                                        activity.getName(),
                                                        activity.getPic().getPicId(),
                                                        activity.getPic().getPic()));
                }catch (Exception e){
                    log.error(e.getMessage());
                }
            }
            retVal.setData(activityDtos);
        }catch (Exception e){
            retVal.setSuccess(false);
            retVal.getErrors().add(CodeException.DATA_BASE_EXCEPTION);
        }
        return retVal;
    }

    public OutputAPIForm validateOccasionTypeDto(OccasionTypeDto dto){
        OutputAPIForm retVal = new OutputAPIForm();
        try{
            retVal = Utility.checkNull(dto);
            retVal = retVal.isSuccess() ? Utility.checkMandatoryOccasionTypeDto(dto):retVal;
            retVal = retVal.isSuccess() ? StringUtility.checkString(dto.getOccasionTypeName(),false,1,50,false):retVal;
            retVal = retVal.isSuccess() ? Utility.checkPic(dto.getPic(),true):retVal;

        }catch (Exception e){
            retVal.setSuccess(false);
            retVal.getErrors().add(CodeException.UNDEFINED);
        }
        return retVal;

    }

    public OutputAPIForm<ArrayList<BaseData>> getBaseData(){
        OutputAPIForm<ArrayList<BaseData>> retVal = new OutputAPIForm<>();
        retVal.setData(new ArrayList<>());
        try{
            retVal.getData().add(OccasionLengthTypeEnum.getLovOccasionType());
            retVal.getData().add(StateRequest.getLovStatusRequest());
        }catch (Exception e){
            retVal.setSuccess(false);
            retVal.getErrors().add(CodeException.UNDEFINED);
        }
        return retVal;
    }

    public OutputAPIForm<EventDto> getAllEvent(){
        OutputAPIForm<EventDto> retVal = new OutputAPIForm<>();
        try {
            List<Event> events = eventRepo.findAll();
            if(Objects.nonNull(events) && events.size() > 0 ){
                EventDto eventDto = new EventDto(events.get(0));
                for(int i=1;i <events.size();i++){
                    findAndAddToTree(eventDto,events.get(i));
                }
                retVal.setData(eventDto);
            }
        }catch (Exception e){
            retVal.setSuccess(false);
            retVal.getErrors().add(CodeException.DATA_BASE_EXCEPTION);
            log.error(e.getMessage());
        }
        return retVal;
    }
    public OutputAPIForm<EquipmentDto> getAllEquipment(){
        OutputAPIForm<EquipmentDto> retVal = new OutputAPIForm<>();
        try {
            List<Equipment> equipments = equipmentRepo.findAll();
            if(Objects.nonNull(equipments) && equipments.size() > 0 ){
                EquipmentDto equipmentDto = new EquipmentDto(equipments.get(0));
                for(int i=1;i <equipments.size();i++){
                    findAndAddToTree(equipmentDto,equipments.get(i));
                }
                retVal.setData(equipmentDto);
            }
        }catch (Exception e){
            retVal.setSuccess(false);
            retVal.getErrors().add(CodeException.DATA_BASE_EXCEPTION);
            log.error(e.getMessage());
        }
        return retVal;
    }

    public void findAndAddToTree(EventDto dto,Event ent){
        if(dto.getEventId().equals(ent.getParentEventId())){
            dto.getChildren().add(new EventDto(ent));
        }else{
            for(EventDto child:dto.getChildren()){
                findAndAddToTree(child,ent);
            }
        }
    }

    public void findAndAddToTree(EquipmentDto dto, Equipment ent){
        if(dto.getEquipmentId().equals(ent.getParentEquipmentId())){
            dto.getChildren().add(new EquipmentDto(ent));
        }else{
            for(EquipmentDto child:dto.getChildren()){
                findAndAddToTree(child,ent);
            }
        }
    }
}
