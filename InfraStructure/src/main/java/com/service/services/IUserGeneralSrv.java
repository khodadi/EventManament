package com.service.services;

import com.from.OutputAPIForm;

/**
 * @Creator 8/9/2023
 * @Project IntelliJ IDEA
 * @Author k.khodadi
 **/


public interface IUserGeneralSrv {
    OutputAPIForm<Object> getUserToken(String token);
}
