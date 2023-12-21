package com.ensak.connect.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
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
    public static String formatPeriod(String startDateStr, String endDateStr) {
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX", Locale.getDefault());
        SimpleDateFormat outputFormat = new SimpleDateFormat("MMM yyyy", Locale.getDefault());

        try {
            Date startDate = inputFormat.parse(startDateStr);
            Date endDate = inputFormat.parse(endDateStr);

            String formattedStartDate = outputFormat.format(startDate);
            String formattedEndDate = outputFormat.format(endDate);

            return formattedStartDate + " - " + formattedEndDate;
        } catch (ParseException e) {
            e.printStackTrace();
            return "Invalid dates";
        }
    }
}


