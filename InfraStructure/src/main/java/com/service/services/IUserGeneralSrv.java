package com.service.services;

import com.form.OutputAPIForm;
import org.springframework.stereotype.Service;

/**
 * @Creator 8/9/2023
 * @Project IntelliJ IDEA
 * @Author k.khodadi
 **/

@Service
public interface IUserGeneralSrv {
    <T> OutputAPIForm<T> getUserToken(String token);
}
