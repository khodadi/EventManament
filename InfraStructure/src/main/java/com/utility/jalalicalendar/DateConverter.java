package com.utility.jalalicalendar;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by temp on 10/7/2015.
 */
public class DateConverter {

    static final float JALALI_EPOCH = 1948320.5f;
    static final float GREGORIAN_EPOCH = 1721425.5f;

    public static Date jalaloToGregorian(int year, int month, int date) {
        float jdDate = jalaliToJd(year, month, date);
        return jdToGregorian(jdDate);
    }

    public static Date jalaloToGregorian(JalaliDate jalaliDate) {
        float jdDate = jalaliToJd(jalaliDate.getYear(), jalaliDate.getMonth(), jalaliDate.getDate());
        return jdToGregorian(jdDate);
    }

    public static JalaliDate gregorianToJalali(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        return jdToJalali(gregorianToJd(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1,
                calendar.get(Calendar.DAY_OF_MONTH)));
    }

    public static JalaliDate gregorianToJalali(int year, int month, int date) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DATE, date);

        return jdToJalali(gregorianToJd(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1,
                calendar.get(Calendar.DAY_OF_MONTH)));
    }

    public static boolean isGregorianLeapYear(int year) {
        return ((year % 4) == 0) && (!(((year % 100) == 0) && ((year % 400) != 0)));
    }

    private static float gregorianToJd(int year, int month, int date) {
        return (float)(GREGORIAN_EPOCH - 1.0f + (365 * (year - 1)) +
                Math.floor((year - 1) / 4) +
                (-1 * Math.floor((year - 1) / 100)) +
                Math.floor((year - 1) / 400) +
                Math.floor((((367 * month) - 362) / 12) +
                ((month <= 2) ? 0 : (isGregorianLeapYear(year) ? -1 : -2)) + date));
    }

    private static Date jdToGregorian(float jd) {
        float wjd, depoch, quadricent, dqc, cent, dcent, quad, dquad, yindex, yearday, leapadj;
        int year, month, date;

        wjd = (float)(Math.floor(jd - 0.5) + 0.5);
        depoch = wjd - GREGORIAN_EPOCH;
        quadricent = (float)Math.floor(depoch / 146097);
        dqc = (depoch % 146097);
        cent = (float)Math.floor(dqc / 36524);
        dcent = (dqc % 36524);
        quad = (float)Math.floor(dcent / 1461);
        dquad = (dcent % 1461);
        yindex = (float)Math.floor(dquad / 365);
        year = (int)((quadricent * 400) + (cent * 100) + (quad * 4) + yindex);
        if (!((cent == 4) || (yindex == 4))) {
            year++;
        }
        yearday = wjd - gregorianToJd(year, 1, 1);
        leapadj = ((wjd < gregorianToJd(year, 3, 1)) ? 0 : (isGregorianLeapYear(year) ? 1 : 2));
        month = (int)Math.floor((((yearday + leapadj) * 12) + 373) / 367);
        date = (int)(wjd - gregorianToJd(year, month, 1)) + 1;

        Calendar calendar = new GregorianCalendar(year, month - 1, date);
        return calendar.getTime();
    }

    private static float jalaliToJd(int year, int month, int date) {
        int epbase, epyear;

        epbase = year - ((year >= 0) ? 474 : 473);


        epyear = 474 + (epbase % 2820);

        return date +
                ((month <= 7) ? ((month - 1) * 31) : (((month - 1) * 30) + 6)) +
                (float)Math.floor(((epyear * 682) - 110) / 2816) +
                (epyear - 1) * 365 + (float)Math.floor(epbase / 2820) * 1029983 +
                (JALALI_EPOCH - 1);
    }

    private static JalaliDate jdToJalali(float jd) {
        float depoch, cycle, cyear, ycycle, aux1, aux2, yday;
        int year, month, date;

        jd = (float)Math.floor(jd) + 0.5f;

        depoch = jd - jalaliToJd(475, 1, 1);
        cycle = (float)Math.floor(depoch / 1029983);
        cyear = (depoch % 1029983);


        if (cyear == 1029982) {
            ycycle = 2820;
        } else {
            aux1 = (float)Math.floor(cyear / 366);
            aux2 = (cyear % 366);
            ycycle = (float)Math.floor(((2134 * aux1) + (2816 * aux2) + 2815) / 1028522) + aux1 + 1;
        }
        year = (int)(ycycle + (2820 * cycle) + 474);
        if (year <= 0) {
            year--;
        }
        yday = (jd - jalaliToJd(year, 1, 1)) + 1;
        month = (yday <= 186) ? (int)Math.ceil(yday / 31) : (int)Math.ceil(round(((yday - 6) / 30), 4));
        date = (int)(jd - jalaliToJd(year, month, 1)) + 1;

        return new JalaliDate(year, month, date);
    }

    private static double round(double number, int decimalPlace) {
        BigDecimal bigDecimal = new BigDecimal(Double.toString(number));
        bigDecimal = bigDecimal.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP);
        return bigDecimal.doubleValue();
    }
}
