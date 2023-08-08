package com.service.dto;

import com.basedata.OccasionLengthTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BaseOccasionDto {
    private String occasionName;
    private Long occasionTypeId;
    private OccasionLengthTypeEnum OccasionLengthType;
    private Timestamp startDate;
    private Timestamp endDate;
    private Boolean sharable;
    private byte[] pic;
    private float latitude;
    private float longitude;
}
