package com.env.service.services;

import com.form.OutputAPIForm;
import com.env.service.dto.*;

import java.util.ArrayList;

public interface IOccasionSrv {
    OutputAPIForm<OccasionDto> saveOccasion(BaseOccasionDto dto);
    OutputAPIForm<OccasionDto> editOccasion(OccasionDto dto);
    OutputAPIForm<ArrayList<OccasionDto>> listOccasion(CriOccasionDto criOccasion);
    OutputAPIForm<ArrayList<OccasionPicDto>> listOccasionPic(CriOccasionDto criOccasion);
    OutputAPIForm<OccasionPicDto> saveOccasionPic(OccasionPicDto dto);
    OutputAPIForm deleteOccasionPic(OccasionPicDto dto);
    OutputAPIForm<ArrayList<OccasionCostDto>> listOccasionCost(CriOccasionDto criOccasion);
    OutputAPIForm<OccasionUsersDto> saveOccasionUsers(OccasionUsersDto dto);
    OutputAPIForm<OccasionUsersDto> updateOccasionUser(OccasionUsersDto dto);
    OutputAPIForm<OccasionCostDto> saveOccasionCost(OccasionCostDto dto);
    OutputAPIForm<OccasionCostDto> updateOccasionCost(OccasionCostDto dto);
    OutputAPIForm deleteOccasionCost(OccasionCostDto dto);
    byte[] getImage(OccasionPicDto picDto);
}
