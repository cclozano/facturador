package com.aurora.comprobantes;


import com.aurora.comprobantes.mappers.FacturaMapper;
import com.aurora.config.ParametrosServer;
import com.aurora.pos.entidades.Emisor;
import com.aurora.pos.entidades.Factura;
import com.aurora.pos.servicios.ParametrosEmisor;
import com.aurora.pos.servicios.ServicioEmisor;
import com.aurora.pos.sri.api.Autorizador;
import com.aurora.pos.sri.api.AutorizadorImp;
import com.aurora.pos.sri.api.ProcesadorDocumento;
import com.aurora.pos.sri.api.ProcesadorDocumentoImp;
import com.aurora.pos.sri.esquemas.XMLSerializador;
import com.aurora.pos.sri.firma.FirmaElectronica;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.vaadin.spring.annotation.PrototypeScope;

@Configuration
public class ComprobantesElectronicosConfig {


    @Autowired
    private ParametrosServer parametros;

    //@Autowired
    //private ParametrosEmisor parametrosEmisor;

    @Autowired
    private ServicioEmisor servicioEmisor;



    @Autowired
    FacturaMapper facturaMapper;

    @Bean("procesadorFacturaDesarrollo")
    @Lazy
    public ProcesadorDocumento<Factura> getProcesadorFacturaDessarrollo()
    {
       // FacturaMapper facturaMapper = new FacturaMapper(parametrosEmisor);
        XMLSerializador<com.aurora.pos.sri.esquemas.factura_v1.Factura> serializador
                = new XMLSerializador<>(com.aurora.pos.sri.esquemas.factura_v1.Factura.class);

        ProcesadorDocumento<Factura> procesadorDocumento
                = new ProcesadorDocumentoImp<com.aurora.pos.sri.esquemas.factura_v1.Factura,Factura>
                (facturaMapper,serializador,getAutorizadorDesarrollo(),getFirmaElectronica());

        return procesadorDocumento;
    }

    @Bean("procesadorFacturaProduccion")
    @Lazy
    public ProcesadorDocumento<Factura> getProcesadorFacturaProduccion()
    {
        //FacturaMapper facturaMapper = new FacturaMapper(parametrosEmisor);
        XMLSerializador<com.aurora.pos.sri.esquemas.factura_v1.Factura> serializador
                = new XMLSerializador<>(com.aurora.pos.sri.esquemas.factura_v1.Factura.class);

        ProcesadorDocumento<Factura> procesadorDocumento
                = new ProcesadorDocumentoImp<com.aurora.pos.sri.esquemas.factura_v1.Factura,Factura>
                (facturaMapper,serializador,getAutorizadorProduccion(),getFirmaElectronica());

        return procesadorDocumento;
    }


    @Bean( name = "autorizadorDesarrollo")
    public Autorizador getAutorizadorDesarrollo()
    {
        return new AutorizadorImp(parametros.getUrlRecepcionDesarrollo(), parametros.getUrlAutorizacionDesarrollo());
    }


    @Bean( name = "autorizadorProduccion")
    public Autorizador getAutorizadorProduccion(){

        return new AutorizadorImp(parametros.getUrlRecepcionProduccion(), parametros.getUrlAutorizacionProduccion());
    }

    @Bean
    @Lazy
    public FirmaElectronica getFirmaElectronica()
    {
        Emisor emisor =  servicioEmisor.getEmisor();
        FirmaElectronica firmaElectronica = new FirmaElectronica(emisor.getPasswordKey(),emisor.getPathkey());
        return firmaElectronica;
    }
}
