package com.aurora.util;



/**
 * Created by max on 07/07/17.
 */
public class ComprobantesReportUtil{

    private ReportGenerator reportGenerator=new ReportGenerator();

    //private final static String BASE_REPORT = System.getProperty("user.dir");

    private final static String RIDE_FACTURA = "cabeceraFactura.jrxml";

    private final static String RIDE_NOTA_CREDITO ="cabeceraNotaCredito.jrxml";

    private final static String RIDE_GUIA = "cabeceraGuia.jrxml";

    private final static String RIDE_RETENCION = "cabeceraRetencion.jrxml";

   // private String pathLogo;

   // private String pathReport;

   // List<Comp> lista;

    /*private DocumentoElectronicoImp comprobante;

    private String basePathReport;

    public ComprobantesReportUtil(DocumentoElectronicoImp comprobante,String basePathReport )
    {
        this.comprobante = comprobante;
        this.basePathReport = basePathReport;
    }


    private String getPathLogo()
    {
        if(comprobante.getEmisor().getPathLogo()!=null)
        {
            return comprobante.getEmisor().getPathLogo();
        }
        return basePathReport + "files/nologo.png";

    }


    private String getPathReport()
    {
        if(comprobante.getTipoDocumento().equals("FAC"))
        {
            //this.pathReport = BASE_REPORT +  "/bus/src/main/resources/reports/" + RIDE_FACTURA;
            return basePathReport + RIDE_FACTURA;
        }
        else if(comprobante.getTipoDocumento().equals("NC"))
        {
            //this.pathReport = BASE_REPORT +  "/bus/src/main/resources/reports/" + RIDE_NOTA_CREDITO;
            return basePathReport + RIDE_NOTA_CREDITO;
        }
        else if (comprobante.getTipoDocumento().equals("GUIA"))
        {
            //this.pathReport = BASE_REPORT +  "/bus/src/main/resources/reports/" + RIDE_GUIA;
            return basePathReport + RIDE_GUIA;
        }
        else if(comprobante.getTipoDocumento().equals("RETENCION"))
        {
            //this.pathReport = BASE_REPORT +  "/bus/src/main/resources/reports/" + RIDE_RETENCION;
            return basePathReport + RIDE_RETENCION;
        }
        return null;
    }


    public byte[] getFile() throws JRException {

        ByteArrayOutputStream pdfBuffer = new ByteArrayOutputStream();
        Map datosReporte = new HashMap();
        datosReporte.put("P_TITULO", "");
        datosReporte.put("P_SUBTITULO", "");
        datosReporte.put("LOGO_URL", getPathLogo());
        datosReporte.put("PATH", this.basePathReport);


        ArrayList<ComprobanteElectronico> lista = new ArrayList<>();
        lista.add(comprobante);

        JRBeanCollectionDataSource data = new JRBeanCollectionDataSource(lista);
        reportGenerator.executeReport(this.getPathReport(), pdfBuffer,data,datosReporte,Formato.PDF);
        return pdfBuffer.toByteArray();

    }*/



}
