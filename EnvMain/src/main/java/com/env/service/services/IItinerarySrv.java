package com.env.service.services;

import com.env.dao.entity.Occasion;
import com.form.OutputAPIForm;
import com.env.service.dto.BaseItineraryDetailDto;
import com.env.service.dto.BaseOccasionDto;
import com.env.service.dto.ItineraryDto;
import com.env.service.dto.OccasionDto;

import java.util.ArrayList;
import java.util.Optional;

public interface IItinerarySrv {
    OutputAPIForm saveDefaultItinerary(BaseOccasionDto baseOccasionDto,Long OccasionId);
    OutputAPIForm<ArrayList<ItineraryDto>> editDefaultItinerary(Optional<Occasion> occasion, OccasionDto dto);
    OutputAPIForm saveItineraryDetail(BaseItineraryDetailDto dto);
}
