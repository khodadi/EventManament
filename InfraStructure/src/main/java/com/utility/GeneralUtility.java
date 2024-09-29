package com.utility;

import com.service.component.ApplicationContextProvider;
import com.service.services.IMessageBundleSrv;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
public class GeneralUtility {

    public static <T> boolean checkNextPage(List<T> input, int pageSize){
        boolean retVal = false;
        List<T> deleteElement = new ArrayList<>();
        try{
            if(Objects.nonNull(input) && input.size()> pageSize){
                retVal = true;
//                for(int index = pageSize;index < input.size();index++){
//                    deleteElement.add(input.get(index));
//                }
//                input.removeAll(deleteElement);
            }
        }catch (Exception e){
            retVal = false;
        }
        return retVal;
    }

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
