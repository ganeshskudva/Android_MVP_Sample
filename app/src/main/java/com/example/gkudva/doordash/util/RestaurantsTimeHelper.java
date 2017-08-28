package com.example.gkudva.doordash.util;

import android.util.Log;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static java.lang.Math.abs;

/**
 * Created by gkudva on 26/08/17.
 */

public class RestaurantsTimeHelper {

    public static String TAG = "RestaurantsTimeHelper";
    public static String getTimeDiff(String time)
    {
        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm a");
        String temp = time.substring(time.lastIndexOf(" "));
        String inTime = temp.substring(1, temp.length() - 2);
        String isAmPm = temp.substring(temp.length() - 2);
        long diffSec, diffMs,min =0;
        Calendar calendar = Calendar.getInstance();

        try {
            Log.d(TAG, Integer.toString(calendar.get(Calendar.YEAR)));
            Log.d(TAG, Integer.toString(calendar.get(Calendar.MONTH)));
            Log.d(TAG, Integer.toString(calendar.get(Calendar.DATE)));
            Date d1 = sdf.parse(Integer.toString(calendar.get(Calendar.YEAR))+ "-" +
                                Integer.toString(calendar.get(Calendar.MONTH) + 1)+ "-" +
                                Integer.toString(calendar.get(Calendar.DATE))+ " " +
                                inTime + " " + isAmPm);
            Date d2 = new Date();

            diffMs = abs(d1.getTime() - d2.getTime());
            diffSec = diffMs / 1000;
            min = diffSec / 60;
        }
        catch (Exception ex)
        {
            Log.e(TAG, ex.getMessage());
        }

        return Long.toString(min) + " mins";

    }
}
