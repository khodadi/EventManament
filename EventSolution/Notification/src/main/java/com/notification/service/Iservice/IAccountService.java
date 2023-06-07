package com.notification.service.Iservice;

import com.infra.dto.Output;
import com.infra.dto.UserBaseInfoDto;

import java.util.HashSet;

/**
 * @author m.shahrestanaki @createDate 4/30/2023
 */
public interface IAccountService {
    Output<UserBaseInfoDto> getUserInfo();
}
