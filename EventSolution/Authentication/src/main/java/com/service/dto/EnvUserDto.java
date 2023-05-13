package com.service.dto;

import com.basedata.UserType;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

@NoArgsConstructor
@AllArgsConstructor
@Slf4j
@Builder
@Setter
@Getter
public class EnvUserDto {
    private Long userId;
    private String password;
    private String firstName;
    private String lastName;
    private String email;
    private String cellPhone;
    private UserType userType;
}
