package com.aurora.reports;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import java.io.ByteArrayOutputStream;
import java.util.Collection;
import java.util.Map;

public class ReportUtil {

    private Map parametrosReporte;
    // Collection<?> datosReporte ;
    private String pathReporte;

    private ReportGenerator reportGenerator=new ReportGenerator();

    public ReportUtil(Map parametrosReporte, String pathReporte)
    {
        this.parametrosReporte = parametrosReporte;
        //this.datosReporte =datosReporte;
        this.pathReporte = pathReporte;
        // this.datosReporte = datosReporte;

    }

    public byte[] getFile( Collection<?> datosReporte) throws JRException {
        ByteArrayOutputStream pdfBuffer = new ByteArrayOutputStream();
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(datosReporte);
        reportGenerator.executeReport(pathReporte, pdfBuffer,dataSource, parametrosReporte ,ReportGenerator.Formato.PDF);
        byte[] pdf = pdfBuffer.toByteArray();
        return pdf;
    }
}
