package com.env.service.services;

import com.env.dao.entity.OccasionCost;
import com.env.service.dto.CriOccasionDto;
import com.env.service.dto.OccasionCostDto;
import com.form.OutputAPIForm;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @Creator 9/28/2024
 * @Project IntelliJ IDEA
 * @Author k.khodadi
 **/


public interface IOccasionCostSrv {
    OutputAPIForm<ArrayList<OccasionCostDto>> listOccasionCost(CriOccasionDto criOccasion);
    OutputAPIForm<OccasionCostDto> saveOccasionCost(OccasionCostDto dto);
    OutputAPIForm<OccasionCostDto> updateOccasionCost(OccasionCostDto dto);
    OutputAPIForm deleteOccasionCost(OccasionCostDto dto);
}
