package com.basedata;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author M.Keyvanlou
 * @created 03/07/2023 - 12:31 PM
 **/

@Getter
@AllArgsConstructor
public enum PersianMonthsEnum {

    FARVARDIN(1),
    ORDIBEHESHT(2),
    KHORDAD(3),
    TIR(4),
    MORDAD(5),
    SHAHRIVAR(6),
    MEHR(7),
    ABAN(8),
    AZAR(9),
    DEY(10),
    BAHMAN(11),
    ESFAND(12);

    private final int persianMonthsCode;

    @Override
    public String toString() {
//        return switch (this.persianMonthsCode) {
//            case 1 -> "فروردین";
//            case 2 -> "اردیبهشت";
//            case 3 -> "خرداد";
//            case 4 -> "تیر";
//            case 5 -> "مرداد";
//            case 6 -> "شهریور";
//            case 7 -> "مهر";
//            case 8 -> "آبان";
//            case 9 -> "آذر";
//            case 10 -> "دی";
//            case 11 -> "بهمن";
//            case 12 -> "اسفند";
//            default -> "نامشخص";
//        };
        return "";
    }
}
