package com.env.service.services;

import com.basedata.generalcode.CodeException;
import com.env.dao.entity.OccasionCost;
import com.env.dao.repository.IOccasionCostRepo;
import com.env.service.dto.CriOccasionDto;
import com.env.service.dto.OccasionCostDto;
import com.form.OutputAPIForm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @Creator 9/28/2024
 * @Project IntelliJ IDEA
 * @Author k.khodadi
 **/

@Service
@Transactional
@Slf4j
public class OccasionCostSrv implements IOccasionCostSrv{

    private final IOccasionCostRepo occasionCostRepo;
    private final IOccasionUserSrv occasionUserSrv;

    public OccasionCostSrv(IOccasionCostRepo occasionCostRepo, IOccasionUserSrv occasionUserSrv) {
        this.occasionCostRepo = occasionCostRepo;
        this.occasionUserSrv = occasionUserSrv;
    }

    public final static int pageSize = 100;

    public OutputAPIForm<ArrayList<OccasionCostDto>> listOccasionCost(CriOccasionDto criOccasion){
        OutputAPIForm<ArrayList<OccasionCostDto>> retVal = occasionUserSrv.hasAccessOccasion(criOccasion.getOccasionId());
        if(retVal.isSuccess()){
            ArrayList<OccasionCostDto> dtos = new ArrayList<>();
            List<OccasionCost> occasionCosts = occasionCostRepo.getAllByOccasionId(criOccasion.getOccasionId(), PageRequest.of(0, pageSize+1, Sort.by("creationDate")));
            if(Objects.nonNull(occasionCosts)){
                for(OccasionCost ent:occasionCosts){
                    dtos.add(new OccasionCostDto(ent.getOccasionCostId(),ent.getOccasionCost(),ent.getUserId(),ent.getOccasionId(),ent.getDescription()));
                }
            }
            retVal.setData(dtos);
        }
        return retVal;
    }

    public OutputAPIForm<OccasionCostDto> saveOccasionCost(OccasionCostDto dto){
        OutputAPIForm<OccasionCostDto> retVal = occasionUserSrv.hasAccessOccasion(dto.getOccasionId());
        try{
            if(retVal.isSuccess()){
                OccasionCost ent = new OccasionCost(dto);
                occasionCostRepo.save(ent);
                dto.setOccasionCostId(ent.getOccasionCostId());
                retVal.setData(dto);
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
            if(Objects.nonNull(ent) && occasionUserSrv.hasAccessOccasion(ent.getOccasionId()).isSuccess()){
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
            if(Objects.nonNull(ent) && occasionUserSrv.hasAccessOccasion(ent.getOccasionId()).isSuccess()){
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
}
