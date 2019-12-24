package com.aurora.util;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

/**
 * Created by max on 14/07/17.
 */
public class HoyFechaUtil {

    //private Date fecha;
    LocalDate localDate;

    public HoyFechaUtil()
    {
        localDate = LocalDate.now();
    }

    public HoyFechaUtil(Date fecha)
    {
        localDate =  fecha.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }





    public  Date getFechaInico()
    {

        Date inicio = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        inicio.setHours(0);
        inicio.setMinutes(0);
        inicio.setSeconds(0);

        return inicio;
    }

    public  Date getFechaFin()
    {
        Date fin =  Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        fin.setHours(23);
        fin.setMinutes(59);
        fin.setSeconds(59);
        return fin;

    }
}
