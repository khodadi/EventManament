package com.service.dto;

import com.dao.entity.EnvUsers;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

@NoArgsConstructor
@AllArgsConstructor
@Slf4j
@Builder
@Setter
@Getter
public class EnvUserSaveDto {

    public EnvUserSaveDto(EnvUsers ent){
        this.setFirstName(ent.getFirstName());
        this.setLastName(ent.getLastName());
        this.setUserName(ent.getUserName());
    }
    private String firstName;
    private String lastName;
    private String userName;
    private String password;
}
