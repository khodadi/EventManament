package com.service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ActivityTypeDto {
    private Long activityTypeId;
    private String activityTypeNameFa;
    private String activityTypeName;

    public ActivityTypeDto(Long activityTypeId) {
        this.activityTypeId = activityTypeId;
    }
}
