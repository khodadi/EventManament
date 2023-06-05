package com.service.dto;

import com.basedata.UserType;
import com.dao.entity.EnvUsers;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.User;

@NoArgsConstructor
@AllArgsConstructor
@Slf4j
@Setter
@Getter
public class EnvUserDto extends EnvUserSaveDto{
    public EnvUserDto(EnvUsers ent){
        super(ent);
        this.setUserId(ent.getUserId());
        this.setUserType(ent.getUserType());
    }

    private Long userId;
    private UserType userType;
}
