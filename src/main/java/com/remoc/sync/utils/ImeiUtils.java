package com.remoc.sync.utils;

public class ImeiUtils {

    public static String getUrl(String imei) {
        return imei.split("@")[2];
    }

    public static String getChannelNum(String imei) {
        return imei.split("@")[3];
    }

    public static String getIp(String imei) {
        String cod = imei.split("@")[2];
        String[] split = cod.split(":");
        return split[0];
    }

    public static String getPort(String imei) {
        String cod = imei.split("@")[2];
        String[] split = cod.split(":");
        return split[1];
    }

    public static String getUsername(String imei) {
        String cod = imei.split("@")[2];
        String[] split = cod.split(":");
        return split[2];
    }

    public static String getPassword(String imei) {
        String cod = imei.split("@")[2];
        String[] split = cod.split(":");
        return split[3];
    }
}
