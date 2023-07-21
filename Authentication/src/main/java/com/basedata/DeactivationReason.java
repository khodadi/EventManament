package com.basedata;

import com.fasterxml.jackson.annotation.JsonValue;

public enum DeactivationReason {

    MaxUnsuccessfulLoginRetriesReached(0),
    TemporarilyDeactivatedForSecurityReasons(1),
    DeactivatedByUser(2);


    private int deactivationReasonCode;


    @JsonValue
    public int getDeactivationReasonCode() {
        return deactivationReasonCode;
    }

    public void setDeactivationReasonCode(int deactivationReasonCode) {
        if(deactivationReasonCode < 0 || deactivationReasonCode > 2){
            throw new IllegalArgumentException("The given deactivation reason code is invalid.");
        }

        this.deactivationReasonCode = deactivationReasonCode;
    }


    DeactivationReason(int deactivationReasonCode){
        this.deactivationReasonCode = deactivationReasonCode;
    }
}
