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
}
