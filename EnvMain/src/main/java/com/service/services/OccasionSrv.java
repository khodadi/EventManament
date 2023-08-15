package com.service.services;

import com.basedata.CodeException;
import com.dao.entity.Occasion;
import com.dao.entity.OccasionComponent;
import com.dao.entity.OccasionType;
import com.dao.entity.Pic;
import com.dao.repository.IOccasionRepo;
import com.dao.repository.IOccasionTypeRepo;
import com.dao.repository.IPicRepo;
import com.form.OutputAPIForm;
import com.service.dto.BaseOccasionDto;
import com.service.dto.ComponentEventDto;
import com.service.dto.OccasionDto;
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

    public OccasionSrv(IOccasionRepo occasionRepo, IPicRepo picRepo, IOccasionTypeRepo occasionTypeRepo) {
        this.occasionRepo = occasionRepo;
        this.picRepo = picRepo;
        this.occasionTypeRepo = occasionTypeRepo;
    }

    public OutputAPIForm<OccasionDto> saveOccasion(BaseOccasionDto dto){
        OutputAPIForm<OccasionDto> retVal = new OutputAPIForm<>();
        try{
            retVal = validateBaseOccasionDto(dto);
            if(retVal.isSuccess()){
                OccasionType occasionType = occasionTypeRepo.getOne(dto.getOccasionTypeId());
                if(occasionType != null && occasionType.getOccasionComponents()!= null){
                    Pic pic = new Pic(dto.getPic(), dto.getOccasionName());
                    picRepo.save(pic);
                    Occasion occasion = new Occasion(dto,pic.getPicId());
                    occasionRepo.save(occasion);
                    retVal.setData(new OccasionDto(occasion, getTabs(occasionType)));
                }
            }
        }catch (Exception e){
            retVal.setSuccess(false);
            retVal.getErrors().add(CodeException.UNDEFINED);
        }
        return retVal;
    }

    private ArrayList<ComponentEventDto> getTabs(OccasionType occasionType ){
        ArrayList<ComponentEventDto> retVal =new ArrayList<>();
        for(OccasionComponent occasionComponent:occasionType.getOccasionComponents()){
            try{
                retVal.add(new ComponentEventDto(
                        occasionComponent.getComponent().getComponentName(),
                        occasionComponent.getComponent().getComponentNameFa(),
                        occasionComponent.getOrder()
                ));
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


}
