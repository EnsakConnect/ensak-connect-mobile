package com.ensak.connect.utils;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class Utils {
    public static String calculateTimeAgo(Date newDate) {
        long timestamp = newDate.getTime();
        long currentTime = System.currentTimeMillis();

        long durationMillis = currentTime - timestamp;
        long days = TimeUnit.MILLISECONDS.toDays(durationMillis);
        long weeks = days / 7;
        long years = days / 365;

        if (days > 0) {
            if (days < 7) {
                return days + "d";
            } else if (weeks < 52) {
                return weeks + "w";
            } else {
                return years + "y";
            }
        } else {
            return "0d"; // If less than a day, consider it as 0 days.
        }
    }
}
