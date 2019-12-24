package com.aurora.pos.sri.mappers;



/**
 * Created by max on 19/06/17.
 */
public class GuiaMapper {}/*extends MapperBase implements Mapper<GuiaEntity,GuiaRemision> {
    @Override
    public GuiaRemision map(GuiaEntity source) {
        GuiaRemision target = new GuiaRemision();
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

        GuiaRemision.InfoGuiaRemision infoGuiaRemision = new GuiaRemision.InfoGuiaRemision();
        infoGuiaRemision.setDirEstablecimiento(source.getDireccionEstablecimiento());
        infoGuiaRemision.setDirPartida(source.getDireccionPartida());
        infoGuiaRemision.setRazonSocialTransportista(source.getRazonSocialTransportista());
        infoGuiaRemision.setTipoIdentificacionTransportista(source.getTipoIdentificacionTransportista());
        infoGuiaRemision.setRucTransportista(source.getRucTransportista());
        infoGuiaRemision.setObligadoContabilidad(source.getObligadoContabilidad()? ObligadoContabilidad.SI:ObligadoContabilidad.NO);
        try {
            infoGuiaRemision.setFechaIniTransporte(formatter.format(source.getFechaInicioTransporte()));
            infoGuiaRemision.setFechaFinTransporte(formatter.format(source.getFechaFinTransporte()));
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }

        if(source.getPlaca()!=null && source.getPlaca().length()>0) {
            infoGuiaRemision.setPlaca(source.getPlaca());
        }
        target.setInfoGuiaRemision(infoGuiaRemision);
        GuiaRemision.Destinatarios destinatarios = new GuiaRemision.Destinatarios();

        for(DestinatarioEntity item : source.getDestinatarios())
        {
            Destinatario destinatario = new Destinatario();
            destinatario.setIdentificacionDestinatario(item.getIdentificacionDestinatario());
            destinatario.setRazonSocialDestinatario(item.getRazonSocialDestinatario());
            destinatario.setDirDestinatario(item.getDireccionDestinatario());
            destinatario.setMotivoTraslado(item.getMotivoTraslado());

            Destinatario.Detalles detalles = new Destinatario.Detalles();
            for (DetalleEntity det : item.getDetalles())
            {
               Detalle detalle = new Detalle();
               detalle.setDescripcion(det.getDescripcion());
               detalle.setCantidad(det.getCantidad()!=null?det.getCantidad().setScale(6, BigDecimal.ROUND_HALF_UP):BigDecimal.ZERO);
               if(det.getCodigoPrincipal() != null && det.getCodigoPrincipal().length() > 0) detalle.setCodigoInterno(det.getCodigoPrincipal());
               if(det.getCodigoAuxiliar() != null && det.getCodigoAuxiliar().length() > 0) detalle.setCodigoAdicional(det.getCodigoAuxiliar());

               if(det.getDetallesAdicionales()!=null && det.getDetallesAdicionales().size()>0) {
                   Detalle.DetallesAdicionales detallesAdicionales
                           = new Detalle.DetallesAdicionales();
                   for (DetalleAdicionalEntity adicional : det.getDetallesAdicionales()) {
                       if(adicional.getNombre()!=null && adicional.getNombre().length()>0 &&
                               adicional.getValor()!=null && adicional.getValor().length()>0) {
                           Detalle.DetallesAdicionales.DetAdicional detAdicional =
                                   new Detalle.DetallesAdicionales.DetAdicional();
                           detAdicional.setNombre(adicional.getNombre());
                           detAdicional.setValor(adicional.getValor());
                           detallesAdicionales.getDetAdicional().add(detAdicional);
                       }
                   }
                   if(detallesAdicionales.getDetAdicional().size()>0) {
                       detalle.setDetallesAdicionales(detallesAdicionales);
                   }
               }
               detalles.getDetalle().add(detalle);
            }
            destinatario.setDetalles(detalles);
            destinatarios.getDestinatario().add(destinatario);
        }

        target.setDestinatarios(destinatarios);
        return target;





    }
}*/
