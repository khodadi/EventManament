package com.dao.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Set;

@Entity
@Table(name = "ITINERARY",schema = "ENV_DATA")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Itinerary extends ABaseEntity{
    @Id
    @Column(name = "itinerary_Id")
    @GeneratedValue(generator = "SEQ_ITINERARY", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "SEQ_ITINERARY", allocationSize = 1, sequenceName = "SEQ_ITINERARY",schema = "ENV_DATA")
    private Long itineraryId;
    @Column(name = "OCCASION_ID")
    private Long occasionId;
    @Column(name = "itineraryDate")
    private Timestamp itineraryDate;
    @OneToMany(mappedBy = "itineraryId",targetEntity = ItineraryDetail.class)
    private Set<ItineraryDetail> itineraryDetails;

    public Itinerary(Long occasionId, Timestamp itineraryDate) {
        this.occasionId = occasionId;
        this.itineraryDate = itineraryDate;
    }
}
