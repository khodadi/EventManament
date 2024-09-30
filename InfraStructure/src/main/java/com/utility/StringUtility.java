package com.utility;

import com.basedata.generalcode.CodeException;
import com.form.OutputAPIForm;
import org.springframework.util.StringUtils;

public class StringUtility extends StringUtils {

    public static final String enMatcher = "[a-z A-Z]+";

    public static OutputAPIForm checkString(String str, int minLength, int maxLength, boolean enLang){
        OutputAPIForm retVal = checkString(str);
        retVal = retVal.isSuccess()?checkString(str,minLength,maxLength):retVal;
        retVal = retVal.isSuccess()?checkLangString(str,enLang):retVal;
        return retVal;
    }
    public static OutputAPIForm checkString(String str){
        OutputAPIForm retVal = new OutputAPIForm<>();
        if(!StringUtils.hasLength(str)){
            retVal.setSuccess(false);
            retVal.getErrors().add(CodeException.MANDATORY_FIELD);
        }
        return retVal;
    }

    public static OutputAPIForm checkString(String str,boolean isNull){
        OutputAPIForm retVal = new OutputAPIForm<>();
        if(!isNull){
           retVal = checkString(str);
        }
        return retVal;
    }
    public static OutputAPIForm checkString(String str,int minLength,int maxLength){
        OutputAPIForm retVal = new OutputAPIForm<>();
        String strTemp = (str == null ?"":str.trim());
        if(strTemp.length() < minLength  || strTemp.length() > maxLength){
            retVal.setSuccess(false);
            retVal.getErrors().add(CodeException.LENGTH_FIELD);
        }
        return retVal;
    }

    public static OutputAPIForm checkLangString(String str,boolean enLang){
        OutputAPIForm retVal = new OutputAPIForm<>();
        String strTemp = (str == null ?"":str.trim());
        if(enLang && !strTemp.matches(enMatcher)){
            retVal.setSuccess(false);
            retVal.getErrors().add(CodeException.INVALID_LANGUAGE);
        }
        return retVal;
    }
}
