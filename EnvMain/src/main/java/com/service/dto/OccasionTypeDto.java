package com.service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;

/**
 * @Creator 8/12/2023
 * @Project IntelliJ IDEA
 * @Author k.khodadi
 **/

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OccasionTypeDto {

    private Long occasionTypeId;
    private String occasionTypeName;
    private byte[] pic;
}
