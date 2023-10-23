package org.leapyear;

public class LeapYearCalculator {
    public static boolean isLeapYear(int year) {
        if (year >= 1 && year <= 9999) {
            if (year % 4 == 0) {
                if (year % 100 == 0) {
                    if (year % 400 == 0) {
                        return true;
                    }
                } else {
                    return true;
                }
            }
            return false;
        } else {
            return false;
        }
    }

    public static void main(String[] args) {
        System.out.println(isLeapYear(400));
    }
}
