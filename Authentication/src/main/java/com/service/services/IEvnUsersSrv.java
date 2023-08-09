package com.service.services;

import com.api.form.OutputAPIForm;
import com.service.dto.EnvUserDto;
import com.service.dto.EnvUserSaveDto;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface IEvnUsersSrv {
    OutputAPIForm<EnvUserDto> insertUser(EnvUserSaveDto userDto);
    OutputAPIForm<UserDetails> loadUserByToken(String token) throws UsernameNotFoundException;
}
