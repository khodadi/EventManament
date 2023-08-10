package com.service.services;

import com.form.OutputAPIForm;
import com.utility.CallWSRest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @Creator 8/9/2023
 * @Project IntelliJ IDEA
 * @Author k.khodadi
 **/

@Service
@Slf4j
public class UserGeneralSrv implements IUserGeneralSrv{

    public OutputAPIForm<Object> getUserToken(String token){
        OutputAPIForm<Object> retVal = new OutputAPIForm<>();
        retVal = CallWSRest.post("url","userName:password",token,OutputAPIForm.class);
        return retVal;
    }

}
