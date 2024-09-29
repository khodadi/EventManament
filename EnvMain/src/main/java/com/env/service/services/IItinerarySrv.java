package com.env.service.services;

import com.env.dao.entity.Occasion;
import com.env.service.dto.*;
import com.form.OutputAPIForm;

import java.util.ArrayList;
import java.util.Optional;

public interface IItinerarySrv {
    OutputAPIForm saveItineraryDetail(BaseItineraryDetailDto dto);
    OutputAPIForm updateItineraryDetail(BaseItineraryDetailDto dto);
    OutputAPIForm deleteItineraryDetail(OccasionItineraryDetailDto dto);
}
