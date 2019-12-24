package com.aurora.util;

import com.aurora.comprobantes.mappers.FacturaMapper;
import com.aurora.config.ParametrosServer;
import com.aurora.pos.server.reportes.dto.Factura;
import com.vaadin.server.BrowserWindowOpener;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Resource;
import com.vaadin.server.StreamResource;
import com.vaadin.ui.*;
import org.springframework.beans.factory.annotation.Autowired;

import javax.xml.bind.JAXBException;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by max on 17/06/17.
 */
public class PdfWindow extends Window {

    @Autowired
    private ParametrosServer parametrosServer;
    String  baseReportsPath;


    private FacturaMapper facturaMapper = new FacturaMapper();
    /*private NotaCreditoMapper notaCreditoMapper = new NotaCreditoMapper();
    private NotaDebitoMapper notaDebitoMapper = new NotaDebitoMapper();
    private GuiaMapper guiaMapper = new GuiaMapper();
    private RetencionMapper retencionMapper = new RetencionMapper();*/



    private Button pdfButton = new Button(FontAwesome.FILE_PDF_O);
    private Button xmlButton = new Button((FontAwesome.FILE));
    private Button zipButton = new Button(FontAwesome.FILE_ZIP_O);


    private String pathLogo;


    private PdfWindow() throws JAXBException, IOException
    {
        this.baseReportsPath = "/home/clozano/IdeaProjects/Facturacion/server/src/main/resources/static/reports/";
        //this.baseReportsPath = System.getProperty("user.dir") +  "/bus/src/main/resources/reports/";
        pdfButton.setDescription("Mostrar PDF");
        xmlButton.setDescription("Mostrar XML");
        zipButton.setDescription("Descargar zip");
        HorizontalLayout verticalLayout = new HorizontalLayout(pdfButton,xmlButton,zipButton);
        verticalLayout.setSpacing(true);
        verticalLayout.setMargin(true);

        this.setCaption("Generar");
        this.setModal(true);
        this.center();
        this.setContent(verticalLayout);
    }



    public PdfWindow(Factura factura) throws Exception {
        this();
        List<Factura> lista = new ArrayList<>();
        lista.add(factura);
        ReportsUtil rp = new ReportsUtil("/home/clozano/IdeaProjects/Facturacion/server/src/main/resources/static/reports/");
        pathLogo = rp.getBaseResourcesPath()+"/files/nologo.png";

        pdfButton.addClickListener(event -> this.mostrarReporte(lista,pathLogo,"cabeceraFactura",Formato.PDF));
        //com.aurora.pos.sri.esquemas.factura_v1.Factura facturaJaxB =  facturaMapper.map(factura);
        //XMLSerializador<Factura> serializador = new XMLSerializador<>(Factura.class);
        //ByteArrayOutputStream bos = serializador.serializar(facturaJaxB);
        StringBuilder sb = new StringBuilder();
        sb.append("FAC_");
        sb.append(factura.getIdentificacionComprador());
        sb.append("_");
        sb.append(factura.getNumeroFactura());
        sb.append(".xml");
        //setButtonXml(factura.obtenerDocumentoSri(),sb.toString());
    }




    /*public PdfWindow(NotaCreditoEntity notaCredito) throws Exception {
        this();

        List<NotaCreditoEntity> lista = new ArrayList<>();
        lista.add(notaCredito);
        ReportsUtil rp = new ReportsUtil(baseReportsPath);
        String pathLogo = rp.getBaseResourcesPath()+"/files/nologo.png";
        if(notaCredito.getEmisor().getPathLogo()!=null)
        {
            pathLogo = notaCredito.getEmisor().getPathLogo();
        }
        this.mostrarReporte(lista,pathLogo,"cabeceraNotaCredito",Formato.PDF);
        NotaCredito notaCreditoJaxb =  notaCreditoMapper.map(notaCredito);
        XMLSerializador<NotaCredito> serializador = new XMLSerializador<>(NotaCredito.class);
        ByteArrayOutputStream bos = serializador.serializar(notaCreditoJaxb);
        StringBuilder sb = new StringBuilder();
        sb.append("NC_");
        sb.append(notaCredito.getInformacionTributaria().getEstablecimento());
        sb.append(notaCredito.getInformacionTributaria().getPuntoEmision());
        sb.append("_");
        sb.append(notaCredito.getInformacionTributaria().getSecuencial());
        sb.append(".xml");
        setButtonXml(notaCredito.obtenerDocumentoSri(),sb.toString());
    }


    public PdfWindow(NotaDebitoEntity notaDebitoEntity) throws Exception {
        this();

        List<NotaDebitoEntity> lista = new ArrayList<>();
        lista.add(notaDebitoEntity);
        //this.mostrarReporte(lista,"","","cabeceraFactura", Formato.PDF,pdfButton);

        ReportsUtil rp = new ReportsUtil(baseReportsPath);
        // String pathLogo = rp.getBaseResourcesPath()+"/files/LOGO_JARDINES.png";
        pathLogo = rp.getBaseResourcesPath()+"/files/nologo.png";
        if(notaDebitoEntity.getEmisor().getPathLogo()!=null)
        {
            pathLogo = notaDebitoEntity.getEmisor().getPathLogo();
        }
        pdfButton.addClickListener(event -> this.mostrarReporte(lista,pathLogo,"cabeceraFactura",Formato.PDF));
        NotaDebito notaDebitoJaxB =  notaDebitoMapper.map(notaDebitoEntity);
        XMLSerializador<NotaDebito> serializador = new XMLSerializador<>(NotaDebito.class);
        ByteArrayOutputStream bos = serializador.serializar(notaDebitoJaxB);
        StringBuilder sb = new StringBuilder();
        sb.append("ND_");
        sb.append(notaDebitoEntity.getInformacionTributaria().getEstablecimento());
        sb.append(notaDebitoEntity.getInformacionTributaria().getPuntoEmision());
        sb.append("_");
        sb.append(notaDebitoEntity.getInformacionTributaria().getSecuencial());
        sb.append(".xml");
        setButtonXml(notaDebitoEntity.obtenerDocumentoSri(),sb.toString());
    }

    public PdfWindow(GuiaEntity guiaEntity) throws Exception {
        this();

        List<GuiaEntity> lista = new ArrayList<>();
        lista.add(guiaEntity);
        ReportsUtil rp = new ReportsUtil(baseReportsPath);
        pathLogo = rp.getBaseResourcesPath()+"/files/nologo.png";
        if(guiaEntity.getEmisor().getPathLogo()!=null)
        {
            pathLogo = guiaEntity.getEmisor().getPathLogo();
        }
        pdfButton.addClickListener(event -> this.mostrarReporte(lista,pathLogo,"cabeceraNotaCredito",Formato.PDF));
        GuiaRemision guiaJaxb =  guiaMapper.map(guiaEntity);
        XMLSerializador<GuiaRemision> serializador = new XMLSerializador<>(GuiaRemision.class);
        ByteArrayOutputStream bos = serializador.serializar(guiaJaxb);
        StringBuilder sb = new StringBuilder();
        sb.append("GUIA_");
        sb.append(guiaEntity.getInformacionTributaria().getEstablecimento());
        sb.append(guiaEntity.getInformacionTributaria().getPuntoEmision());
        sb.append("_");
        sb.append(guiaEntity.getInformacionTributaria().getSecuencial());
        sb.append(".xml");
        setButtonXml(guiaEntity.obtenerDocumentoSri(),sb.toString());
    }


    public PdfWindow(RetencionEntity retencion) throws Exception {
        this();

        List<RetencionEntity> lista = new ArrayList<>();
        lista.add(retencion);
        ReportsUtil rp = new ReportsUtil(baseReportsPath);
        pathLogo = rp.getBaseResourcesPath()+"/files/nologo.png";
        if(retencion.getEmisor().getPathLogo()!=null)
        {
            pathLogo = retencion.getEmisor().getPathLogo();
        }
        pdfButton.addClickListener(event -> this.mostrarReporte(lista,pathLogo,"cabeceraRetencion",Formato.PDF));
        ComprobanteRetencion comprobanteJaxb =  retencionMapper.map(retencion);
        XMLSerializador<ComprobanteRetencion> serializador = new XMLSerializador<>(ComprobanteRetencion.class);
        ByteArrayOutputStream bos = serializador.serializar(comprobanteJaxb);
        StringBuilder sb = new StringBuilder();
        sb.append("RET_");
        sb.append(retencion.getInformacionTributaria().getEstablecimento());
        sb.append(retencion.getInformacionTributaria().getPuntoEmision());
        sb.append("_");
        sb.append(retencion.getInformacionTributaria().getSecuencial());
        sb.append(".xml");
        setButtonXml(retencion.obtenerDocumentoSri(),sb.toString());
    }*/




    private void setButtonXml(byte[] arr,String fileName) throws JAXBException, IOException {
        StreamResource.StreamSource streamSource = (StreamResource.StreamSource) () -> {
              try {
                      return new ByteArrayInputStream(arr);
              } catch (Exception e) {
                   e.printStackTrace();
              }
              return null;
        };
        Resource res = new StreamResource(streamSource,fileName);
        BrowserWindowOpener opener = new BrowserWindowOpener(res);
        opener.extend(xmlButton);
    }


    protected void mostrarReporte(List dataSource,String pathLogo,String nombreReportFile,Formato formato)
    {
        if (dataSource == null || dataSource.size() == 0) {
            Notification.show("No se encontraron datos para el reporte!");
            return;
        }
        Map datosReporte = new HashMap();
        datosReporte.put("P_TITULO", "");
        datosReporte.put("P_SUBTITULO", "");
        datosReporte.put("LOGO_URL", pathLogo);
        datosReporte.put("PATH", baseReportsPath);
        ReportsUtil rpu = new ReportsUtil(baseReportsPath);
        Window window = rpu.getReportWindow(dataSource,nombreReportFile,"doc",datosReporte);
        window.setIcon(FontAwesome.FILE_PDF_O);
        UI.getCurrent().addWindow(window);

    }



    public void show()
    {
        UI.getCurrent().addWindow(this);
    }
}
