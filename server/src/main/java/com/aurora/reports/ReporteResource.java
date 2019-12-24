package com.aurora.reports;

import com.vaadin.server.StreamResource;
import net.sf.jasperreports.engine.JRException;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

public class ReporteResource implements StreamResource.StreamSource{

    ReportUtil reportUtil;
    List datosRepote;
    List datos;



    public ReporteResource(Map datosReporte, String pathReport, List datos)
    {
        reportUtil =
                new ReportUtil(datosReporte, pathReport);
        this.datos = datos;
    }



    @Override
    public InputStream getStream() {
        return getBais();
    }

    ByteArrayInputStream bais;

    public ByteArrayInputStream getBais() {
        if(bais == null)
        {
            bais = new ByteArrayInputStream(getPdf());
        }
        return bais;
    }


    byte[] pdf;

    public byte[] getPdf() {
        if(pdf ==null)
        {
            try {
                pdf =   reportUtil.getFile(datos);
            } catch (JRException e) {
                e.printStackTrace();
            }
        }
        return pdf;
    }
}