package com.shp.basedata;

import com.service.dto.BaseData;
import com.service.dto.KeyValue;
import com.service.services.IMessageBundleSrv;
import com.utility.GeneralUtility;

import java.util.ArrayList;

public enum CartStatus {
    Reserve(0),Timeout(1),Accepted(2);
    public static final String REQUEST_STATE = "CardStatus";
    CartStatus(int cardStatusCode){
        this.setCardStatusCode(cardStatusCode);
    }

    public int cardStatusCode;

    public int getCardStatusCode() {
        return cardStatusCode;
    }

    public void setCardStatusCode(int cardStatusCode) {
        this.cardStatusCode = cardStatusCode;
    }

    public static BaseData getLovStatusRequest(){
        BaseData retVal = new BaseData(REQUEST_STATE,new ArrayList<>());
        IMessageBundleSrv messageBundleSrv = GeneralUtility.getMessageSrv();
        for(CartStatus cardStatus: CartStatus.values()){
            try{
                retVal.getLov().add(new KeyValue( cardStatus.getCardStatusCode(),
                                messageBundleSrv!=null?messageBundleSrv.getMessage((REQUEST_STATE+"."+cardStatus).toLowerCase()):cardStatus.toString()
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
        switch (getCardStatusCode()){
            case 0:
                retVal = "Reserve";
                break;
            case 1:
                retVal = "Timeout";
                break;
            case 2:
                retVal = "Accepted";
                break;
        }
        return retVal;
    }
}
