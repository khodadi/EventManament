package com.env.dao.entity;

import com.env.service.dto.BaseItineraryDetailDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
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
    @Column(name = "DESTINATION_PLACE_ID")
    private Long destinationPlaceId;
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
        dto.getDestinationId(),
        dto.getStartTime(),
        dto.getEntTime(),
        dto.getTimeDistance(),
        dto.getWayDistance(),
        dto.getDescription(),
        dto.getActivityType(),
        dto.getEventId());
    }
}
