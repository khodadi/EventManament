package com.auth.service.services;

import com.auth.service.dto.CriEnvUser;
import com.auth.service.dto.EnvUserDto;
import com.auth.service.dto.EnvUserSaveDto;
import com.form.OutputAPIForm;
import com.auth.security.UserSecurity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.Map;

public interface IEvnUsersSrv {
    OutputAPIForm<EnvUserDto> insertUser(EnvUserSaveDto userDto);
    OutputAPIForm<UserDetails> loadUserByToken(String token) throws UsernameNotFoundException;
    OutputAPIForm saveToken(UserSecurity user, Map<String,String> tokens);
    UserDetailsService userDetailsService();
    OutputAPIForm<ArrayList<EnvUserDto>> listUsers(CriEnvUser cri);
}
