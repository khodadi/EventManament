package com.env.service.dto;

import com.env.basedata.StateRequest;
import com.env.dao.entity.OccasionUsers;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OccasionUsersDto implements ITab {
    private Long occasionUserId;
    private Long userId;
    private Long occasionId;
    private StateRequest stateRequest;

    public OccasionUsersDto(OccasionUsers ent) {

        this.occasionUserId = ent.getOccasionUserId();
        this.userId = ent.getUserId();
        this.occasionId = ent.getOccasionId();
        this.stateRequest = ent.getStateRequest();
    }
}
