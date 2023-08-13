package com.service.services;


import com.form.OutputAPIForm;
import com.service.dto.OccasionTypeDto;

import java.util.ArrayList;

/**
 * @Creator 8/12/2023
 * @Project IntelliJ IDEA
 * @Author k.khodadi
 **/


public interface IOccasionBaseSrv {

    OutputAPIForm<ArrayList<OccasionTypeDto>> getAllOccasionTypes();
    OutputAPIForm saveOccasionType(OccasionTypeDto dto);
}
