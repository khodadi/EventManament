package com.basedata;
import com.fasterxml.jackson.annotation.JsonValue;
import java.io.Serializable;

public enum CodeException {
    SYSTEM_EXCEPTION(1),
    UNDEFINED(2),
    INVALID_MAIL(3),
    INVALID_CELLPHONE(4),
    LENGTH_FIELD(5),
    MANDATORY_FIELD(6),
    DATA_BASE_EXCEPTION(7);



    private int codeException;
    CodeException(int code) {
        this.codeException = code;
    }

    @JsonValue
    public int getCodeException() {
        return codeException;
    }

    public void setCodeException(int codeException) {
        if(codeException < 0 || codeException > 10){
            throw new IllegalArgumentException("The given exception code is invalid.");
        }
        this.codeException = codeException;
    }
}
