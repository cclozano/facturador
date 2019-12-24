package com.aurora.reports;

import com.vaadin.server.BrowserWindowOpener;
import com.vaadin.server.StreamResource;
import com.vaadin.ui.Button;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import java.io.InputStream;
import java.util.Collection;
import java.util.Map;

public class ExpandReport {

    private Map parametrosReporte;
   // Collection<?> datosReporte ;
    private String pathReporte;

    private ReportGenerator reportGenerator=new ReportGenerator();

    public ExpandReport(Map parametrosReporte, String pathReporte)
    {
        this.parametrosReporte = parametrosReporte;
        //this.datosReporte =datosReporte;
        this.pathReporte = pathReporte;
        // this.datosReporte = datosReporte;

    }

    public void extend(Button button, Collection<?> datosReporte,String fileName)
    {
        MyPdfResource pdfResource = new MyPdfResource(datosReporte);
        StreamResource streamResource = new StreamResource(pdfResource,fileName);

        BrowserWindowOpener opener = new BrowserWindowOpener(streamResource);
        opener.extend(button);

    }


    public class MyPdfResource implements StreamResource.StreamSource
    {
        private Collection<?> datosReporte;

        public MyPdfResource(Collection<?> datosReporte)
        {
            this.datosReporte = datosReporte;
        }


        @Override
        public InputStream getStream() {
            try {
                return new ByteArrayInputStream(getFile());
            } catch (JRException e) {
                e.printStackTrace();

            }
            return null;
        }

        public byte[] getFile() throws JRException {
            ByteArrayOutputStream pdfBuffer = new ByteArrayOutputStream();
            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(datosReporte);
            reportGenerator.executeReport(pathReporte, pdfBuffer,dataSource, parametrosReporte ,ReportGenerator.Formato.PDF);
            byte[] pdf = pdfBuffer.toByteArray();
            return pdf;
        }
    }

}
