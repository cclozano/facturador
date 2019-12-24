package framework.vaadinreports;

import com.vaadin.server.StreamResource;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Calendar;
import java.util.Collection;
import java.util.Map;

/**
 * Created by max on 18/06/17.
 */
public class ReportsUtil {

    private String baseReportsPath;

    public ReportsUtil(String baseReportsPath) {
        this.baseReportsPath = baseReportsPath;
    }

    public String getBaseResourcesPath()
    {
        return baseReportsPath;
    }

    /**
     * Get database connection, call report generation method and export's report to Vaadin's FileDownloader
     * @param reportTemplate Nombre de la plantilla JasperReports
     * @param formato Tipo de salida del archivo
     */
    public StreamResource prepareForExportReport(String reportTemplate, String reportFileName,
                                                 Formato formato, Collection dataSource, Map datosReporte){
        StreamResource myResource;
        JRBeanCollectionDataSource data = new JRBeanCollectionDataSource(dataSource);
        myResource = createResource(reportTemplate + ".jrxml",reportFileName,formato,data,datosReporte);
        return  myResource;
    }

    /**
     * Generate pdf report, and return it as a StreamResource
     * @param templatePath Report template path
     * @param reportFileName Pdf output file name
     * @return StreamResource with the generated pdf report
     */
    private StreamResource createResource(final String templatePath,
                                          String reportFileName,
                                          Formato formato,
                                          JRBeanCollectionDataSource dataSource,
                                          Map datosReporte) {
        String reportOutputFilename = reportFileName + ("_"+getDateAsString()+formato.value());
        return new StreamResource((StreamResource.StreamSource) () -> {
            ByteArrayOutputStream pdfBuffer = new ByteArrayOutputStream();
            ReportGenerator reportGenerator=new ReportGenerator();
            try {

                reportGenerator.executeReport(baseReportsPath+templatePath, pdfBuffer,dataSource,datosReporte,formato);
            } catch (JRException e) {
                e.printStackTrace();
            }
            // Return a stream from the buffer.
            return new ByteArrayInputStream(
                    pdfBuffer.toByteArray());
        }, reportOutputFilename);
    }


    private StreamResource createXResource(final String templatePath, Formato formato, JRBeanCollectionDataSource dataSource, Map datosReporte) {
        String reportOutputFilename = ("_"+getDateAsString()+formato.value());
        StreamResource str = new StreamResource((StreamResource.StreamSource) () -> {
            ByteArrayOutputStream Buffer = new ByteArrayOutputStream();
            ReportGenerator reportGenerator=new ReportGenerator();
            try {
                reportGenerator.executeReport(baseReportsPath+templatePath, Buffer,dataSource,datosReporte,formato);
            } catch (JRException e) {
                e.printStackTrace();
            }
            return new ByteArrayInputStream(
                    Buffer.toByteArray());
        }, reportOutputFilename);
        return str;
    }

    /**
     * Convert a date to String
     * @return String with date
     */
    private String getDateAsString(){
        return(String.valueOf(Calendar.getInstance().get(Calendar.YEAR))+"_"+
                String.valueOf(Calendar.getInstance().get(Calendar.MONTH)+1)+"_"+
                String.valueOf(Calendar.getInstance().get(Calendar.DAY_OF_MONTH))+"_"+
                String.valueOf(Calendar.getInstance().get(Calendar.HOUR_OF_DAY))+"_"+
                String.valueOf(Calendar.getInstance().get(Calendar.MINUTE))+"_"+
                String.valueOf(Calendar.getInstance().get(Calendar.SECOND)));
    }



    public Window getReportWindow(Collection dataSource,
                                  String nombreArchivoJRXML,
                                  String nombreArchivoSalida,
                                  Map parametros)
    {
        StreamResource sr = prepareForExportReport(
                nombreArchivoJRXML,
                nombreArchivoSalida,
                Formato.PDF,
                dataSource,
                parametros);

        VerticalLayout v = new VerticalLayout();
        Embedded e = new Embedded("", sr);
        e.setSizeFull();
        e.setType(Embedded.TYPE_BROWSER);
        v.addComponent(e);
        Window w = new Window();
        w.setContent(v);
        v.setHeight("100%");
        w.setWidth("80%");
        w.setHeight("90%");
        w.setModal(true);
        return w;
    }


}
