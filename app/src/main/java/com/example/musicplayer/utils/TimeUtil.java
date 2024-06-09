package com.example.musicplayer.utils;

public class TimeUtil {
    public static String format(int time) {
        if (time < 10) {
            return "0" + time;
        }
        return String.valueOf(time);
    }
}
