package com.aurora.pos.sri.mappers;


import java.math.BigDecimal;

/**
 * Created by max on 19/05/17.
 */
public class FacturaMapper {} /*extends MapperBase implements Mapper<FacturaEntity,Factura>{




    public Factura map(FacturaEntity entity)
    {

        Factura factura = new Factura();
        factura.setId("comprobante");
        factura.setVersion("1.1.0");

        if(entity.getInformacionTributaria()!=null) {
            InfoTributaria infoTributaria = new InfoTributaria();
            infoTributaria.setAmbiente(entity.getInformacionTributaria().getAmbiente()!=null?entity.getInformacionTributaria().getAmbiente().toString():"");
            infoTributaria.setTipoEmision(entity.getInformacionTributaria().getTipoEmision());
            infoTributaria.setRazonSocial(entity.getInformacionTributaria().getRazonSocial());
            infoTributaria.setNombreComercial(entity.getInformacionTributaria().getNombreComercial());
            infoTributaria.setRuc(entity.getInformacionTributaria().getRuc());
            infoTributaria.setClaveAcceso(entity.getInformacionTributaria().getClaveAcceso());
            infoTributaria.setCodDoc(entity.getInformacionTributaria().getCodigoDocumento());
            infoTributaria.setEstab(entity.getInformacionTributaria().getEstablecimento());
            infoTributaria.setPtoEmi(entity.getInformacionTributaria().getPuntoEmision());
            infoTributaria.setSecuencial(entity.getInformacionTributaria().getSecuencial());
            infoTributaria.setDirMatriz(entity.getInformacionTributaria().getDireccionMatriz());
            factura.setInfoTributaria(infoTributaria);
        }

        Factura.InfoFactura infoFactura = new Factura.InfoFactura();
        infoFactura.setFechaEmision(formatter.format(entity.getFechaEmision()));
        infoFactura.setDirEstablecimiento(entity.getDireccionEstablecimiento());
        infoFactura.setContribuyenteEspecial(entity.getContribuyenteEspecial());
        infoFactura.setObligadoContabilidad( entity.getObligadoLlevarContabilidad()? ObligadoContabilidad.SI : ObligadoContabilidad.NO);
        infoFactura.setTipoIdentificacionComprador(entity.getTipoIdentificacionComprador());
        infoFactura.setRazonSocialComprador(entity.getRazonSocialComprador());
        infoFactura.setIdentificacionComprador(entity.getIdentificacionComprador());
        infoFactura.setDireccionComprador(entity.getDireccionComprador());
        infoFactura.setTotalSinImpuestos(entity.getTotalSinImpuestos());
        infoFactura.setTotalDescuento(entity.getTotalDescuento());
        infoFactura.setPropina(entity.getPropina());
        infoFactura.setImporteTotal(entity.getImporteTotal());
        infoFactura.setMoneda(entity.getMoneda());
        factura.setInfoFactura(infoFactura);

        if(entity.getTotalImpuestos()!= null && entity.getTotalImpuestos().size()>0) {
            Factura.InfoFactura.TotalConImpuestos totalConImpuestos = new Factura.InfoFactura.TotalConImpuestos();
            for (ImpuestoTotalFacturaEntity impuesto : entity.getTotalImpuestos()) {
                Factura.InfoFactura.TotalConImpuestos.TotalImpuesto totalImpuesto1 = new Factura.InfoFactura.TotalConImpuestos.TotalImpuesto();
                totalImpuesto1.setCodigo(impuesto.getCodigo());
                totalImpuesto1.setCodigoPorcentaje(impuesto.getCodigoPorcentaje());
                totalImpuesto1.setBaseImponible(impuesto.getBaseImponible() != null ? impuesto.getBaseImponible().setScale(NUMERO_DECIMALES, BigDecimal.ROUND_HALF_UP) : BigDecimal.ZERO);
                totalImpuesto1.setValor(impuesto.getValor() != null ? impuesto.getValor().setScale(NUMERO_DECIMALES, BigDecimal.ROUND_HALF_UP) : BigDecimal.ZERO);
                totalConImpuestos.getTotalImpuesto().add(totalImpuesto1);
            }
            infoFactura.setTotalConImpuestos(totalConImpuestos);
        }


        if(entity.getPagos()!=null && entity.getPagos().size()>0) {
            Pagos pagos = new Pagos();
            for (PagoFacturaEntity pagoEntity : entity.getPagos()) {
                Pagos.Pago pago = new Pagos.Pago();

                pago.setFormaPago(pagoEntity.getFormaPago());
                pago.setPlazo(pagoEntity.getPlazo());
                pago.setUnidadTiempo(pagoEntity.getUnidadTiempo());
                pago.setTotal(pagoEntity.getTotal() != null ? pagoEntity.getTotal().setScale(NUMERO_DECIMALES, BigDecimal.ROUND_HALF_UP) : BigDecimal.ZERO);
                pagos.getPago().add(pago);
            }
            infoFactura.setPagos(pagos);
        }

       Factura.Detalles facturaDetalles = new Factura.Detalles();
       for (DetalleFacturaEntity detalleFacturaEntity : entity.getDetalles())
       {
            Factura.Detalles.Detalle detalle = new Factura.Detalles.Detalle();

            if(detalleFacturaEntity.getCodigoPrincipal()!=null && detalleFacturaEntity.getCodigoPrincipal().length()>0) {
                detalle.setCodigoPrincipal(detalleFacturaEntity.getCodigoPrincipal());
            }

            if(detalleFacturaEntity.getCodigoAuxiliar()!=null&& detalleFacturaEntity.getCodigoAuxiliar().length()>0) {
                detalle.setCodigoAuxiliar(detalleFacturaEntity.getCodigoAuxiliar());
            }
            detalle.setDescripcion(detalleFacturaEntity.getDescripcion());
            detalle.setCantidad(detalleFacturaEntity.getCantidad()!=null?detalleFacturaEntity.getCantidad().setScale(6, BigDecimal.ROUND_HALF_UP):BigDecimal.ZERO);
            detalle.setPrecioUnitario(detalleFacturaEntity.getPrecioUnitario()!=null?detalleFacturaEntity.getPrecioUnitario().setScale(6, BigDecimal.ROUND_HALF_UP):BigDecimal.ZERO);
            detalle.setDescuento(detalleFacturaEntity.getDescuento()!=null?detalleFacturaEntity.getDescuento().setScale(NUMERO_DECIMALES, BigDecimal.ROUND_HALF_UP):BigDecimal.ZERO);
            detalle.setPrecioTotalSinImpuesto(detalleFacturaEntity.getPrecioTotalSinImpuestos()!=null?detalleFacturaEntity.getPrecioTotalSinImpuestos().setScale(NUMERO_DECIMALES, BigDecimal.ROUND_HALF_UP):BigDecimal.ZERO);

            if(detalleFacturaEntity.getImpuestos()!=null && detalleFacturaEntity.getImpuestos().size()>0) {
                Factura.Detalles.Detalle.Impuestos impuestos = new Factura.Detalles.Detalle.Impuestos();
                for (ImpuestoFacturaEntity impuestoFacturaEntity : detalleFacturaEntity.getImpuestos()) {
                    Impuesto impuesto = new Impuesto();
                    impuesto.setCodigo(impuestoFacturaEntity.getCodigo());
                    impuesto.setCodigoPorcentaje(impuestoFacturaEntity.getCodigoPorcentaje());
                    impuesto.setTarifa(impuestoFacturaEntity.getTarifa().setScale(NUMERO_DECIMALES, BigDecimal.ROUND_HALF_UP));
                    impuesto.setBaseImponible(impuestoFacturaEntity.getBaseImponible().setScale(NUMERO_DECIMALES, BigDecimal.ROUND_HALF_UP));
                    impuesto.setValor(impuestoFacturaEntity.getValor().setScale(NUMERO_DECIMALES, BigDecimal.ROUND_HALF_UP));
                    impuestos.getImpuesto().add(impuesto);

                }
                detalle.setImpuestos(impuestos);
            }

           if(detalleFacturaEntity.getDetallesAdicionales()!=null &&  detalleFacturaEntity.getDetallesAdicionales().size()>0) {
                Factura.Detalles.Detalle.DetallesAdicionales detallesAdicionales = new Factura.Detalles.Detalle.DetallesAdicionales();
                for (DetalleAdicionalEntity dt : detalleFacturaEntity.getDetallesAdicionales()) {
                    if(dt.getNombre()!=null && dt.getNombre().length()>0 &&
                            dt.getValor()!=null && dt.getValor().length()>0) {

                        Factura.Detalles.Detalle.DetallesAdicionales.DetAdicional detAdicional = new Factura.Detalles.Detalle.DetallesAdicionales.DetAdicional();
                        detAdicional.setNombre(dt.getNombre());
                        detAdicional.setValor(dt.getValor());
                        detallesAdicionales.getDetAdicional().add(detAdicional);
                    }
                }
                detalle.setDetallesAdicionales(detallesAdicionales);
            }
            facturaDetalles.getDetalle().add(detalle);
        }
        factura.setDetalles(facturaDetalles);


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
       }

        return factura;
    }

}*/
