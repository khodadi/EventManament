package com.env.service.services.tab;

import com.env.dao.entity.Occasion;
import com.env.dao.entity.OccasionComponent;
import com.env.service.dto.ComponentEventGeneralDto;
import com.env.service.dto.ITab;

/**
 * @Creator 9/1/2024
 * @Project IntelliJ IDEA
 * @Author k.khodadi
 **/


public interface ITabSrv {

    ComponentEventGeneralDto createTab(Occasion event, OccasionComponent occasionComponent);
}
