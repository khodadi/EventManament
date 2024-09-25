package com.env.dao.entity;

import com.env.service.dto.PlaceDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

import java.util.Objects;

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

    public void update(PlaceDto dto){
        this.setEventId(Objects.nonNull(dto.getEventId()) ? dto.getEventId() :this.getEventId());
        this.setName(StringUtils.hasLength(dto.getName()) ? dto.getName(): this.getName());
        this.setNameFa(StringUtils.hasLength(dto.getNameFa()) ? dto.getNameFa(): this.getNameFa());
        this.setDescription(StringUtils.hasLength(dto.getDescription()) ? dto.getDescription(): this.getDescription());
        this.setPhone(StringUtils.hasLength(dto.getPhone()) ? dto.getPhone(): this.getPhone());
        this.setScore(Objects.nonNull(dto.getScore()) ? dto.getScore() :this.getScore());
        this.setCost(Objects.nonNull(dto.getCost()) ? dto.getCost() :this.getCost());
        this.setLatitude(Objects.nonNull(dto.getLatitude()) ? dto.getLatitude() :this.getLatitude());
        this.setLongitude(Objects.nonNull(dto.getLongitude()) ? dto.getLongitude() :this.getLongitude());
        this.setFree(Objects.nonNull(dto.isFree()) ? dto.isFree() :this.getFree());
        this.setPublicView(Objects.nonNull(dto.isPublicPlace()) ? dto.isPublicPlace() :this.getPublicView());
    }
}
