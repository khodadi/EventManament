package com.env.service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @Creator 9/2/2023
 * @Project IntelliJ IDEA
 * @Author k.khodadi
 **/

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PlacePicDto {

    private Long placeId;
    private String name;
    private byte[] pic;
    private Long placePicId;
    private Long picId;

}
