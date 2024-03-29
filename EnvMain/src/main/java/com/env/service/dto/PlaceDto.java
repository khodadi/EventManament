package com.env.service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Objects;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PlaceDto {
    private Long placeId;
    private Long eventId;
    private String name;
    private String nameFa;
    private String description;
    private String phone;
    private float score;
    private long cost;
    private boolean free;
    private boolean publicPlace;
    private Double latitude;
    private Double longitude;
    private ArrayList<byte[]> pics;
    private ArrayList<Long> picIds;
    private ArrayList<Long> placePicIds;
    public PlaceDto(Long placeId) {
        this.placeId = placeId;
    }
    public ArrayList<byte[]> getPics() {
        if(Objects.isNull(pics)){
            pics = new ArrayList<>();
        }
        return pics;
    }
    public ArrayList<Long> getPicIds() {
        if(Objects.isNull(picIds)){
            picIds = new ArrayList<>();
        }
        return picIds;
    }
    public ArrayList<Long> getPlacePicIds() {
        if(Objects.isNull(placePicIds)){
            placePicIds = new ArrayList<>();
        }
        return placePicIds;
    }
}
