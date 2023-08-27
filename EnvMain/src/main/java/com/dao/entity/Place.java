package com.dao.entity;

import com.service.dto.PlaceDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
@Entity
@Table(name = "PLACE",schema = "ENV_DATA")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Place extends ABaseEntity{
    @Id
    @Column(name = "PLACE_ID")
    @GeneratedValue(generator = "SEQ_PLACE", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "SEQ_PLACE", allocationSize = 1, sequenceName = "SEQ_PLACE",schema = "ENV_DATA")
    private Long placeId;
    @Column(name="EVENT_ID")
    private Long eventId;
    @Column(name="NAME")
    private String name;
    @Column(name="NAME_FA")
    private String nameFa;
    @Column(name="DESCRIPTION")
    private String description;
    @Column(name="PHONE")
    private String phone;
    @Column(name="score")
    private Float score;
    @Column(name="COST")
    private Long cost;
    @Column(name="LATITUDE")
    private Double latitude;
    @Column(name="LONGITUDE")
    private Double longitude;
    @Column(name="FREE")
    private Boolean free;
    @Column(name="PUBLIC_VIEW")
    private Boolean publicView;

    public Place(PlaceDto dto){
        this(null,
                dto.getEventId(),
                dto.getName(),
                dto.getNameFa(),
                dto.getDescription(),
                dto.getPhone(),
                dto.getScore(),
                dto.getCost(),
                dto.getLatitude(),
                dto.getLongitude(),
                dto.isFree(),
                dto.isPublicPlace());
    }
}
