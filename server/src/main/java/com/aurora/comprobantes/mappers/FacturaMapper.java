package com.aurora.comprobantes.mappers;

import com.aurora.pos.entidades.*;
import com.aurora.pos.repositorios.EmisorRepositorio;
import com.aurora.pos.servicios.ParametrosEmisor;
import com.aurora.pos.sri.esquemas.factura_v1.Impuesto;
import com.aurora.pos.sri.esquemas.factura_v1.InfoTributaria;
import com.aurora.pos.sri.esquemas.factura_v1.ObligadoContabilidad;
import com.aurora.pos.sri.esquemas.factura_v1.Pagos;
import com.aurora.pos.sri.mappers.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

@Component
public class FacturaMapper implements Mapper<Factura,com.aurora.pos.sri.esquemas.factura_v1.Factura> {



    protected SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

    public static int NUMERO_DECIMALES = 2;


    //@Autowired
    //private ParametrosEmisor parametros;

    @Autowired
    private EmisorRepositorio emisorRepositorio;


    public FacturaMapper()
    {
    }

    private Emisor getEmisor()
    {
        List<Emisor> emisors = this.emisorRepositorio.findAll();
        if(emisors.size() == 0) return new Emisor();

        return emisors.get(0);
    }



    @Override
    public com.aurora.pos.sri.esquemas.factura_v1.Factura map(Factura entity) {


        com.aurora.pos.sri.esquemas.factura_v1.Factura factura = new com.aurora.pos.sri.esquemas.factura_v1.Factura();
        factura.setId("comprobante");
        factura.setVersion("1.1.0");

        Emisor emisor = getEmisor();

        InfoTributaria infoTributaria = new InfoTributaria();
        infoTributaria.setAmbiente( String.valueOf(entity.getAmbiente().getValue()) );
        infoTributaria.setTipoEmision("1");
        infoTributaria.setRazonSocial(emisor.getRazonSocialEmisor());
        infoTributaria.setNombreComercial(emisor.getNombreComercialEmisor());
        infoTributaria.setRuc(emisor.getRucEmisor());
        infoTributaria.setClaveAcceso(entity.getClaveAcceso());
        infoTributaria.setCodDoc(entity.getCodigoDocumento());
        infoTributaria.setEstab(entity.getPuntoEmision().getEstablecimiento().getCodigo());
        infoTributaria.setPtoEmi(entity.getPuntoEmision().getCodigo());
        infoTributaria.setSecuencial(entity.getNumeroDisplay());
        infoTributaria.setDirMatriz(emisor.getDireccionMatriz());
        factura.setInfoTributaria(infoTributaria);


        com.aurora.pos.sri.esquemas.factura_v1.Factura.InfoFactura infoFactura = new com.aurora.pos.sri.esquemas.factura_v1.Factura.InfoFactura();
        Date fechaEmision = entity.getFechaEmision();
        //formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
        String fechaString = formatter.format(fechaEmision);
        infoFactura.setFechaEmision(fechaString);
        infoFactura.setDirEstablecimiento(entity.getPuntoEmision().getEstablecimiento().getDireccion());
        //infoFactura.setContribuyenteEspecial(null);
        infoFactura.setContribuyenteEspecial(entity.getNumeroContribullenteEspecial() == 0 ? null:String.valueOf(entity.getNumeroContribullenteEspecial())  );
        infoFactura.setObligadoContabilidad( entity.isObligadoContabilidad()? ObligadoContabilidad.SI : ObligadoContabilidad.NO);
        infoFactura.setTipoIdentificacionComprador(entity.getCliente().getTipoIdentificacion().getValue());
        infoFactura.setRazonSocialComprador(entity.getCliente().getNombre());
        infoFactura.setIdentificacionComprador(entity.getCliente().getIdentificacion());
        infoFactura.setDireccionComprador(entity.getCliente().getDireccion());
        infoFactura.setTotalSinImpuestos(entity.getTotalSinImpuestos().setScale(NUMERO_DECIMALES, BigDecimal.ROUND_HALF_UP));
        infoFactura.setTotalDescuento(entity.getTotalDescuento().setScale(NUMERO_DECIMALES, BigDecimal.ROUND_HALF_UP));
        infoFactura.setPropina(null);
        infoFactura.setImporteTotal(entity.getTotalFactura().setScale(NUMERO_DECIMALES, BigDecimal.ROUND_HALF_UP));
        infoFactura.setMoneda("DOLAR");
        factura.setInfoFactura(infoFactura);

        if(entity.getTotalesImpuestos()!= null && entity.getTotalesImpuestos().size()>0) {
            com.aurora.pos.sri.esquemas.factura_v1.Factura.InfoFactura.TotalConImpuestos totalConImpuestos =
                    new com.aurora.pos.sri.esquemas.factura_v1.Factura.InfoFactura.TotalConImpuestos();
            for (TotalImpuesto impuesto : entity.getTotalesImpuestos()) {
                com.aurora.pos.sri.esquemas.factura_v1.Factura.InfoFactura.TotalConImpuestos.TotalImpuesto totalImpuesto1
                        = new com.aurora.pos.sri.esquemas.factura_v1.Factura.InfoFactura.TotalConImpuestos.TotalImpuesto();
                totalImpuesto1.setCodigo(impuesto.getCodigo());
                totalImpuesto1.setCodigoPorcentaje(impuesto.getCodigoPorcentaje());
                totalImpuesto1.setBaseImponible(impuesto.getBaseImponible() != null ? impuesto.getBaseImponible().setScale(NUMERO_DECIMALES, BigDecimal.ROUND_HALF_UP) : BigDecimal.ZERO);
                totalImpuesto1.setValor(impuesto.getValor() != null ? impuesto.getValor().setScale(NUMERO_DECIMALES, BigDecimal.ROUND_HALF_UP) : BigDecimal.ZERO);
                totalConImpuestos.getTotalImpuesto().add(totalImpuesto1);
            }
            infoFactura.setTotalConImpuestos(totalConImpuestos);
        }


        if(entity.getFormaPagos()!=null && entity.getFormaPagos().size()>0) {
            Pagos pagos = new Pagos();
            for (FormaPago pagoEntity : entity.getFormaPagos()) {
                Pagos.Pago pago = new Pagos.Pago();

                pago.setFormaPago(pagoEntity.getCodigoFormaPago());
                pago.setPlazo( new BigDecimal(pagoEntity.getPlazo()) );
                pago.setUnidadTiempo("dias");
                pago.setTotal(pagoEntity.getValor().setScale(NUMERO_DECIMALES, BigDecimal.ROUND_HALF_UP));
                pagos.getPago().add(pago);
            }
            infoFactura.setPagos(pagos);
        }

        com.aurora.pos.sri.esquemas.factura_v1.Factura.Detalles facturaDetalles =
                new com.aurora.pos.sri.esquemas.factura_v1.Factura.Detalles();
        for (DetalleFactura detalleFactura : entity.getDetalles())
        {
            com.aurora.pos.sri.esquemas.factura_v1.Factura.Detalles.Detalle detalle =
                    new com.aurora.pos.sri.esquemas.factura_v1.Factura.Detalles.Detalle();

            if(detalleFactura.getItem() !=null)
            {
                detalle.setCodigoAuxiliar(detalleFactura.getItem().getCodigo());
                //detalle.setCodigoAuxiliar();
            }


            StringBuilder sbDescripcion = new StringBuilder();
            sbDescripcion.append(detalleFactura.getItem()!=null?detalleFactura.getItem().getNombreCompleto()+"-":"");
            sbDescripcion.append(detalleFactura.getDescripcion());


            detalle.setDescripcion(sbDescripcion.toString());
            detalle.setCantidad(detalleFactura.getCantidad().setScale(6, BigDecimal.ROUND_HALF_UP));
            detalle.setPrecioUnitario(detalleFactura.getPrecioUnitario().setScale(6, BigDecimal.ROUND_HALF_UP));
            detalle.setDescuento(detalleFactura.getDescuento().setScale(NUMERO_DECIMALES, BigDecimal.ROUND_HALF_UP));
            detalle.setPrecioTotalSinImpuesto(detalleFactura.getSubTotal().setScale(NUMERO_DECIMALES, BigDecimal.ROUND_HALF_UP));


            if(detalleFactura.getImpuestos()!=null && detalleFactura.getImpuestos().size()>0) {
                com.aurora.pos.sri.esquemas.factura_v1.Factura.Detalles.Detalle.Impuestos impuestos
                        = new  com.aurora.pos.sri.esquemas.factura_v1.Factura.Detalles.Detalle.Impuestos();
                for (ImpuestoFactura impuestoFacturaEntity : detalleFactura.getImpuestos()) {
                    Impuesto impuesto = new Impuesto();
                    impuesto.setCodigo(impuestoFacturaEntity.getCodigo());
                    impuesto.setCodigoPorcentaje(impuestoFacturaEntity.getCodigoPorcentaje());
                    impuesto.setTarifa(impuestoFacturaEntity.getPorcentaje().setScale(NUMERO_DECIMALES, BigDecimal.ROUND_HALF_UP));
                    impuesto.setBaseImponible(impuestoFacturaEntity.getBaseImponible().setScale(NUMERO_DECIMALES, BigDecimal.ROUND_HALF_UP));
                    impuesto.setValor(impuestoFacturaEntity.getValor().setScale(NUMERO_DECIMALES, BigDecimal.ROUND_HALF_UP));
                    impuestos.getImpuesto().add(impuesto);

                }
                detalle.setImpuestos(impuestos);
            }

            /*
            if(detalleFacturaEntity.getDetallesAdicionales()!=null &&  detalleFacturaEntity.getDetallesAdicionales().size()>0) {
                Factura.Detalle.Detalle.DetallesAdicionales detallesAdicionales = new Factura.Detalle.Detalle.DetallesAdicionales();
                for (DetalleAdicionalEntity dt : detalleFacturaEntity.getDetallesAdicionales()) {
                    if(dt.getNombre()!=null && dt.getNombre().length()>0 &&
                            dt.getValor()!=null && dt.getValor().length()>0) {

                        Factura.Detalle.Detalle.DetallesAdicionales.DetAdicional detAdicional = new Factura.Detalle.Detalle.DetallesAdicionales.DetAdicional();
                        detAdicional.setNombre(dt.getNombre());
                        detAdicional.setValor(dt.getValor());
                        detallesAdicionales.getDetAdicional().add(detAdicional);
                    }
                }
                detalle.setDetallesAdicionales(detallesAdicionales);
            }*/
            facturaDetalles.getDetalle().add(detalle);
        }
        factura.setDetalles(facturaDetalles);


        /*
        if(entity.getInformacionAdicional()!=null && entity.getInformacionAdicional().size()>0) {
            Factura.InfoAdicional infoAdicional = new Factura.InfoAdicional();
            for (InformacionAdicionalFacturaEntity info : entity.getInformacionAdicional()) {
                if(info.getNombre()!=null && info.getNombre().length()>0 &&
                        info.getValor()!=null && info.getValor().length()>0) {
                    Factura.InfoAdicional.CampoAdicional campoAdicional = new Factura.InfoAdicional.CampoAdicional();
                    campoAdicional.setNombre(info.getNombre());
                    campoAdicional.setValue(info.getValor());
                    infoAdicional.getCampoAdicional().add(campoAdicional);
                }
            }
            if(infoAdicional.getCampoAdicional().size()>0) {
                factura.setInfoAdicional(infoAdicional);
            }
        }*/

        return factura;

    }
}
