package com.service.dto;

import lombok.*;
import lombok.extern.slf4j.Slf4j;

@NoArgsConstructor
@AllArgsConstructor
@Slf4j
@Builder
@Setter
@Getter
public class EnvUserSaveDto {
    private String firstName;
    private String lastName;
    private String mobileNumber;
    private String mail;
    private String userName;
    private String password;
}
