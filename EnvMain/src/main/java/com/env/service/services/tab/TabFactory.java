package com.env.service.services.tab;

import com.env.service.services.IFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Creator 9/1/2024
 * @Project IntelliJ IDEA
 * @Author k.khodadi
 **/

@Service
@Transactional
@Slf4j
public class TabFactory implements IFactory<ITabSrv,String> {

    private final ITabSrv tabItinerary;

    private final ITabSrv tabGeneral;

    public TabFactory(ITabSrv tabItinerary, ITabSrv tabGeneral) {
        this.tabItinerary = tabItinerary;
        this.tabGeneral = tabGeneral;
    }

    public  ITabSrv factory(String componentName){
        ITabSrv retVal = null;
        if(componentName.equals("Itinerary")){
            retVal = tabItinerary;
        }else{
            retVal = tabGeneral;
        }
        return retVal;
    }
}
