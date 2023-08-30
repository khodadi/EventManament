package com.service.services;

import com.basedata.generalcode.CodeException;
import com.form.OutputAPIForm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Locale;
import java.util.ResourceBundle;

@Service
@Slf4j
public class MessageBundleSrv implements IMessageBundleSrv{

    public String getMessage(String key){
        String retVal = getMessage(key,new Locale("fa"));
        return retVal;
    }
    public void createMsg(OutputAPIForm obj){
        try{
            createMsg(obj,new Locale("fa"));
        }catch (Exception e){
            log.error(e.getMessage());
        }
    }
    public String getMessage(String key, Locale locale){
        String retVal ="";
        try{
            retVal = ResourceBundle.getBundle("message",locale).getString(key);
        }catch (Exception e){
            log.error(e.getMessage());
        }
        return retVal;
    }
    public void createMsg(OutputAPIForm obj, Locale locale){
        try{
            if(obj.isSuccess()){
                obj.setMessage(ResourceBundle.getBundle("message_infra",locale).getString("successfulMessage"));
            }else{
               for(CodeException exp:obj.getErrors()){
                   obj.setMessage(ResourceBundle.getBundle("message_infra",locale).getString("error."+exp.toString().toLowerCase()));
                   break;
               }
            }
        }catch (Exception e){
            log.error(e.getMessage());
        }

    }

}
