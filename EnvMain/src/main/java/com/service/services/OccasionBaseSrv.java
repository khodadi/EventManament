package com.service.services;

import com.basedata.CodeException;
import com.dao.entity.OccasionType;
import com.dao.entity.Pic;
import com.dao.repository.IOccasionTypeRepo;

import com.dao.repository.IPicRepo;
import com.form.OutputAPIForm;
import com.service.dto.OccasionTypeDto;
import com.utility.StringUtility;
import com.utility.Utility;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

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

    public OccasionBaseSrv(IOccasionTypeRepo occasionTypeRepo, IPicRepo picRepo) {
        this.occasionTypeRepo = occasionTypeRepo;
        this.picRepo = picRepo;
    }

    public OutputAPIForm saveOccasionType(OccasionTypeDto dto) {
        OutputAPIForm retVal = validateOccasionTypeDto(dto);
        try{
            if(retVal.isSuccess()){
                Pic pic = new Pic(dto.getPic(), dto.getOccasionTypeName());
                picRepo.save(pic);
                OccasionType ent = new OccasionType(null,dto.getOccasionTypeName(),dto.getOccasionTypeNameFa(),pic.getPicId(),pic);
                occasionTypeRepo.save(ent);
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
            ArrayList<OccasionTypeDto> occasionTypeDtos = new ArrayList<>();
            List<OccasionType> occasionTypes = occasionTypeRepo.findAll();
            for(OccasionType occasionType:occasionTypes){
                try{
                    occasionTypeDtos.add(new OccasionTypeDto(occasionType.getOccasionTypeId(), occasionType.getOccasionTypeName(),occasionType.getOccasionTypeNameFa(), occasionType.getPic().getPic()));
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

}
