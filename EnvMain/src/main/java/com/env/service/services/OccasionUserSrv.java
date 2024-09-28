package com.env.service.services;

import com.basedata.generalcode.CodeException;
import com.env.basedata.StateRequest;
import com.env.dao.entity.Occasion;
import com.env.dao.entity.OccasionUsers;
import com.env.dao.repository.IOccasionRepo;
import com.env.dao.repository.IOccasionUsersRepo;
import com.env.service.dto.CriOccasionDto;
import com.env.service.dto.OccasionUsersDto;
import com.form.OutputAPIForm;
import com.utility.InfraSecurityUtils;
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
public class OccasionUserSrv implements IOccasionUserSrv{

    private final IOccasionRepo occasionRepo;
    private final IOccasionUsersRepo occasionUsersRepo;

    public OccasionUserSrv(IOccasionRepo occasionRepo, IOccasionUsersRepo occasionUsersRepo) {
        this.occasionRepo = occasionRepo;
        this.occasionUsersRepo = occasionUsersRepo;
    }

    public final static int pageSize = 100;

    private boolean hasAccessToOccasion(Long occasionId){
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
        if(Objects.isNull(occasionId) || !hasAccessToOccasion(occasionId)){
            retVal.setSuccess(false);
            retVal.getErrors().add(CodeException.ACCESS_DENIED);
        }
        return retVal;
    }

    public OutputAPIForm<OccasionUsersDto> saveOccasionUsers(OccasionUsersDto dto){
        OutputAPIForm<OccasionUsersDto> retVal = hasAccessOccasion(dto.getOccasionId());
        try{
            if(retVal.isSuccess()){
                OccasionUsers ent = new OccasionUsers(dto);
                occasionUsersRepo.save(ent);
                dto.setOccasionUserId(ent.getOccasionUserId());
                dto.setStateRequest(ent.getStateRequest());
                retVal.setData(dto);
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
            List<OccasionUsers> requestUsers = occasionUsersRepo.getByUserId(InfraSecurityUtils.getCurrentUser(), PageRequest.of(criOccasion.getPage(), pageSize+1, Sort.by("creationDate")));
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
}
