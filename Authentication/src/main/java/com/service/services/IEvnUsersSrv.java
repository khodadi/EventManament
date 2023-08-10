package com.service.services;

import com.form.OutputAPIForm;
import com.security.UserSecurity;
import com.service.dto.EnvUserDto;
import com.service.dto.EnvUserSaveDto;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Map;

public interface IEvnUsersSrv {
    OutputAPIForm<EnvUserDto> insertUser(EnvUserSaveDto userDto);
    OutputAPIForm<UserDetails> loadUserByToken(String token) throws UsernameNotFoundException;
    OutputAPIForm saveToken(UserSecurity user, Map<String,String> tokens);
}
