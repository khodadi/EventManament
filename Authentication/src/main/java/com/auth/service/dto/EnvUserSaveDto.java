package com.auth.service.dto;

import com.auth.dao.entity.EnvUsers;
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
        this.setMobileNumber(ent.getCellPhone());
        this.setMail(ent.getEmail());
    }
    private String firstName;
    private String lastName;
    private String mobileNumber;
    private String mail;
    private String userName;
    private String password;
}