package com.service.services;

import com.dao.entity.Occasion;
import com.form.OutputAPIForm;
import com.service.dto.BaseItineraryDetailDto;
import com.service.dto.BaseOccasionDto;
import com.service.dto.ItineraryDto;
import com.service.dto.OccasionDto;

import java.util.ArrayList;
import java.util.Optional;

public interface IItinerarySrv {
    OutputAPIForm saveDefaultItinerary(BaseOccasionDto baseOccasionDto,Long OccasionId);
    OutputAPIForm<ArrayList<ItineraryDto>> editDefaultItinerary(Optional<Occasion> occasion, OccasionDto dto);
    OutputAPIForm saveItineraryDetail(BaseItineraryDetailDto dto);
}
