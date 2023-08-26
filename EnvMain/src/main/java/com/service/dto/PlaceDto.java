package com.service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PlaceDto {
    private Long placeId;
    private float latitude;
    private float longitude;
    private byte[] pic;
    private String name;
    private String description;
    private boolean free;
    private long cost;
    private boolean publicPlace;


    public PlaceDto(Long placeId) {
        this.placeId = placeId;
    }
}
