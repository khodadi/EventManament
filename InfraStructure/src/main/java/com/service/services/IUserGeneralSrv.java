package com.service.services;

import com.form.OutputAPIForm;

/**
 * @Creator 8/9/2023
 * @Project IntelliJ IDEA
 * @Author k.khodadi
 **/


public interface IUserGeneralSrv {
    <T> OutputAPIForm<T> getUserToken(String token);
}
