package com.utility.jalalicalendar;

/**
 * Created by temp on 10/8/2015.
 */
public class JalaliDate {
    private int year;
    private int month;
    private int date;

    public JalaliDate(){
    }

    public JalaliDate(int year, int month, int date){
        this.year = year;
        this.month = month;
        this.date = date;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDate() {
        return date;
    }

    public void setDate(int date) {
        this.date = date;
    }

    public String toString(boolean twoDigitYear){
        if(twoDigitYear){
            return String.format("%02s/%02s/%02s", year % 100, month, date);
        } else {
            return String.format("%s/%s/%s", year, Integer.valueOf(month).toString().length() == 1 ? "0" + month : month, Integer.valueOf(date).toString().length() == 1 ? "0" + date : date);
        }
    }

    public String toString(){
        return toString(false);
    }


}
