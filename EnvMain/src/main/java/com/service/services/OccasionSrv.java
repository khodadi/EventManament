package com.service.services;

import com.api.form.OutputAPIForm;
import com.basedata.CodeException;
import com.dao.entity.Occasion;
import com.dao.entity.Pic;
import com.dao.repository.IOccasionRepo;
import com.dao.repository.IPicRepo;
import com.service.dto.BaseOccasionDto;
import com.service.dto.OccasionDto;
import com.utility.StringUtility;
import com.utility.Utility;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.Objects;

@Service
@Transactional
@Slf4j
public class OccasionSrv implements IOccasionSrv{
    private final IOccasionRepo occasionRepo;
    private final IPicRepo picRepo;

    public OccasionSrv(IOccasionRepo occasionRepo, IPicRepo picRepo) {
        this.occasionRepo = occasionRepo;
        this.picRepo = picRepo;
    }

    public OutputAPIForm<OccasionDto> saveOccasion(BaseOccasionDto dto){
        OutputAPIForm<OccasionDto> retVal = new OutputAPIForm<>();
        try{
            retVal = validateBaseOccasionDto(dto);
            if(retVal.isSuccess()){
                Pic pic = new Pic(dto.getPic(), dto.getOccasionName());
                picRepo.save(pic);
                Occasion occasion = new Occasion(dto,pic.getPicId());
                occasionRepo.save(occasion);
                retVal.setData(new OccasionDto(occasion));
            }
        }catch (Exception e){
            retVal.setSuccess(false);
            retVal.getErrors().add(CodeException.UNDEFINED);
        }
        return retVal;
    }

    public OutputAPIForm validateBaseOccasionDto(BaseOccasionDto dto){
        OutputAPIForm retVal = Utility.checkNull(dto);
        retVal = retVal.isSuccess() ? StringUtility.checkString(dto.getOccasionName(),false,5,50,false):retVal;
        retVal = retVal.isSuccess() ? Utility.checkMandatoryBaseOccasion(dto):retVal;
        retVal = retVal.isSuccess() ? Utility.checkPic(dto.getPic(),true):retVal;
        retVal = retVal.isSuccess() ? Utility.checkOccasionDateTime(dto.getOccasionLengthType(),dto.getStartDate(),dto.getEndDate()):retVal;
        return retVal;

    }


}
