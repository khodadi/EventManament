package com.utility;

import com.basedata.CodeException;
import com.form.OutputAPIForm;
import com.utility.jalalicalendar.DateConverter;
import com.utility.jalalicalendar.JalaliCalendar;
import com.utility.jalalicalendar.JalaliDate;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @Creator 8/27/2022
 * @Project IntelliJ IDEA
 * @Author k.khodadi
 **/


public final class DateUtils {
    private static final SimpleDateFormat timeFormat;
    private static final SimpleDateFormat jsonDateFormat;
    private static final SimpleDateFormat gregorianDateFormat;
    private static final SimpleDateFormat gregorianDateTimeFormat;
    private static final String jalaliDatePlaceHolder;
    private static final String jalaliDateTimePlaceHolder;

    static {
        timeFormat = new SimpleDateFormat("HH:mm:ss");
        jsonDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
        gregorianDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        gregorianDateTimeFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        jalaliDatePlaceHolder = "%04d/%02d/%02d";
        jalaliDateTimePlaceHolder = "%04d/%02d/%02d %02d:%02d:%02d";
    }

    public static Timestamp getCurrentDefaultDate(){
//        Calendar cl = Calendar.getInstance();
//        return new Timestamp(cl.getTimeInMillis());
        return new Timestamp(System.currentTimeMillis());
    }

    public static Timestamp getCurrentDate(){
        Calendar cl = Calendar.getInstance();
//        cl.set(Calendar.YEAR,2016);
//        cl.set(Calendar.MONTH,2);
//        cl.set(Calendar.DAY_OF_MONTH,22);
//        return new Timestamp(cl.getTimeInMillis());
        return new Timestamp(System.currentTimeMillis());
    }

    public static Timestamp getStartDateTest(){
        Calendar cl = Calendar.getInstance();
        cl.set(Calendar.YEAR,2016);
        cl.set(Calendar.MONTH,2);
        cl.set(Calendar.DAY_OF_MONTH,20);
        return new Timestamp(cl.getTimeInMillis());
//        return new Timestamp(System.currentTimeMillis());
    }

    public static Timestamp getEndDateTest(){
        Calendar cl = Calendar.getInstance();
        cl.set(Calendar.YEAR,2016);
        cl.set(Calendar.MONTH,2);
        cl.set(Calendar.DAY_OF_MONTH,20);
        return new Timestamp(cl.getTimeInMillis());
//        return new Timestamp(System.currentTimeMillis());
    }

    public static Integer getYearDate(Timestamp input){
        Integer retVal = 0;
        try{
            Calendar cl = Calendar.getInstance();
            cl.setTimeInMillis(input.getTime());
            retVal = cl.get(Calendar.YEAR);

        }catch (Exception e){
            retVal = 0;
        }
        return retVal;
    }

    public static Integer getQuartersDate(Timestamp input){
        Integer retVal = 1;
        int  mon = Integer.parseInt(getMonthDate(input));
        switch (mon) {
            case 4:
            case 5:
            case 6:
                retVal = 2;
                break;
            case 7:
            case 8:
            case 9:
                retVal = 3;
                break;
            case 10:
            case 11:
            case 12:
                retVal = 4;
                break;

        }
        return retVal;
    }

    public static String getMonthDate(Timestamp input){
        String retVal = "01";
        Integer mon;
        try{
            Calendar cl = Calendar.getInstance();
            cl.setTimeInMillis(input.getTime());
            mon = cl.get(Calendar.MONTH)+1;
            if(mon <= 9){
                retVal = "0"+ mon;
            }else{
                retVal = mon.toString();
            }

        }catch (Exception e){
            retVal = "01";
        }
        return retVal;
    }



    public static String getDayDate(Timestamp input){
        String retVal = "01";
        Integer day;
        try{
            Calendar cl = Calendar.getInstance();
            cl.setTimeInMillis(input.getTime());
            day = cl.get(Calendar.DAY_OF_MONTH);
            if(day <= 9){
                retVal = "0"+ day;
            }else{
                retVal = day.toString();
            }

        }catch (Exception e){
            retVal = "01";
        }
        return retVal;
    }
    public static String getHourDate(Timestamp input){
        String retVal = "01";
        Integer hour;
        try{
            Calendar cl = Calendar.getInstance();
            cl.setTimeInMillis(input.getTime());
            hour = cl.get(Calendar.HOUR_OF_DAY);
            if(hour <= 9){
                retVal = "0"+ hour;
            }else{
                retVal = hour.toString();
            }

        }catch (Exception e){
            retVal = "01";
        }
        return retVal;
    }
    public static String getMinuteDate(Timestamp input){
        String retVal = "01";
        Integer minute;
        try{
            Calendar cl = Calendar.getInstance();
            cl.setTimeInMillis(input.getTime());
            minute = cl.get(Calendar.MINUTE);
            if(minute <= 9){
                retVal = "0"+ minute;
            }else{
                retVal = minute.toString();
            }

        }catch (Exception e){
            retVal = "01";
        }
        return retVal;
    }

    public static String getSecondDate(Timestamp input){
        String retVal = "01";
        Integer second;
        try{
            Calendar cl = Calendar.getInstance();
            cl.setTimeInMillis(input.getTime());
            second = cl.get(Calendar.SECOND);
            if(second <= 9){
                retVal = "0"+ second;
            }else{
                retVal = second.toString();
            }

        }catch (Exception e){
            retVal = "01";
        }
        return retVal;
    }
    public static String getMiliSecontDate(Timestamp input){
        String retVal = "01";
        Integer mili;
        try{
            Calendar cl = Calendar.getInstance();
            cl.setTimeInMillis(input.getTime());
            mili = cl.get(Calendar.MILLISECOND);
            if(mili <= 9){
                retVal = "00000"+ mili;
            }else if(mili <= 99){
                retVal = "0000"+ mili;
            }else if(mili <= 999){
                retVal = "000"+ mili;
            }else if(mili <= 9999){
                retVal = "00"+mili.toString();
            }else if(mili <= 99999){
                retVal = "0"+mili.toString();
            }else{
                retVal = mili.toString();
            }

        }catch (Exception e){
            retVal = "000001";
        }
        return retVal;
    }

    public static Timestamp getRandomDateBefor(){
        Random r = new Random();
        Calendar cl = Calendar.getInstance();
        cl.add(Calendar.DAY_OF_YEAR,-r.nextInt(5));
        return new Timestamp(cl.getTimeInMillis());
    }

    public static Timestamp getRandomDateAfter(){
        Random r = new Random();
        r.nextInt();
        Calendar cl = Calendar.getInstance();
        cl.add(Calendar.DAY_OF_YEAR,r.nextInt(5));
        return new Timestamp(cl.getTimeInMillis());
    }

    public static long diffTwoDate(Timestamp firstDate,Timestamp secondDate){
        long retVal = 0;
        long distance = setDate(firstDate).getTime()-setDate(secondDate).getTime();
        retVal = distance / (1000*60*60*24);
        return retVal;
    }

    public static long diffTwoDateDay(Timestamp firstDate,Timestamp secondDate){
        long diffInMillies = Math.abs(secondDate.getTime() - firstDate.getTime());
        return TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
    }

    public static long diffTwoTimestamp(Timestamp firstDate,Timestamp secondDate){
        long retVal = 0;
        long distance = firstDate.getTime()-secondDate.getTime();
        retVal = distance ;
        return retVal;
    }

    public static boolean datesEqual(Date dateOne, Date dateTwo) {
        Calendar calendar = Calendar.getInstance(Locale.ENGLISH);
        calendar.setTime(dateOne);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        dateOne = calendar.getTime();

        calendar.setTime(dateTwo);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        dateTwo = calendar.getTime();

        return dateOne.equals(dateTwo);
    }

    public static Timestamp setDate(Timestamp date){

        Calendar cl = Calendar.getInstance(Locale.ENGLISH);
        cl.setTimeInMillis(date.getTime());
        cl.set(Calendar.HOUR_OF_DAY, 0);
        cl.set(Calendar.SECOND, 0);
        cl.set(Calendar.MILLISECOND, 0);
        Timestamp retVal = new Timestamp(cl.getTimeInMillis());
        return retVal;

    }

    public static StringBuffer queryFromTimestamp(String entityName, String fieldName, Timestamp[] dateAndTimes, Map<String,Object> para){
        StringBuffer retVal = new StringBuffer();
        if(dateAndTimes == null || dateAndTimes.length == 0 || dateAndTimes.length > 2){
            retVal = new StringBuffer();
        }else if(dateAndTimes.length == 1){
            retVal.append(" AND ").append(entityName).append(".").append(fieldName).append(" = ").append(":"+fieldName);
            para.put(fieldName,dateAndTimes[0]);
        }else{
            if(dateAndTimes[0] == null && dateAndTimes[1]!= null){
                retVal.append(queryFromTimestampEnd(entityName,fieldName,dateAndTimes[1],para));
            }else if(dateAndTimes[0] != null && dateAndTimes[1] == null) {
                retVal.append(queryFromTimestampStart(entityName,fieldName,dateAndTimes[0],para));
            }else if(dateAndTimes[0] == null && dateAndTimes[1] == null){
                retVal = new StringBuffer();
            }else if(dateAndTimes[0] != null && dateAndTimes[1] != null){
                retVal.append(queryFromTimestamp(entityName,fieldName,dateAndTimes[0],dateAndTimes[1],para));
            }
        }
        return retVal;
    }

    public static StringBuffer queryFromTimestamp(String entityName,String fieldName,Timestamp dateAndTimeStart,Timestamp dateAndTimeEnd,Map<String,Object> para){
        StringBuffer retVal = new StringBuffer();
        if(dateAndTimeStart != null && dateAndTimeEnd != null ){
            retVal.append(" AND ").append(entityName).append(".").append(fieldName).append(" between ").append(":"+fieldName+"_S").append(" AND ").append(":"+fieldName+"_E");
            para.put(fieldName+"_S",dateAndTimeStart);
            para.put(fieldName+"_E",dateAndTimeEnd);
        }

        return retVal;
    }

    public static StringBuffer queryFromTimestampStart(String entityName,String fieldName,Timestamp dateAndTimeStart,Map<String,Object> para){
        StringBuffer retVal = new StringBuffer();
        if(dateAndTimeStart != null  ){
            retVal.append(" AND ").append(entityName).append(".").append(fieldName).append(" >= ").append(":"+fieldName);
            para.put(fieldName,dateAndTimeStart);
        }

        return retVal;
    }

    public static StringBuffer queryFromTimestampEnd(String entityName,String fieldName,Timestamp dateAndTimeEnd,Map<String,Object> para){
        StringBuffer retVal = new StringBuffer();
        if( dateAndTimeEnd != null ){
            retVal.append(" AND ").append(entityName).append(".").append(fieldName).append(" <= ").append(":"+fieldName);
            para.put(fieldName,dateAndTimeEnd);
        }

        return retVal;
    }

    public static boolean datePartEqual(Date firstDate, Date secondDate){
        return firstDate.getYear() == secondDate.getYear() &&
                firstDate.getMonth() == secondDate.getMonth() &&
                firstDate.getDate() == secondDate.getDate();
    }

    public static Timestamp jsonStringToDate(String input) {
        Date date = gregorianJsonStringToDate(input);

        if(date == null){
            return null;
        }

        return new Timestamp(date.getTime());
    }

    public static Timestamp dateStringToDate(Date date, String input, boolean isJalaliDate){
        try {
            Date convertedDate;

            if(isJalaliDate){
                convertedDate = jalaliDateTimeStringToCalendar(input).getTime();
            } else {
                convertedDate = gregorianDateFormat.parse(input);
            }

            Calendar convertedCalendar = Calendar.getInstance();
            convertedCalendar.setTime(convertedDate);

            if (date != null) {
                Calendar baseCalendar = Calendar.getInstance();
                baseCalendar.setTime(date);
                convertedCalendar.set(Calendar.HOUR_OF_DAY, baseCalendar.get(Calendar.HOUR_OF_DAY));
                convertedCalendar.set(Calendar.MINUTE, baseCalendar.get(Calendar.MINUTE));
                convertedCalendar.set(Calendar.SECOND, baseCalendar.get(Calendar.SECOND));
                convertedCalendar.set(Calendar.MILLISECOND, baseCalendar.get(Calendar.MILLISECOND));
            }

            return new Timestamp(convertedCalendar.getTimeInMillis());
        } catch (Exception e) {
            return null;
        }
    }

    public static Timestamp dateStringToDate(String input, boolean isJalaliDate){
        return dateStringToDate(null, input, isJalaliDate);
    }

    public static Timestamp dateTimeStringToDate(String input, boolean isJalaliDate){
        try {
            if(isJalaliDate){
                return new Timestamp(jalaliDateTimeStringToCalendar(input).getTimeInMillis());
            }

            return new Timestamp(gregorianDateTimeFormat.parse(input).getTime());
        } catch (Exception e) {
            return null;
        }
    }

    public static Timestamp timeStringToDate(Date date, String input){
        try{
            int hour = Integer.parseInt(input.substring(0, 2));
            int minute = Integer.parseInt(input.substring(3, 5));
            int second = Integer.parseInt(input.substring(6, 8));
            int milliSecond = 0;

            if(input.length() >= 12){
                milliSecond = Integer.parseInt(input.substring(9, 12));
            }

            Calendar calendar = Calendar.getInstance();
            if(date != null){
                calendar.setTime(date);
            }
            calendar.set(Calendar.HOUR_OF_DAY, hour);
            calendar.set(Calendar.MINUTE, minute);
            calendar.set(Calendar.SECOND, second);
            calendar.set(Calendar.MILLISECOND, milliSecond);

            return new Timestamp(calendar.getTimeInMillis());
        } catch (Exception e){
            return null;
        }
    }

    public static Timestamp timeStringToDate(String input){
        return timeStringToDate(null, input);
    }

    private static Date gregorianJsonStringToDate(String input) {
        try {
            if (input.endsWith( "Z" )) {
                input = input.substring(0, input.length() - 1);
            } else {
                input = input.substring(0, input.length() - 6);
            }

            return gregorianDateFormat.parse(input);
        } catch (ParseException e) {
            return null;
        }
    }

    private static Calendar jalaliDateTimeStringToCalendar(String input) {
        if(input == null || input.length() < 10){
            return null;
        }

        try {
            int year = Integer.parseInt(input.substring(0, 4));
            int month = Integer.parseInt(input.substring(5, 7));
            int date = Integer.parseInt(input.substring(8, 10));

            Date gregorianDate = DateConverter.jalaloToGregorian(year, month, date);

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(gregorianDate);

            int hour = 0;
            int minute = 0;
            int second = 0;
            int milliSecond = 0;

            if(input.length() >= 19){
                hour = Integer.parseInt(input.substring(11, 13));
                minute = Integer.parseInt(input.substring(14, 16));
                second = Integer.parseInt(input.substring(17, 19));
                if(input.length() >= 23){
                    milliSecond = Integer.parseInt(input.substring(20, 23));
                }
            }

            calendar.set(Calendar.HOUR_OF_DAY, hour);
            calendar.set(Calendar.MINUTE, minute);
            calendar.set(Calendar.SECOND, second);
            calendar.set(Calendar.MILLISECOND, milliSecond);

            return calendar;
        } catch (Exception e) {
            return null;
        }
    }

    public static Timestamp getStartDateCurMonth(Timestamp input){
        Timestamp retVal = null;
        if(input != null){
            JalaliDate jalaliDate = DateConverter.gregorianToJalali(input);
            jalaliDate.setDate(1);
            retVal = new Timestamp(DateConverter.jalaloToGregorian(jalaliDate).getTime());
        }
        return retVal;
    }

    public static Timestamp getStartDatePerMonth(Timestamp input){
        Timestamp retVal = null;
        if(input != null){
            JalaliDate jalaliDate = DateConverter.gregorianToJalali(input);
            jalaliDate.setDate(1);
            if(jalaliDate.getMonth() == 1){
                jalaliDate.setMonth(12);
                jalaliDate.setYear(jalaliDate.getYear() - 1);
            }else{
                jalaliDate.setMonth(jalaliDate.getMonth() -1);
            }
            retVal = new Timestamp(DateConverter.jalaloToGregorian(jalaliDate).getTime());
        }
        return retVal;
    }

    public static Calendar jalaliToCalendar(int year,int month,int date){

        try{
            Date gregorianDate = DateConverter.jalaloToGregorian(year, month, date);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(gregorianDate);
            int hour = 0;
            int minute = 0;
            int second = 0;
            int milliSecond = 0;
            calendar.set(Calendar.HOUR_OF_DAY, hour);
            calendar.set(Calendar.MINUTE, minute);
            calendar.set(Calendar.SECOND, second);
            calendar.set(Calendar.MILLISECOND, milliSecond);
            return calendar;
        } catch (Exception e) {
            return null;
        }
    }

    public static String dateToDateString(Date input, boolean toJalali){
        try {
            if(!toJalali) {
                return gregorianDateFormat.format(input);
            }

            JalaliDate jalaliDate = DateConverter.gregorianToJalali(input);

            return String.format(jalaliDatePlaceHolder, jalaliDate.getYear(), jalaliDate.getMonth(),
                    jalaliDate.getDate());
        } catch (Exception e) {
            return null;
        }
    }

    public static Timestamp minusDays(Timestamp timestamp, int days){
        return addDays(timestamp, -days);
    }

    public static Timestamp addMonth(Timestamp date,int month){
        Timestamp retVal ;
        Calendar cl = Calendar.getInstance();
        cl.setTimeInMillis(date.getTime());
        cl.add(Calendar.MONTH,month);
        retVal = new Timestamp(cl.getTimeInMillis());
        return retVal;
    }

    public static String dateToDateTimeString(Date input, boolean toJalali){
        try {
            if(!toJalali) {
                return gregorianDateTimeFormat.format(input);
            }

            JalaliDate jalaliDate = DateConverter.gregorianToJalali(input);

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(input);
            int hour = calendar.get(Calendar.HOUR_OF_DAY);
            int minute = calendar.get(Calendar.MINUTE);
            int second = calendar.get(Calendar.SECOND);

            return String.format(jalaliDateTimePlaceHolder, jalaliDate.getYear(), jalaliDate.getMonth(),
                    jalaliDate.getDate(), hour, minute, second);
        } catch (Exception e) {
            return null;
        }
    }

    public static String dateToTimeString(Date input){
        try {
            return timeFormat.format(input);
        } catch (Exception e) {
            return null;
        }
    }

    public static Timestamp getBeginOfToday(){
        return getBeginOfDay(getCurrentDate());
    }

    public static Timestamp getBeginOfDay(Timestamp timestamp){
        Calendar calendar = getBeginOfDayCalendar(timestamp.getTime());
        return new Timestamp(calendar.getTimeInMillis());
    }

    public static Date getBeginOfDay(Date date){
        Calendar calendar = getBeginOfDayCalendar(date.getTime());
        return new Date(calendar.getTimeInMillis());
    }

    public static Long convertMonth(Timestamp date){
        JalaliDate jalaliDate = DateConverter.gregorianToJalali(date);
        Integer month = jalaliDate.getMonth();
        Integer year = jalaliDate.getYear();
        Long retVal = new Long(String.format("%04d", year) + String.format("%02d", month));
        return retVal;
    }

    private static Calendar getBeginOfDayCalendar(long time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        return calendar;
    }

    public static Timestamp getEndOfToday(){
        return getEndOfDay(getCurrentDate());
    }

    public static Timestamp getEndOfDay(Timestamp timestamp){
        Calendar calendar = getEndOfDayCalendar(timestamp.getTime());
        return new Timestamp(calendar.getTimeInMillis());
    }

    public static Date getEndOfDay(Date date){
        Calendar calendar = getEndOfDayCalendar(date.getTime());
        return new Date(calendar.getTimeInMillis());
    }

    private static Calendar getEndOfDayCalendar(long time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);

        return calendar;
    }

    private static Calendar getCalendar(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(date.getTime());

        return calendar;
    }

    public static int dateToJalaliInt(Date date) {
        try {
            JalaliDate jalaliDate = DateConverter.gregorianToJalali(date);

            return 10000 * jalaliDate.getYear() + 100 * jalaliDate.getMonth() + jalaliDate.getDate();
        } catch (NumberFormatException numberException) {
            return 0;
        }
    }

    public static Date jalaliIntToDate(int dateNumber) {
        try {
            int jalaliYear = dateNumber / 10000;
            int jalaliMonthDate = dateNumber % 10000;
            int jalaliMonth = jalaliMonthDate / 100;
            int jalaliDate = jalaliMonthDate % 100;

            return DateConverter.jalaloToGregorian(jalaliYear, jalaliMonth, jalaliDate);
        } catch (NumberFormatException numberException) {
            return null;
        }
    }

    public static Date addMinutes(Date date, int minutes){
        Calendar calendar = getCalendar(date);
        calendar.add(Calendar.MINUTE, minutes);

        return new Date(calendar.getTimeInMillis());
    }

    public static Timestamp addMinutes(Timestamp timestamp, int minutes){
        Calendar calendar = getCalendar(timestamp);
        calendar.add(Calendar.MINUTE, minutes);
        return new Timestamp(calendar.getTimeInMillis());
    }

    public static Date addDays(Date date, int days){
        Calendar calendar = getCalendar(date);
        calendar.add(Calendar.DATE, days);

        return new Date(calendar.getTimeInMillis());
    }

    public static Timestamp addDays(Timestamp timestamp, int days){
        Calendar calendar = getCalendar(timestamp);
        calendar.add(Calendar.DATE, days);
        return new Timestamp(calendar.getTimeInMillis());
    }
    public static Timestamp addMons(Timestamp timestamp, int mons){
        Calendar calendar = getCalendar(timestamp);
        calendar.add(Calendar.MONTH, mons);
        return new Timestamp(calendar.getTimeInMillis());
    }

    public static Long  addMonsJalali(Timestamp timestamp, int mons){
        Long retVal = 0L;
        try{
            JalaliDate jalaliDate = DateConverter.gregorianToJalali(getBeginOfDay(timestamp));
            JalaliCalendar jc = new JalaliCalendar(jalaliDate.getYear(),jalaliDate.getMonth(),jalaliDate.getDate());
            int month = jc.get(Calendar.MONTH)+mons;
            if(month <= 1 && month <= 12){
                retVal = new Long(String.format("%04d", jc.get(Calendar.YEAR)) + String.format("%02d", jc.get(Calendar.MONTH)));
            }else if(month > 1){
                retVal = new Long(String.format("%04d", jc.get(Calendar.YEAR)-1) + String.format("%02d", jc.get(Calendar.MONTH)+12));
            }else if (month > 12){
                retVal = new Long(String.format("%04d", jc.get(Calendar.YEAR)+1) + String.format("%02d", jc.get(Calendar.MONTH)-12));
            }
        }catch (Exception e){
            e.printStackTrace();
            retVal = 140001L;
        }
        return retVal;
    }

    public static OutputAPIForm checkDate(Timestamp date, int numberDate){
        OutputAPIForm output = new OutputAPIForm();
        if(date == null || date.before(addDays(getCurrentDate(),-numberDate)) ){
            output.setSuccess(false);
        }
        return output;
    }



    public static boolean towDatesVsCurrentDate(Timestamp startDate ,Timestamp endDate ,boolean afterCurrentDate){
        boolean result = true;
        if(startDate == null || endDate == null){
            result = false;
        }
        if(result) {
            int startDateVsCuDate = startDate.compareTo(getCurrentDate());
            int endDateVsCuDate = endDate.compareTo(getCurrentDate());
            int towDate = startDate.compareTo(endDate);
            if (towDate >= 0) {
                result = false;
            } else if (afterCurrentDate && (startDateVsCuDate < 1 || endDateVsCuDate < 1)) {
                result = false;
            } else if (!afterCurrentDate && (startDateVsCuDate > 0 || endDateVsCuDate > 0)) {
                result = false;
            }
        }
        return result;
    }


//    public static boolean isDateTimeBetweenTheTwo(Timestamp startDate, Timestamp endDate, Timestamp startTime, Timestamp endTime){
//        boolean result;
//        DateTimeComparator dateTimeComparator = DateTimeComparator.getInstance();
//        Calendar calendar = Calendar.getInstance();
//        calendar.set(getYearDate(startDate),Integer.parseInt(getMonthDate(startDate)) -1,Integer.parseInt(getDayDate(startDate)), Integer.parseInt(getHourDate(startTime)), Integer.parseInt(getMonthDate(startTime)));
//        Date oneDate = calendar.getTime();
//        calendar.set(getYearDate(endDate),Integer.parseInt(getMonthDate(endDate)) -1,Integer.parseInt(getDayDate(endDate)), Integer.parseInt(getHourDate(endTime)), Integer.parseInt(getMonthDate(endTime)));
//        Date twoDate = calendar.getTime();
//        Date systemDate = new Date(getCurrentDate().getTime());
//
//        int systemVsOneDate = dateTimeComparator.compare(systemDate, oneDate);
//        int systemVsTwoDate = dateTimeComparator.compare(systemDate, twoDate);
//        // if currentDateVsInputDate = 0 both dates are equal
//        // if currentDateVsInputDate < 0 currentDate is before InputDate
//        // if currentDateVsInputDate > 0 currentDate is after InputDate
//
//        result = systemVsOneDate >= 0 && systemVsTwoDate <= 0;
//        // if true -> current date after first date and before second date
//
//        return result;
//    }

    public static boolean validateDateAndTime(Timestamp startDate, Timestamp endDate) {
        Calendar calendarStart = Calendar.getInstance();
        calendarStart.setTimeInMillis(startDate.getTime());
        Calendar calendarEnd = Calendar.getInstance();
        calendarEnd.setTimeInMillis(endDate.getTime());
        startDate = addMinutes(getBeginOfDay(startDate),calendarStart.get(Calendar.HOUR_OF_DAY) * 60 + calendarStart.get(Calendar.MINUTE));
        endDate = addMinutes(getBeginOfDay(endDate),calendarEnd.get(Calendar.HOUR_OF_DAY) * 60 + calendarEnd.get(Calendar.MINUTE));
        return datesCompareWithCurrentDate(startDate,endDate,true);
    }

    public static boolean datesCompareWithCurrentDate(Timestamp startDate , Timestamp endDate , boolean afterCurrentDate){
        boolean result = true;
        if(startDate == null || endDate == null){
            result = false;
        } else {
            int startDateVsCurrentDate = startDate.compareTo(new Timestamp(System.currentTimeMillis()));
            int endDateVsCurrentDate = endDate.compareTo(new Timestamp(System.currentTimeMillis()));
            if (startDate.compareTo(endDate) >= 0 || startDateVsCurrentDate > 0 || endDateVsCurrentDate < 0) {
                result = false;
            }
        }
        return result;
    }
}
