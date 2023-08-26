package com.service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;

@Slf4j
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ComponentEventDto implements Comparable<ComponentEventDto> {
    private String componentName;
    private String componentNameFa;
    private int order;
    private ArrayList<ItineraryDto> Itineraries;

    public ComponentEventDto(String componentName, String componentNameFa, int order) {
        this.componentName = componentName;
        this.componentNameFa = componentNameFa;
        this.order = order;
    }

    @Override
    public int compareTo(ComponentEventDto componentEventDto) {
        int retVal = 0;
        try{
            if(componentEventDto.getOrder() > this.getOrder()){
                retVal = -1;
            }else{
                retVal = 1;
            }
        }catch (Exception e){
            log.error(e.getMessage());
        }

        return retVal;
    }
}
