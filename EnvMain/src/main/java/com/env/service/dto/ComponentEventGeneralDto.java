package com.env.service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 * @Creator 9/1/2024
 * @Project IntelliJ IDEA
 * @Author k.khodadi
 **/

@Slf4j
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ComponentEventGeneralDto<T>  implements Comparable<ComponentEventGeneralDto>{

    private String componentName;
    private String componentNameFa;
    private int order;
    private T data;

    @Override
    public int compareTo(ComponentEventGeneralDto componentEventDto) {
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
