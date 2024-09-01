package com.env.basedata;

import com.fasterxml.jackson.annotation.JsonValue;
import com.service.dto.BaseData;
import com.service.dto.KeyValue;
import com.service.services.IMessageBundleSrv;
import com.utility.GeneralUtility;
import java.util.ArrayList;

public enum StateRequest {

    Accepted(0),Requested(1),Rejected(2);

    public static final String REQUEST_STATE = "RequestState";

    StateRequest(int stateRequestCode){
        this.setStateRequestCode(stateRequestCode);
    }

    public int stateRequestCode;

    public void setStateRequestCode(int stateRequestCode) {
        this.stateRequestCode = stateRequestCode;
    }

    @JsonValue
    public int getStateRequestCode() {
        return stateRequestCode;
    }

    public static BaseData getLovStatusRequest(){
        BaseData retVal = new BaseData(REQUEST_STATE,new ArrayList<>());
        IMessageBundleSrv messageBundleSrv = GeneralUtility.getMessageSrv();
        for(StateRequest stateRequest:StateRequest.values()){
            try{
                retVal.getLov().add(new KeyValue( stateRequest.getStateRequestCode(),
                                messageBundleSrv!=null?messageBundleSrv.getMessage((REQUEST_STATE+"."+stateRequest).toLowerCase()):stateRequest.toString()
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
        switch (getStateRequestCode()){
            case 0:
                retVal = "Accepted";
                break;
            case 1:
                retVal = "Requested";
                break;
            case 2:
                retVal = "Rejected";
                break;
        }
        return retVal;
    }

}