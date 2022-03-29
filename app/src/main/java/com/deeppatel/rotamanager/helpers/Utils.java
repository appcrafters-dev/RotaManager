package com.deeppatel.rotamanager.helpers;

import android.content.Context;
import android.widget.Toast;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {
    static Toast toast;

    public static final String DATE_FORMAT = "dd-MM-yyyy";
    public static final String MONTH_FORMAT = "MMMM, yyyy";
    public static final String DAY_FORMAT = "EEE";
    public static final String TIME_FORMAT = "h:mm a";

    public static void showToastMessage(Context context, String message) {
        if (message == null || message.isEmpty()) return;
        if (toast != null) toast.cancel();
        toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
        toast.show();
    }

    public static Boolean isValidEmail(String value) {
        Pattern pattern = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(value);
        return matcher.find();
    }

    /*******************
    * DateTime Utils
    ********************/

    public static String dateTimeToString(DateTime dateTime, String format) {
        if (dateTime == null) return null;
        return dateTime.toString(DateTimeFormat.forPattern(format));
    }

    public static String dateTimeToDateString(DateTime dateTime) {
        return dateTimeToString(dateTime, DATE_FORMAT);
    }
    public static String dateTimeToMonthString(DateTime dateTime) {
        return dateTimeToString(dateTime, MONTH_FORMAT);
    }
    public static String dateTimeToTimeString(DateTime dateTime) {
        return dateTimeToString(dateTime, TIME_FORMAT);
    }
    public static String dateTimeToDayString(DateTime dateTime) {
        return dateTimeToString(dateTime, DAY_FORMAT);
    }

    public static DateTime dateTimeFromString(String dateTime, String format) {
        return DateTime.parse(dateTime, DateTimeFormat.forPattern(format));
    }

    public static DateTime dateTimeFromDateString(String dateTime) {
        return dateTimeFromString(dateTime, DATE_FORMAT);
    }

    public static int getWeekOfMonth(DateTime dateTime){
        return (dateTime.getDayOfMonth() / 7) + 1;
    }
}
