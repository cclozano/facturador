package com.aurora.reports;

import com.vaadin.server.StreamResource;

import java.util.List;
import java.util.Map;

public class MyStreamResource extends StreamResource
{

    private ReporteResource reporteResource;


    public MyStreamResource(String filename, Map datosReporte, String pathReport, List datos) {
        super(new ReporteResource(datosReporte,pathReport,datos) , filename);
        this.reporteResource =(ReporteResource)getStreamSource();
    }

    public ReporteResource getReporteResource() {
        return reporteResource;
    }
}