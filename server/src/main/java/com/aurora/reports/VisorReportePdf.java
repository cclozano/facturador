package com.aurora.reports;

import com.vaadin.server.BrowserWindowOpener;
import com.vaadin.server.FileResource;
import com.vaadin.server.StreamResource;
import com.vaadin.ui.*;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Collection;
import java.util.Map;

/**
 * Created by max on 24/08/17.
 */
public class VisorReportePdf extends Window {

    private Map parametrosReporte;
    Collection<?> datosReporte ;
    private String pathReporte;

    private ReportGenerator reportGenerator=new ReportGenerator();

    public VisorReportePdf(Map parametrosReporte, Collection<?> datosReporte, String pathReporte)
    {
        this.parametrosReporte = parametrosReporte;
        this.datosReporte =datosReporte;
        this.pathReporte = pathReporte;
       // this.datosReporte = datosReporte;

    }


    public byte[] getFile() throws JRException {

        ByteArrayOutputStream pdfBuffer = new ByteArrayOutputStream();
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(datosReporte);
        reportGenerator.executeReport(pathReporte, pdfBuffer,dataSource, parametrosReporte ,ReportGenerator.Formato.PDF);
        byte[] pdf = pdfBuffer.toByteArray();

        try {
            FileOutputStream fos = new FileOutputStream("/home/max/Escritorio/xxx.pdf");
            fos.write(pdf);
            fos.close();
        }
        catch (Exception ex)
        {

        }

        return pdf;
    }


    public void showReport(String width,String height)
    {
        this.setWidth(width);
        this.setHeight(height);

        StreamResource sr = new StreamResource((StreamResource.StreamSource) () -> {
            ByteArrayInputStream bais = null;
            try {
                bais = new ByteArrayInputStream(getFile());
            } catch (JRException e) {
                e.printStackTrace();
            }
            return bais;
        }, "factura.pdf");
        sr.setMIMEType("application/pdf");

        VerticalLayout v = new VerticalLayout();
        Embedded e = new Embedded("", sr);
        e.setSizeFull();
        e.setType(Embedded.TYPE_BROWSER);

        Button pdfButton = new Button("PDF");
        BrowserWindowOpener opener = new BrowserWindowOpener(sr);
        opener.extend(pdfButton);

        BrowserFrame browserFrame = new BrowserFrame("Browser",sr);

        File pdfFile = new File("//home/max/comprobates/0991393757001_logo.png");
        Embedded pdf = new Embedded(null, new FileResource(pdfFile));
        //pdf.setMimeType("application/pdf");
        pdf.setType(Embedded.TYPE_BROWSER);
        pdf.setSizeFull();



        v.addComponent(pdfButton);
        v.addComponent(pdf);
        v.setSizeFull();
        this.setContent(v);
        this.setModal(true);
        this.center();
        this.setCaption("Visor PDF");
        UI.getCurrent().addWindow(this);
    }

    public void showReport()
    {
        showReport("80%","90%");
    }



}
