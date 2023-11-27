package com.env.service.dto;

import com.env.dao.entity.ItineraryDetailEquipment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ItineraryDetailEquipmentDto {
    private Long itineraryDetailEquipmentId;
    private Long equipmentId;
    private Long itineraryDetailId;

    public ItineraryDetailEquipmentDto(ItineraryDetailEquipment ent) {
        this(ent.getItineraryDetailEquipmentId(),
             ent.getEquipmentId(),
             ent.getItineraryDetailId());
    }
}
