package com.env.service.services;


import com.form.OutputAPIForm;
import com.env.service.dto.*;
import com.service.dto.BaseData;

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
    OutputAPIForm<OccasionTypeDto> saveOccasionType(OccasionTypeDto dto);
    OutputAPIForm<ActivityDto> saveActivity(ActivityDto dto);
    OutputAPIForm<ArrayList<BaseData>> getBaseData();


}
