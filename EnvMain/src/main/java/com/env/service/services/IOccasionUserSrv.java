package com.env.service.services;

import com.env.service.dto.CriOccasionDto;
import com.env.service.dto.OccasionUsersDto;
import com.form.OutputAPIForm;

import java.util.ArrayList;

/**
 * @Creator 9/28/2024
 * @Project IntelliJ IDEA
 * @Author k.khodadi
 **/


public interface IOccasionUserSrv {

    OutputAPIForm hasAccessOccasion(Long occasionId);
    OutputAPIForm<OccasionUsersDto> saveOccasionUsers(OccasionUsersDto dto);
    OutputAPIForm<ArrayList<OccasionUsersDto>> listOccasionRequest(CriOccasionDto criOccasion);
    OutputAPIForm<OccasionUsersDto> updateOccasionUser(OccasionUsersDto dto);

}
