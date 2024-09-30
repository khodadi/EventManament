package com.env.service.dto;

import com.env.dao.entity.Place;
import com.env.utility.Utility;
import com.form.OutputAPIForm;
import com.utility.StringUtility;
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

    public PlaceDto(Place ent){
        this(ent.getPlaceId(),
                ent.getEventId(),
                ent.getName(),
                ent.getNameFa(),
                ent.getDescription(),
                ent.getPhone(),
                ent.getScore(),
                ent.getCost(),
                ent.getFree(),
                ent.getPublicView(),
                ent.getLatitude(),
                ent.getLongitude(),
                null,
                null,
                null);

    }
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

    public OutputAPIForm validateUpdatePlace(){
        OutputAPIForm retVal = Utility.checkNull(getPlaceId());
        retVal = Utility.checkNull(getName()).isSuccess() ?  StringUtility.checkString(getName(),2,20,true): retVal ;
        retVal = (retVal.isSuccess() && Utility.checkNull(getNameFa()).isSuccess()) ? StringUtility.checkString(getNameFa(),2,20,false):retVal;
        retVal = (retVal.isSuccess() && Utility.checkNull(getDescription()).isSuccess()) ? StringUtility.checkString(getDescription(),0,20,false): retVal;
        return retVal;
    }

}
