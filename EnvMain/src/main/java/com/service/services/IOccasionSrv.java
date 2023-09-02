package com.service.services;

import com.form.OutputAPIForm;
import com.service.dto.BaseOccasionDto;
import com.service.dto.OccasionDto;
import com.service.dto.OccasionPicDto;

public interface IOccasionSrv {
    OutputAPIForm<OccasionDto> saveOccasion(BaseOccasionDto dto);
    OutputAPIForm<OccasionPicDto> saveOccasionPic(OccasionPicDto dto);
}
