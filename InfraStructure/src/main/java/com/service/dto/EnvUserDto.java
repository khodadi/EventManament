package com.service.dto;

import com.basedata.UserType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@NoArgsConstructor
@AllArgsConstructor
@Slf4j
@Setter
@Getter
public class EnvUserDto extends EnvUserSaveDto{
    private Long userId;
    private UserType userType;

    public EnvUserDto( Long userId, UserType userType,String firstName, String lastName, String mobileNumber, String mail, String userName, String password) {
        super(firstName, lastName, mobileNumber, mail, userName, password);
        this.userId = userId;
        this.userType = userType;
    }
}
