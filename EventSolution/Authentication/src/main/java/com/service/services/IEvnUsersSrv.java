package com.service.services;

import com.api.form.OutputAPIForm;
import com.service.dto.EnvUserDto;
import com.service.dto.EnvUserSaveDto;

public interface IEvnUsersSrv {
    OutputAPIForm<EnvUserDto> insertUser(EnvUserSaveDto userDto);
}
