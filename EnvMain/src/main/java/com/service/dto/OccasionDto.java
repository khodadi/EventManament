package com.service.dto;

import com.dao.entity.Occasion;
import lombok.*;

import java.sql.Timestamp;
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OccasionDto extends BaseOccasionDto{

    private Timestamp creationDate;
    private Timestamp lastUpdate;
    private Long occasionId;
    private Long picId;

    public OccasionDto(Occasion ent){
        super(ent.getOccasionName(),
                ent.getOccasionTypeId(),
                ent.getOccasionLengthType(),
                ent.getStartDate(),
                ent.getEndDate(),
                ent.getSharable(),
                null,
                ent.getLatitude(),
                ent.getLongitude());
        this.setCreationDate(ent.getCreationDate());
        this.setLastUpdate(ent.getLastUpdate());
        this.setOccasionId(ent.getOccasionId());
        this.setPicId(ent.getPicId());
    }
}
