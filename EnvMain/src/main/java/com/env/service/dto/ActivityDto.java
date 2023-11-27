package com.service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ActivityDto {
    private Long activityId;
    private String nameFa;
    private String name;
    private Long picId;
    private byte[] pic;

    public ActivityDto(Long activityId) {
        this.activityId = activityId;
    }
}
