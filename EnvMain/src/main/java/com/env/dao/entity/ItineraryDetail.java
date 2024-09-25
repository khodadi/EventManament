package com.env.dao.entity;

import com.env.service.dto.*;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Objects;

@Entity
@Table(name = "ITINERARY_DETAIL",schema = "ENV_DATA")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItineraryDetail extends ABaseEntity{

    @Id
    @Column(name = "ITINERARY_DETAIL_ID")
    @GeneratedValue(generator = "SEQ_ITINERARY_DETAIL", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "SEQ_ITINERARY_DETAIL", allocationSize = 1, sequenceName = "SEQ_ITINERARY_DETAIL",schema = "ENV_DATA")
    private Long itineraryDetailId;
    @Column(name = "ITINERARY_ID")
    private Long itineraryId;
    @Column(name = "SOURCE_PLACE_ID")
    private Long sourcePlaceId;
    @OneToOne
    @JoinColumn(name = "SOURCE_PLACE_ID",insertable = false ,updatable=false)
    private Place sourcePlace;
    @Column(name = "DESTINATION_PLACE_ID")
    private Long destinationPlaceId;
    @OneToOne
    @JoinColumn(name = "DESTINATION_PLACE_ID",insertable = false ,updatable=false)
    private Place destinationPlace;
    @Column(name = "START_TIME")
    private Timestamp startTime;
    @Column(name = "END_TIME")
    private Timestamp endTime;
    @Column(name = "TIME_DISTANCE")
    private Long timeDistance;
    @Column(name = "WAY_DISTANCE")
    private Long wayDistance;
    @Column(name = "DESCRIPTION")
    private String description;
    @Column(name = "ACTIVITY_ID")
    private Long activityId;
    @Column(name = "EVENT_ID")
    private Long eventId;



    public ItineraryDetail(BaseItineraryDetailDto dto){
        this(null,
        dto.getItineraryId(),
        dto.getSourceId(),
        null,
        dto.getDestinationId(),
        null,
        dto.getStartTime(),
        dto.getEntTime(),
        dto.getTimeDistance(),
        dto.getWayDistance(),
        dto.getDescription(),
        dto.getActivityTypeId(),
        dto.getEventId());
    }
    public ItineraryDetail(BaseItineraryDetailDto dto,Place srcPlace,Place desPlace){
        this(dto);
        this.setSourcePlace(srcPlace);
        this.setDestinationPlace(desPlace);
    }

    public void update(BaseItineraryDetailDto dto){
        try {
            this.setSourcePlaceId( Objects.nonNull(dto.getSourceId()) ? dto.getSourceId():this.getSourcePlaceId());
            this.setStartTime(Objects.nonNull(dto.getStartTime()) ? dto.getStartTime() : this.getStartTime());
            this.setEndTime(Objects.nonNull(dto.getEntTime()) ? dto.getEntTime(): this.getEndTime() );
            this.setTimeDistance(Objects.nonNull(dto.getTimeDistance()) ? dto.getTimeDistance(): this.getTimeDistance());
            this.setWayDistance(Objects.nonNull(dto.getWayDistance()) ? dto.getWayDistance(): this.getWayDistance());
            this.setActivityId(Objects.nonNull(dto.getActivityTypeId()) ?dto.getActivityTypeId(): this.getActivityId() );
            this.setEventId(Objects.nonNull(dto.getEventId()) ? dto.getEventId() : this.getEventId());
            this.setDescription(StringUtils.hasLength(dto.getDescription()) ?  dto.getDescription() : this.getDescription());
            this.setDestinationPlaceId(Objects.nonNull(dto.getDestinationId())  ? dto.getDestinationId() : this.getDestinationPlaceId());

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
