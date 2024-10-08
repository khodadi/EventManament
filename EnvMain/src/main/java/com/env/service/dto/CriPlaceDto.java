package com.env.service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @Creator 9/25/2024
 * @Project IntelliJ IDEA
 * @Author k.khodadi
 **/

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CriPlaceDto {
    private Long page;
    private Long eventId;
    private String name;
    private String nameFa;
    private float score;
    private boolean free;
    private boolean publicPlace;
    private Double latitude;
    private Double longitude;

    public CriPlaceDto(Long page, Long eventId, String nameFa) {
        this.page = page;
        this.eventId = eventId;
        this.nameFa = nameFa;
    }

    public CriPlaceDto(Long eventId, String nameFa) {
        this(0L,eventId,nameFa);
    }
}
