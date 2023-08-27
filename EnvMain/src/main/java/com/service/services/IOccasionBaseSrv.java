package com.service.services;


import com.form.OutputAPIForm;
import com.service.dto.*;

import java.util.ArrayList;

/**
 * @Creator 8/12/2023
 * @Project IntelliJ IDEA
 * @Author k.khodadi
 **/


public interface IOccasionBaseSrv {

    OutputAPIForm<ArrayList<OccasionTypeDto>> getAllOccasionTypes();
    OutputAPIForm<ArrayList<ActivityDto>> getAllActivity();
    OutputAPIForm<EventDto> getAllEvent();
    OutputAPIForm<EquipmentDto> getAllEquipment();
    OutputAPIForm saveOccasionType(OccasionTypeDto dto);
    OutputAPIForm saveActivity(ActivityDto dto);
    OutputAPIForm<ArrayList<BaseData>> getBaseData();


}
