package com.service.services;

import com.form.OutputAPIForm;
import com.service.dto.*;

public interface IOccasionSrv {
    OutputAPIForm<OccasionDto> saveOccasion(BaseOccasionDto dto);
    OutputAPIForm<OccasionPicDto> saveOccasionPic(OccasionPicDto dto);
    OutputAPIForm<OccasionUsersDto> saveOccasionUsers(OccasionUsersDto dto);
    OutputAPIForm<OccasionCostDto> saveOccasionCost(OccasionCostDto dto);
}
