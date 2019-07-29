package com.bignerdranch.android.haya.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TimeFormat {
    SimpleDateFormat simpleDateFormatTime;
    SimpleDateFormat simpleDateFormatDay;
    SimpleDateFormat simpleDateFormatDate;
    Calendar cal;

    public String getTimeFormat(String timestamp)
    {
        if(simpleDateFormatTime == null)
        {
            cal = Calendar.getInstance();
            simpleDateFormatTime = new SimpleDateFormat("hh:mm");
            simpleDateFormatDay  = new SimpleDateFormat("EEEE");
            simpleDateFormatDate = new SimpleDateFormat("dd/MM/yyyy" );
        }
        Date d1 = cal.getTime();
        Date d2 = new Date(Long.parseLong(timestamp));

        long dateDiff = d1.getTime() - d2.getTime();
        long numDays = dateDiff/(1000*60*60*24);

        String dateString = "";
        if(numDays >= 7)
            dateString = simpleDateFormatDate.format(new Date(Long.parseLong(timestamp)));
        else if(numDays > 0) //The message is not today.
            dateString = simpleDateFormatDay.format(new Date(Long.parseLong(timestamp)));
        else if(numDays == 0) //The message is today.
            dateString = simpleDateFormatTime.format(new Date(Long.parseLong(timestamp)));

        return dateString;
    }


}
