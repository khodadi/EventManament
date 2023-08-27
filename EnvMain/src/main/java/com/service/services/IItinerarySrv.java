package com.service.services;

import com.form.OutputAPIForm;
import com.service.dto.BaseItineraryDetailDto;
import com.service.dto.BaseOccasionDto;

public interface IItinerarySrv {
    OutputAPIForm saveDefaultItinerary(BaseOccasionDto baseOccasionDto,Long OccasionId);
    OutputAPIForm saveItineraryDetail(BaseItineraryDetailDto dto);
}
