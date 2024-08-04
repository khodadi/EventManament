package com.service.services;

import com.basedata.generalcode.CodeException;
import com.basedata.UserType;
import com.form.OutputAPIForm;
import com.security.UserSecurity;
import com.service.dto.EnvUserDto;
import com.utility.CallWSRest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;

/**
 * @Creator 8/9/2023
 * @Project IntelliJ IDEA
 * @Author k.khodadi
 **/

@Service
@Slf4j
public class UserGeneralSrv implements IUserGeneralSrv{

    public OutputAPIForm<UserSecurity> getUserToken(String token){

        OutputAPIForm<UserSecurity> retVal = new OutputAPIForm<>();
        OutputAPIForm result = new OutputAPIForm<>();
        try{
            String url = "http://localhost:8097/authentication/api/v1/token/user?token="+token;
            result =  CallWSRest.post(url,"admin:123456",null,OutputAPIForm.class);
            retVal = convertResult(result);
        }catch (Exception e){
            retVal.setSuccess(false);
            retVal.getErrors().add(CodeException.UNDEFINED);
        }
        return retVal;
    }

    public OutputAPIForm<UserSecurity> convertResult(OutputAPIForm<LinkedHashMap> result){
        OutputAPIForm<UserSecurity> retVal = new OutputAPIForm<>();
        ((LinkedHashMap)result.getData().get("envUser")).get("firstName");
        EnvUserDto envUser = new EnvUserDto(
                new Long((((LinkedHashMap)result.getData().get("envUser")).get("userId").toString())),
                UserType.valueOf(((LinkedHashMap)result.getData().get("envUser")).get("userType") == null ?"ordinary":((LinkedHashMap)result.getData().get("envUser")).get("userType").toString()) ,
                ((LinkedHashMap)result.getData().get("envUser")).get("firstName")== null ?"":((LinkedHashMap)result.getData().get("envUser")).get("firstName").toString(),
                ((LinkedHashMap)result.getData().get("envUser")).get("lastName") == null ?"":((LinkedHashMap)result.getData().get("envUser")).get("lastName").toString(),
                ((LinkedHashMap)result.getData().get("envUser")).get("mobileNumber")== null ?"":((LinkedHashMap)result.getData().get("envUser")).get("mobileNumber").toString(),
                ((LinkedHashMap)result.getData().get("envUser")).get("mail") == null ?"":((LinkedHashMap)result.getData().get("envUser")).get("mail").toString(),
                ((LinkedHashMap)result.getData().get("envUser")).get("userName") == null ?"":((LinkedHashMap)result.getData().get("envUser")).get("userName").toString(),
                "",
                ((LinkedHashMap)result.getData().get("envUser")).get("defaultLocale") == null ?"":((LinkedHashMap)result.getData().get("envUser")).get("defaultLocale").toString()
        );
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        for(LinkedHashMap m:(ArrayList<LinkedHashMap>)result.getData().get("authorities")){
            authorities.add(new SimpleGrantedAuthority(m.get("authority").toString()));
        }
        UserSecurity userSecurity = new UserSecurity(result.getData().get("username").toString(),"",true,true,true,true,authorities, envUser);
        retVal.setSuccess(result.isSuccess());
        retVal.setMessage(result.getMessage());
        retVal.setErrors(result.getErrors());
        retVal.setData(userSecurity);
        return retVal;
    }
}
