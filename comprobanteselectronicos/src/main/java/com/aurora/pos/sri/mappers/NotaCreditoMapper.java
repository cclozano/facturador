package com.aurora.pos.sri.mappers;


import java.math.BigDecimal;

/**
 * Created by max on 19/06/17.
 */
public class NotaCreditoMapper{}/* extends MapperBase implements Mapper<NotaCreditoEntity,NotaCredito> {
    @Override
    public NotaCredito map(NotaCreditoEntity source) {

        NotaCredito target = new NotaCredito();

        if(source.getInformacionTributaria()!=null)
        {
            target.setVersion("1.1.0");
            target.setId("comprobante");
            InfoTributaria infoTributaria = new InfoTributaria();
            infoTributaria.setAmbiente(source.getInformacionTributaria().getAmbiente());
            infoTributaria.setTipoEmision(source.getInformacionTributaria().getTipoEmision());
            infoTributaria.setRazonSocial(source.getInformacionTributaria().getRazonSocial());
            infoTributaria.setNombreComercial(source.getInformacionTributaria().getNombreComercial());
            infoTributaria.setRuc(source.getInformacionTributaria().getRuc());
            infoTributaria.setClaveAcceso(source.getInformacionTributaria().getClaveAcceso());
            infoTributaria.setCodDoc(source.getInformacionTributaria().getCodigoDocumento());
            infoTributaria.setEstab(source.getInformacionTributaria().getEstablecimento());
            infoTributaria.setPtoEmi(source.getInformacionTributaria().getPuntoEmision());
            infoTributaria.setSecuencial(source.getInformacionTributaria().getSecuencial());
            infoTributaria.setDirMatriz(source.getInformacionTributaria().getDireccionMatriz());
            target.setInfoTributaria(infoTributaria);

        }

        //SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

        NotaCredito.InfoNotaCredito infoNotaCredito
                = new NotaCredito.InfoNotaCredito();

        infoNotaCredito.setFechaEmision(formatter.format(source.getFechaEmision()));
        infoNotaCredito.setDirEstablecimiento(source.getDireccionEstablecimiento());
        infoNotaCredito.setTipoIdentificacionComprador(source.getTipoIdentificacionComprador());
        infoNotaCredito.setRazonSocialComprador(source.getRazonSocialComprador());
        infoNotaCredito.setIdentificacionComprador(source.getIdentificacionComprador());
        infoNotaCredito.setObligadoContabilidad(source.getObligadoLlevarContablidad()?ObligadoContabilidad.SI:ObligadoContabilidad.NO);
        infoNotaCredito.setCodDocModificado(source.getCodigoDocumentoModificado());
        infoNotaCredito.setNumDocModificado(source.getNumeroDocumentoModificado());
        infoNotaCredito.setFechaEmisionDocSustento(formatter.format(source.getFechaEmisionDocumentoSustento()));
        infoNotaCredito.setTotalSinImpuestos(source.getTotalSinImpuestos().setScale(NUMERO_DECIMALES, BigDecimal.ROUND_HALF_UP));
        infoNotaCredito.setValorModificacion(source.getValorModificacion().setScale(NUMERO_DECIMALES, BigDecimal.ROUND_HALF_UP));
        infoNotaCredito.setMoneda(source.getMoneda());

        if(source.getTotalImpuestos()!=null && source.getTotalImpuestos().size()>0) {
            TotalConImpuestos totalConImpuestos = new TotalConImpuestos();
            for (TotalImpuestoEntity item : source.getTotalImpuestos()) {
                TotalConImpuestos.TotalImpuesto impuesto = new TotalConImpuestos.TotalImpuesto();
                impuesto.setCodigo(item.getCodigo());
                impuesto.setCodigoPorcentaje(item.getCodigoPorcentaje());
                impuesto.setBaseImponible(item.getBaseImponible().setScale(NUMERO_DECIMALES, BigDecimal.ROUND_HALF_UP));
                impuesto.setValor(item.getValor().setScale(NUMERO_DECIMALES, BigDecimal.ROUND_HALF_UP));

                totalConImpuestos.getTotalImpuesto().add(impuesto);
            }
            infoNotaCredito.setTotalConImpuestos(totalConImpuestos);
        }


        infoNotaCredito.setMotivo(source.getMotivo());
        target.setInfoNotaCredito(infoNotaCredito);

        NotaCredito.Detalles detalles = new NotaCredito.Detalles();
        for(DetalleNotaCreditoEntity item: source.getDetalles()){

            NotaCredito.Detalles.Detalle detalle = new NotaCredito.Detalles.Detalle();

            detalle.setCodigoInterno(item.getCodigoInterno());
            detalle.setCodigoAdicional(item.getCodigoAdicional());
            detalle.setDescripcion(item.getDescripcion());
            detalle.setCantidad(item.getCantidad().setScale(6, BigDecimal.ROUND_HALF_UP));
            detalle.setPrecioUnitario(item.getPrecioUnitario().setScale(6, BigDecimal.ROUND_HALF_UP));
            detalle.setDescuento(item.getDescuento().setScale(NUMERO_DECIMALES, BigDecimal.ROUND_HALF_UP));
            detalle.setPrecioTotalSinImpuesto(item.getPrecioTotalSinImpuestos().setScale(NUMERO_DECIMALES, BigDecimal.ROUND_HALF_UP));

            if(item.getDetallesAdicionales()!=null && item.getDetallesAdicionales().size()>0) {
                NotaCredito.Detalles.Detalle.DetallesAdicionales detallesAdicionales =
                        new NotaCredito.Detalles.Detalle.DetallesAdicionales();
                for (DetalleAdicionalEntity det : item.getDetallesAdicionales()) {

                    if(det.getNombre()!=null && det.getNombre().length()>0 &&
                            det.getValor()!=null && det.getValor().length()>0) {

                        NotaCredito.Detalles.Detalle.DetallesAdicionales.DetAdicional detalleAdicional =
                                new NotaCredito.Detalles.Detalle.DetallesAdicionales.DetAdicional();

                        detalleAdicional.setNombre(det.getNombre());
                        detalleAdicional.setValor(det.getValor());
                        detallesAdicionales.getDetAdicional().add(detalleAdicional);
                    }

                }
                if(detallesAdicionales.getDetAdicional().size()>0) {
                    detalle.setDetallesAdicionales(detallesAdicionales);
                }
            }

            if(item.getImpuestos()!=null && item.getImpuestos().size()>0) {
                NotaCredito.Detalles.Detalle.Impuestos impuestos = new NotaCredito.Detalles.Detalle.Impuestos();
                for (ImpuestoEntity imp : item.getImpuestos()) {

                    Impuesto impuesto = new Impuesto();

                    impuesto.setCodigo(imp.getCodigo());
                    impuesto.setCodigoPorcentaje(imp.getCodigoPorcentaje());
                    impuesto.setTarifa(imp.getTarifa().setScale(NUMERO_DECIMALES, BigDecimal.ROUND_HALF_UP));
                    impuesto.setBaseImponible(imp.getBaseImponible().setScale(NUMERO_DECIMALES, BigDecimal.ROUND_HALF_UP));
                    impuesto.setValor(imp.getValor().setScale(NUMERO_DECIMALES, BigDecimal.ROUND_HALF_UP));
                    impuestos.getImpuesto().add(impuesto);
                }
                detalle.setImpuestos(impuestos);
            }



            detalles.getDetalle().add(detalle);
        }
        target.setDetalles(detalles);

        if(source.getCamposAdicionales()!=null && source.getCamposAdicionales().size()>0) {
            NotaCredito.InfoAdicional infoAdicional = new NotaCredito.InfoAdicional();
            for (CampoAdicionalEntity campoAdicional : source.getCamposAdicionales()) {
                if(campoAdicional.getNombre()!=null && campoAdicional.getNombre().length()>0 &&
                        campoAdicional.getValor()!=null && campoAdicional.getValor().length()>0) {

                    NotaCredito.InfoAdicional.CampoAdicional adicional = new NotaCredito.InfoAdicional.CampoAdicional();
                    adicional.setNombre(campoAdicional.getNombre());
                    adicional.setValue(campoAdicional.getValor());
                    infoAdicional.getCampoAdicional().add(adicional);
                }
            }
            target.setInfoAdicional(infoAdicional);
        }
        return  target;

    }
}*/
