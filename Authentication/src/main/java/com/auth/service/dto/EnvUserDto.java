package com.auth.service.dto;

import com.auth.basedata.UserType;
import com.auth.dao.entity.EnvUsers;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

@NoArgsConstructor
@AllArgsConstructor
@Slf4j
@Setter
@Getter
public class EnvUserDto extends EnvUserSaveDto {
    public EnvUserDto(EnvUsers ent){
        super(ent);
        this.setUserId(ent.getUserId());
        this.setUserType(ent.getUserType());
    }
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long userId;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private UserType userType;

    public EnvUserDto(String firstName, String lastName,Long userId) {
        super(firstName, lastName, null, null, null, null,null);
        this.userId = userId;
    }
}
