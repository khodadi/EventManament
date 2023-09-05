package com.service.dto;

import com.dao.entity.Occasion;
import lombok.*;

import java.sql.Timestamp;
import java.util.ArrayList;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OccasionDto extends BaseOccasionDto{

    private Timestamp creationDate;
    private Timestamp lastUpdate;
    private Long occasionId;
    private Long picId;
    private ArrayList<ComponentEventDto> tabs;
    private ArrayList<OccasionUsersDto> occasionUsersDtos;


    public OccasionDto(Occasion ent,ArrayList<ComponentEventDto> tabs,boolean sendPic){
        super(ent.getOccasionName(),
                ent.getOccasionTypeId(),
                ent.getOccasionLengthType(),
                ent.getStartDate(),
                ent.getEndDate(),
                ent.getSharable(),
                sendPic?ent.getPic().getPic():null,
                ent.getLatitude(),
                ent.getLongitude());
        this.setCreationDate(ent.getCreationDate());
        this.setLastUpdate(ent.getLastUpdate());
        this.setOccasionId(ent.getOccasionId());
        this.setPicId(ent.getPicId());
        this.setTabs(tabs);
    }
    public OccasionDto(Occasion ent){
        this(ent,null,false);
    }
}
