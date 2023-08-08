package com.dao.entity;


import com.basedata.OccasionLengthTypeEnum;
import com.service.dto.BaseOccasionDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.naming.Name;
import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "Occasion",schema = "ENV_DATA")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Occasion extends ABaseEntity{

    @Id
    @Column(name = "OCCASION_ID")
    @GeneratedValue(generator = "SEQ_OCCASION", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "SEQ_OCCASION", allocationSize = 1, sequenceName = "SEQ_OCCASION",schema = "ENV_DATA")
    private Long occasionId;
    @Column(name = "OCCASION_TYPE_ID")
    private Long occasionTypeId;
    @Column(name = "OCCASION_NAME")
    private String occasionName;
    @Column(name = "OCCASION_LENGTH_TYPE")
    private OccasionLengthTypeEnum OccasionLengthType;
    @Column(name = "START_DATE")
    private Timestamp startDate;
    @Column(name = "END_DATE")
    private Timestamp endDate;
    @Column(name = "SHARABLE")
    private Boolean sharable;
    @Column(name = "PIC_ID")
    private Long picId;
    @Column(name = "LATITUDE")
    private float latitude;
    @Column(name = "LONGITUDE")
    private float longitude;


    public Occasion(BaseOccasionDto dto,Long picId){
        this(null,
                dto.getOccasionTypeId(),
                dto.getOccasionName(),
                dto.getOccasionLengthType(),
                dto.getStartDate(),
                dto.getEndDate(),
                dto.getSharable(),
                picId,
                dto.getLatitude(),
                dto.getLongitude());
    }
}
