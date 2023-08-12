package com.service.services;

import com.basedata.CodeException;
import com.dao.entity.OccasionType;
import com.dao.repository.IOccasionTypeRepo;

import com.form.OutputAPIForm;
import com.service.dto.OccasionTypeDto;
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

    public OccasionBaseSrv(IOccasionTypeRepo occasionTypeRepo) {
        this.occasionTypeRepo = occasionTypeRepo;
    }

    public OutputAPIForm<ArrayList<OccasionTypeDto>> getAllOccasionTypes(){
        OutputAPIForm<ArrayList<OccasionTypeDto>> retVal = new OutputAPIForm<>();
        try{
            ArrayList<OccasionTypeDto> occasionTypeDtos = new ArrayList<>();
            List<OccasionType> occasionTypes = occasionTypeRepo.findAll();
            for(OccasionType occasionType:occasionTypes){
                try{
                    occasionTypeDtos.add(new OccasionTypeDto(occasionType.getOccasionTypeId(), occasionType.getOccasionTypeName(), occasionType.getPic().getPic()));
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

}
