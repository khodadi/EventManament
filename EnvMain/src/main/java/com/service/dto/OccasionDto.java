package com.service.dto;

import com.basedata.OccasionLengthTypeEnum;
import com.dao.entity.Occasion;
import com.dao.entity.OccasionType;
import com.dao.entity.Pic;
import lombok.*;
import org.springframework.util.StringUtils;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Objects;

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
    private boolean editable;

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
        this.setEditable(false);
        this.setTabs(tabs);
    }
    public OccasionDto(Occasion ent){
        this(ent,null,false);
    }

    public void updateEnt(Occasion ent){
        ent.setOccasionTypeId(Objects.isNull(this.getOccasionTypeId())? ent.getOccasionTypeId(): this.getOccasionTypeId());
        ent.setOccasionName(StringUtils.isEmpty(this.getOccasionName())?ent.getOccasionName():this.getOccasionName());
        ent.setOccasionLengthType(Objects.isNull(this.getOccasionLengthType())?ent.getOccasionLengthType():this.getOccasionLengthType());
        ent.setPicId(Objects.isNull(this.getPicId())?ent.getPicId():this.getPicId());
        ent.setStartDate(Objects.isNull(this.getStartDate())?ent.getStartDate():this.getStartDate());
        ent.setEndDate(Objects.isNull(this.getEndDate())?ent.getEndDate():this.getEndDate());
        ent.setSharable(Objects.isNull(this.getSharable())?ent.getSharable():this.getSharable());
        ent.setLatitude(Objects.isNull(this.getLatitude())? ent.getLatitude() : this.getLatitude());
        ent.setLongitude(Objects.isNull(this.getLongitude())?ent.getLongitude():this.getLongitude());
    }
}
