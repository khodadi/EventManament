package com.env.service.services.tab;

import com.env.dao.entity.Itinerary;
import com.env.dao.entity.Occasion;
import com.env.dao.entity.OccasionComponent;
import com.env.service.dto.ComponentEventGeneralDto;
import com.env.service.dto.ItineraryDto;
import com.utility.GeneralUtility;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Collections;

/**
 * @Creator 9/1/2024
 * @Project IntelliJ IDEA
 * @Author k.khodadi
 **/

@Service
@Slf4j
public class TabItinerary implements ITabSrv {
    @Override
    public ComponentEventGeneralDto createTab(Occasion event, OccasionComponent occasionComponent) {
        ArrayList<ItineraryDto> itineraryDtos = new ArrayList<>();
        for(Itinerary itinerary:event.getItineraries()){
            itineraryDtos.add(new ItineraryDto(itinerary));
        }
        Collections.sort(itineraryDtos);
        ComponentEventGeneralDto tab = new ComponentEventGeneralDto( occasionComponent.getComponent().getComponentName(),
                GeneralUtility.getMessageSrv()!=null?GeneralUtility.getMessageSrv().getMessage(("table.component"+"."+occasionComponent.getComponent().getComponentName()).toLowerCase()):occasionComponent.getComponent().getComponentNameFa(),
                occasionComponent.getOrder(),itineraryDtos);

        return tab;
    }
}
