package com.parkit.parkingsystem.util;

public class RoundUtil {

    private RoundUtil() {
    }

    public static double threeDigitRender(double number) {
        return Math.round(number * 1000.0) / 1000.0;
    }
}
