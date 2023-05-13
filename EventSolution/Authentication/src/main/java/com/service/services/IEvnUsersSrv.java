package com.service.services;

import com.api.form.OutputAPIForm;
import com.service.dto.EnvUserDto;

public interface IEvnUsersSrv {
    OutputAPIForm<EnvUserDto> insertUser(EnvUserDto userDto);
}
