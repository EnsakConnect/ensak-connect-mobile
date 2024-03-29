package com.ensak.connect.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class DateUtil {
    public static String calculateTimeAgo(Date newDate) {
        long nowMillis = System.currentTimeMillis(); // Current time in milliseconds
        long dateMillis = newDate.getTime(); // Date time in milliseconds
        long diffMillis = nowMillis - dateMillis; // Difference in milliseconds

        // Calculate differences in various units
        long diffMinutes = TimeUnit.MILLISECONDS.toMinutes(diffMillis);
        long diffHours = TimeUnit.MILLISECONDS.toHours(diffMillis);
        long diffDays = TimeUnit.MILLISECONDS.toDays(diffMillis);
        long diffYears = diffDays / 365;

        if (diffYears >= 1) {
            return diffYears + "y";
        } else if (diffDays >= 1) {
            return diffDays + "d";
        } else if (diffHours >= 1) {
            return diffHours + "h";
        } else if (diffMinutes >= 1) {
            return diffMinutes + "min";
        } else {
            return "Just now";
        }
//        long timestamp = newDate.getTime();
//        long currentTime = System.currentTimeMillis();
//
//        long durationMillis = currentTime - timestamp;
//        long days = TimeUnit.MILLISECONDS.toDays(durationMillis);
//        long weeks = days / 7;
//        long years = days / 365;
//
//        if (days > 0) {
//            if (days < 7) {
//                return days + "d";
//            } else if (weeks < 52) {
//                return weeks + "w";
//            } else {
//                return years + "y";
//            }
//        } else {
//            return "0d"; // If less than a day, consider it as 0 days.
//        }
    }

    public static String covertDateToTimeAgo(Date pasTime) {

        String convTime = null;
        String suffix = "ago";

        Date nowTime = new Date();

        long dateDiff = nowTime.getTime() - pasTime.getTime();

        long second = TimeUnit.MILLISECONDS.toSeconds(dateDiff);
        long minute = TimeUnit.MILLISECONDS.toMinutes(dateDiff);
        long hour = TimeUnit.MILLISECONDS.toHours(dateDiff);
        long day = TimeUnit.MILLISECONDS.toDays(dateDiff);

        if (second < 60) {
            convTime = second + "s " + suffix;
        } else if (minute < 60) {
            convTime = minute + "m " + suffix;
        } else if (hour < 24) {
            convTime = hour + "h " + suffix;
        } else if (day >= 7) {
            if (day > 360) {
                convTime = (day / 360) + "years " + suffix;
            } else if (day > 30) {
                convTime = (day / 30) + "months " + suffix;
            } else {
                convTime = (day / 7) + "weeks " + suffix;
            }
        } else if (day < 7) {
            convTime = day + "days " + suffix;
        }

        return convTime;
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

    public static String convertIsoToReadableFormat(String isoDateString) {

        SimpleDateFormat isoFormatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX", Locale.getDefault());

        SimpleDateFormat readableFormatter = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());

        try {

            Date date = isoFormatter.parse(isoDateString);

            return readableFormatter.format(date);
        } catch (ParseException e) {
            e.printStackTrace();

            return "";
        }
    }
}


