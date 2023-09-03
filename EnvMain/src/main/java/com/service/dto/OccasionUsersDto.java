package com.service.dto;

import com.basedata.StateRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OccasionUsersDto {
    private Long occasionUserId;
    private Long UserId;
    private Long occasionId;
    private StateRequest stateRequest;
}
