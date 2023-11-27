package com.auth.basedata;

public enum UserType {
    ordinary(0),admin(1),superAdmin(2);

    private int userTypeCode;

    UserType(int userTypeCode) {
        this.userTypeCode = userTypeCode;
    }

    @Override
    public String toString() {
        String retVal = "Undefine";
        switch (userTypeCode){
            case 0:
                retVal = "ordinary";
                break;
            case 1:
                retVal = "admin";
                break;
            case 2:
                retVal = "superAdmin";
                break;
        }
        return retVal;
    }
}
