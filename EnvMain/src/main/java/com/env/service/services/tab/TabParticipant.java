package com.env.service.services.tab;

import com.env.dao.entity.Occasion;
import com.env.dao.entity.OccasionComponent;
import com.env.dao.entity.OccasionUsers;
import com.env.service.dto.ComponentEventGeneralDto;
import com.env.service.dto.OccasionUsersDto;
import com.utility.GeneralUtility;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

/**
 * @Creator 9/1/2024
 * @Project IntelliJ IDEA
 * @Author k.khodadi
 **/

@Service
@Slf4j
public class TabParticipant implements ITabSrv {
    @Override
    public ComponentEventGeneralDto createTab(Occasion event, OccasionComponent occasionComponent) {
//        ArrayList<OccasionUsersDto> occasionUsersDtos = new ArrayList<>();
//        for(OccasionUsers occasionUsers:event.getOccasionUsers()){
//            occasionUsersDtos.add(new OccasionUsersDto(occasionUsers.getOccasionUserId(),
//                    occasionUsers.getUserId(),
//                    occasionUsers.getOccasionId(),
//                    occasionUsers.getStateRequest()));
//        }
        ComponentEventGeneralDto tab = new ComponentEventGeneralDto(occasionComponent.getComponent().getComponentName(),
                GeneralUtility.getMessageSrv()!=null?GeneralUtility.getMessageSrv().getMessage(("table.component"+"."+occasionComponent.getComponent().getComponentName()).toLowerCase()):occasionComponent.getComponent().getComponentNameFa(),
                occasionComponent.getOrder(),null);
        return tab;
    }
}
