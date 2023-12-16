package com.basedata.generalcode;
import com.fasterxml.jackson.annotation.JsonValue;

public enum CodeException {
    SYSTEM_EXCEPTION(1),
    UNDEFINED(2),
    LENGTH_FIELD(5),
    MANDATORY_FIELD(6),
    DATA_BASE_EXCEPTION(7),
    INVALID_LANGUAGE(8),
    INVALIDATE_DATETIME(9),
    ACCESS_DENIED(10),
    BAD_USER_PASS(401),
    EXPIRED_TOKEN(401),
    INVALID_MAIL(13),
    INVALID_CELLPHONE(14),
    INVALID_USERNAME(15),
    NOT_FIND_REFERENCE(16),
    INTERNAL_ERROR(17);
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

//    @Override
//    public String toString() {
//        String retVal = "Undefine";
//        switch (codeException){
//            case 1:
//                retVal = "system_exception";
//                break;
//            case 2:
//                retVal = "UNDEFINED";
//                break;
//            case 3:
//                retVal = "invalid_mail";
//                break;
//            case 4:
//                retVal = "invalid_mail";
//                break;
//            case 5:
//                retVal = "invalid_mail";
//                break;
//            case 6:
//                retVal = "invalid_mail";
//                break;
//            case 7:
//                retVal = "invalid_mail";
//                break;
//            case 8:
//                retVal = "invalid_mail";
//                break;
//        }
//        return retVal;
//    }
}
