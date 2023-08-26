package com.dao.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "ITINERARY_DETAIL_EQUIPMENT",schema = "ENV_DATA")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItineraryDetailEquipment extends ABaseEntity{

    @Id
    @Column(name = "ITINERARY_DETAIL_EQUIPMENT_ID")
    @GeneratedValue(generator = "SEQ_ITINERARY_DETAIL_EQUIPMENT", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "SEQ_ITINERARY_DETAIL_EQUIPMENT", allocationSize = 1, sequenceName = "SEQ_ITINERARY_DETAIL_EQUIPMENT",schema = "ENV_DATA")
    private Long itineraryDetailEquipmentId;
    @Column(name = "EQUIPMENT_ID")
    private Long equipmentId;
    @Column(name = "ITINERARY_DETAIL_ID")
    private Long itineraryDetailId;

}
