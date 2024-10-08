package com.env.service.dto;

import com.env.basedata.SearchTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CriOccasionDto {
    private int page;
    private Long occasionId;
    private String occasionName;
    private SearchTypeEnum searchType;

    public CriOccasionDto(Long occasionId) {
        this.occasionId = occasionId;
    }

    public CriOccasionDto(int page) {
        this.page = page;
    }
}
