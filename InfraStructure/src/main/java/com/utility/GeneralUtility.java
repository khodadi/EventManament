package com.utility;

import com.service.component.ApplicationContextProvider;
import com.service.services.IMessageBundleSrv;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class GeneralUtility {

    public static Object getMessageSrv(String beanName){
        Object retVal;
        try{
           retVal = ApplicationContextProvider.getApplicationContext().getBean(beanName);
        }catch (Exception e){
            retVal = null;
        }
        return retVal;
    }
    public static IMessageBundleSrv getMessageSrv(){
        IMessageBundleSrv retVal;
        try{
            retVal = (IMessageBundleSrv) getMessageSrv("messageBundleSrv");
        }catch (Exception e){
            retVal = null;
        }
        return retVal;
    }
}
