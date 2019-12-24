package com.aurora.pos.sri.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by carlos on 7/10/16.
 */
public class DateUtil {

    public static Date removeTime(Date date) {
        DateFormat format = new SimpleDateFormat("dd.MM.yyyy");
        Date trimmed = null;
        try {
            trimmed = format.parse(format.format(date));
        } catch (ParseException e) {}
        return trimmed;
    }

    public static Date removerHora(Date fecha)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fecha);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.HOUR, 0);
        Date fi = calendar.getTime();
        return fi;
    }

    public static Date setUltimaHora(Date fecha)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fecha);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.HOUR, 23);
        Date ff = calendar.getTime();
        return ff;
    }

    public static String dateWithFormat(Date date, String formato)
    {
        return new SimpleDateFormat(formato).format(date);
    }
}
