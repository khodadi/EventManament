package com.basedata;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author M.Keyvanlou
 * @created 17/06/2023 - 4:31 PM
 **/

@Getter
@AllArgsConstructor
public enum PersianDaysEnum {

    SATURDAY(1),
    SUNDAY(2),
    MONDAY(3),
    TUESDAY(4),
    WEDNESDAY(5),
    THURSDAY(6),
    FRIDAY(7);

    private int persianDaysCode;

    public void setPersianDaysCode(int persianDaysCode) {
        this.persianDaysCode = persianDaysCode;
    }


    @Override
    public String toString() {
        String retVal = "";
        switch (this.persianDaysCode) {
            case 1 :
                retVal = "شنبه";
                break;
            case 2 :
                retVal = "یکشنبه";
                break;
            case 3 :
                retVal = "دوشنبه";
                break;
            case 4 :
                retVal = "سه شنبه";
                break;
            case 5 :
                retVal = "چهارشنبه";
                break;
            case 6 :
                retVal = "پنجشنبه";
                break;
            case 7 :
                retVal = "جمعه";
                break;
            default :
                retVal = "نامشخص";
                break;
        };
        return retVal;
    }

}
