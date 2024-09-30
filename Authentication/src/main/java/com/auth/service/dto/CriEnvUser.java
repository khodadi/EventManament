package com.auth.service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 * @Creator 9/30/2024
 * @Project IntelliJ IDEA
 * @Author k.khodadi
 **/

@NoArgsConstructor
@AllArgsConstructor
@Slf4j
@Setter
@Getter
public class CriEnvUser {
    private Long page;
    private String lastName;
    private String mobileNumber;
    private String userName;

    public CriEnvUser(String lastName, String mobileNumber, String userName) {
        this(0L,lastName,mobileNumber,userName);
    }
}
