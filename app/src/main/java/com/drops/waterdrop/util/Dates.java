package com.drops.waterdrop.util;

import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * CREATE BY ALUN
 * EMAIL: alunfeixue2011@gmail.com
 * DATE : 2017/08/31 18:03
 */

public class Dates {

    public static Dates get(){
        return gDefault.get();
    }

    public static final String FORMAT_1 = "yyyyMMddhhmmss";
    public static final String FORMAT_2 = "yyyy/MM/dd/ hh:mm:ss";
    public static final String FORMAT_3 = "yyyy-MM-dd hh:mm:ss";
    public static final String FORMAT_4 = "yyyy-MM-dd";
    public static final String FORMAT_5 = "yyyy/MM/dd";

    @StringDef({FORMAT_1, FORMAT_2, FORMAT_3, FORMAT_4, FORMAT_5})
    @Retention(RetentionPolicy.SOURCE)
    public @interface DateFormat{}

    public String formatMillis(long millis, @DateFormat String format){
        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.CHINA);
        calendar.setTimeInMillis(millis);
        return sdf.format(calendar.getTime());
    }


    private Calendar calendar;
    private Dates(){
        calendar = Calendar.getInstance(Locale.CHINA);
    }

    private static final Singleton<Dates> gDefault = new Singleton<Dates>() {
        @Override
        protected Dates create() {
            return new Dates();
        }
    };
}
