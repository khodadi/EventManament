package com.service.services;

import com.form.OutputAPIForm;
import com.service.dto.*;

import java.util.ArrayList;

public interface IOccasionSrv {
    OutputAPIForm<OccasionDto> saveOccasion(BaseOccasionDto dto);
    OutputAPIForm<OccasionDto> editOccasion(OccasionDto dto);
    OutputAPIForm<ArrayList<OccasionDto>> listOccasion(CriOccasionDto criOccasion);
    OutputAPIForm<OccasionPicDto> saveOccasionPic(OccasionPicDto dto);
    OutputAPIForm<OccasionUsersDto> saveOccasionUsers(OccasionUsersDto dto);
    OutputAPIForm<OccasionCostDto> saveOccasionCost(OccasionCostDto dto);
}
