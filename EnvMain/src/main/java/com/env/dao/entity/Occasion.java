package com.env.dao.entity;


import com.env.basedata.OccasionLengthTypeEnum;
import com.env.service.dto.BaseOccasionDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.Set;

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
    @OneToOne
    @JoinColumn(name = "OCCASION_TYPE_ID",insertable = false ,updatable=false)
    private OccasionType occasionType;
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
    @OneToOne
    @JoinColumn(name = "pic_Id",insertable = false ,updatable=false)
    private Pic pic;
    @Column(name = "LATITUDE")
    private float latitude;
    @Column(name = "LONGITUDE")
    private float longitude;
    @OneToMany(mappedBy = "occasionId",targetEntity = OccasionUsers.class)
    private Set<OccasionUsers> occasionUsers;
    @OneToMany(mappedBy = "occasionId",targetEntity = Itinerary.class)
    private Set<Itinerary> itineraries;
    @OneToMany(mappedBy = "occasionId",targetEntity = OccasionCost.class,fetch = FetchType.LAZY)
    private Set<OccasionCost> occasionCosts;
    @Column(name = "SOURCE_ID")
    private Long sourceId;
    @OneToOne
    @JoinColumn(name = "SOURCE_ID",insertable = false ,updatable=false)
    private Place place;


    public Occasion(BaseOccasionDto dto,Long picId){
        this(null,
                dto.getOccasionTypeId(),
                null,
                dto.getOccasionName(),
                dto.getOccasionLengthType(),
                dto.getStartDate(),
                dto.getEndDate(),
                dto.getSharable(),
                picId,
                null,
                dto.getLatitude(),
                dto.getLongitude(),
                null,
                null,
                null,
                dto.getSourceId(),
                null);
    }
}
