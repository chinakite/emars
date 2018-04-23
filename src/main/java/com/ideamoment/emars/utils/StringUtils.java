package com.ideamoment.emars.utils;

public class StringUtils {
    public static boolean isEmpty(String input) {
        return input == null || input.trim().length() == 0;
    }

    public static boolean isNotEmpty(String input) {
        return !isEmpty(input);
    }

    public static String getExtName(String fileName) {
        int pos = fileName.lastIndexOf('.');
        if(pos != fileName.length() - 1) {
            return fileName.substring(pos+1);
        }else{
            return "";
        }
    }

    public static String getOssKeyFromUrl(String url) {
        int pos = url.lastIndexOf('/');
        pos = url.lastIndexOf('/', pos-1);
        if(pos != url.length() - 1) {
            return url.substring(pos+1);
        }else{
            return "";
        }
    }


}
