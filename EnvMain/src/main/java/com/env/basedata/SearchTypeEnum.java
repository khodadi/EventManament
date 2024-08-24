package com.env.basedata;

import com.service.dto.BaseData;
import com.service.dto.KeyValue;
import com.service.services.IMessageBundleSrv;
import com.utility.GeneralUtility;

import java.util.ArrayList;

/**
 * @Creator 8/24/2024
 * @Project IntelliJ IDEA
 * @Author k.khodadi
 **/


public enum SearchTypeEnum {

    ALL(0),SELF_OCCASION(1),PUBLIC_OCCASION(2);

    public static final String SEARCH_TYPE = "searchType";

    int searchTypeCode;

    SearchTypeEnum(int searchTypeCode){
        this.searchTypeCode  = searchTypeCode;
    }

    public int getSearchTypeCode() {
        return searchTypeCode;
    }

    public static BaseData getLovSearchType(){
        BaseData retVal = new BaseData(SEARCH_TYPE,new ArrayList<>());
        IMessageBundleSrv messageBundleSrv = GeneralUtility.getMessageSrv();
        for(OccasionLengthTypeEnum occasionLength:OccasionLengthTypeEnum.values()){
            try{
                retVal.getLov().add(new KeyValue( occasionLength.getOccasionLengthTypeCode(),
                                messageBundleSrv!=null?messageBundleSrv.getMessage((SEARCH_TYPE+"."+occasionLength).toLowerCase()):occasionLength.toString()
                        )
                );
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return retVal;
    }

    @Override
    public String toString() {
        String retVal = "Undefined";
        switch (getSearchTypeCode()){
            case 0:
                retVal = "ALL";
                break;
            case 1:
                retVal = "SELF_OCCASION";
                break;
            case 2:
                retVal = "PUBLIC_OCCASION";
                break;
        }
        return retVal;
    }

}
