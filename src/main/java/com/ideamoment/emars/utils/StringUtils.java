package com.ideamoment.emars.utils;

public class StringUtils {
    public static boolean isEmpty(String input) {
        return input == null || input.trim().length() == 0;
    }
}