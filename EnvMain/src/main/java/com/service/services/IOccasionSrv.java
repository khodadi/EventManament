package com.service.services;

import com.api.form.OutputAPIForm;
import com.service.dto.BaseOccasionDto;
import com.service.dto.OccasionDto;

public interface IOccasionSrv {
    OutputAPIForm<OccasionDto> saveOccasion(BaseOccasionDto dto);
}
