package com.example.sell.convert;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateToStringConver {

    private static final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public static String conver_int(Date date){
        return simpleDateFormat.format(date);
    }

    public static String Yesterday(Date date){
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(calendar.DATE,-1);
        return simpleDateFormat.format(calendar.getTime());
    }

    public static String conver(Date date,String parrten){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(parrten);
        return  simpleDateFormat.format(date);
    }
}
