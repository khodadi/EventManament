package com.auth.service.dto;

import com.auth.dao.entity.EnvUsers;
import com.auth.security.filter.CustomAuthenticationFilter;
import com.utility.StringUtility;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;

import java.util.Objects;

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
        this.setDefaultLocale(ent.getDefaultLocale());
    }

    private String firstName;
    private String lastName;
    private String mobileNumber;
    private String mail;
    private String userName;
    private String password;
    private String defaultLocale;

    public String getDefaultLocale() {
        if(Objects.isNull(defaultLocale) || !StringUtility.hasLength(defaultLocale)){
            defaultLocale = CustomAuthenticationFilter.defaultLocaleConfig;
        }
        return defaultLocale;
    }
}
