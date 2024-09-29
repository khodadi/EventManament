package com.env.service.dto;

import com.env.dao.entity.Occasion;
import com.env.utility.Utility;
import com.form.OutputAPIForm;
import com.utility.StringUtility;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.util.StringUtils;

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
    private ArrayList<ComponentEventGeneralDto> tabs;
    private boolean editable;

    public OccasionDto(Occasion ent,ArrayList<ComponentEventGeneralDto> tabs,boolean sendPic){
        super(ent.getOccasionName(),
                ent.getOccasionTypeId(),
                ent.getOccasionLengthType(),
                ent.getStartDate(),
                ent.getEndDate(),
                ent.getSharable(),
                sendPic?ent.getPic().getPic():null,
                ent.getSourceId(),
                new PlaceDto(ent.getPlace()));
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
        ent.setOccasionName(StringUtils.hasLength(this.getOccasionName())?ent.getOccasionName():this.getOccasionName());
        ent.setOccasionLengthType(Objects.isNull(this.getOccasionLengthType())?ent.getOccasionLengthType():this.getOccasionLengthType());
        ent.setPicId(Objects.isNull(this.getPicId())?ent.getPicId():this.getPicId());
        ent.setStartDate(Objects.isNull(this.getStartDate())?ent.getStartDate():this.getStartDate());
        ent.setEndDate(Objects.isNull(this.getEndDate())?ent.getEndDate():this.getEndDate());
        ent.setSharable(Objects.isNull(this.getSharable())?ent.getSharable():this.getSharable());
        ent.setSourceId(Objects.isNull(this.getSourceId()) ? ent.getSourceId():this.getSourceId());
    }

    public OutputAPIForm checkMandatoryForUpdate(){
        OutputAPIForm retVal = Utility.checkNull(getOccasionId());
        return retVal;
    }
}
