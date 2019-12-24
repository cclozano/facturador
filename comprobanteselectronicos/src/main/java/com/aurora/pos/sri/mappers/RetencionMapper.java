package com.aurora.pos.sri.mappers;

import java.math.BigDecimal;

/**
 * Created by max on 19/06/17.
 */
public class RetencionMapper {}/* extends MapperBase implements Mapper<RetencionEntity,ComprobanteRetencion> {
    @Override
    public ComprobanteRetencion map(RetencionEntity source) {
        ComprobanteRetencion target = new ComprobanteRetencion();

        if(source.getInformacionTributaria()!=null)
        {
            target.setVersion("1.0.0");
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

        ComprobanteRetencion.InfoCompRetencion infoCompRetencion = new ComprobanteRetencion.InfoCompRetencion();
        infoCompRetencion.setFechaEmision( formatter.format(source.getFechaEmision()));
        infoCompRetencion.setDirEstablecimiento(source.getDireccionEstablecimiento());
        infoCompRetencion.setContribuyenteEspecial(source.getContribuyenteEspecial());
        infoCompRetencion.setObligadoContabilidad(source.getObligadoContabilidad()?ObligadoContabilidad.SI:ObligadoContabilidad.NO);
        infoCompRetencion.setTipoIdentificacionSujetoRetenido(source.getTipoIdentificacionSujetoRetenido());
        infoCompRetencion.setRazonSocialSujetoRetenido(source.getRazonSocialSujetoRetenido());
        infoCompRetencion.setIdentificacionSujetoRetenido(source.getIdentificacionSujetoRetenido());
        infoCompRetencion.setPeriodoFiscal(source.getPeriodoFiscal());
        target.setInfoCompRetencion(infoCompRetencion);


        ComprobanteRetencion.Impuestos impuestos = new ComprobanteRetencion.Impuestos();
        for (RetencionImpuesto imp : source.getDetalles())
        {
            Impuesto impuesto = new Impuesto();
            impuesto.setCodigo(imp.getCodigo());
            impuesto.setCodigoRetencion(imp.getCodigoRetencion());
            impuesto.setBaseImponible(imp.getBaseImponible()!=null?imp.getBaseImponible().setScale(NUMERO_DECIMALES, BigDecimal.ROUND_HALF_UP):BigDecimal.ZERO);
            impuesto.setValorRetenido(imp.getValorRetenido()!=null?imp.getValorRetenido().setScale(NUMERO_DECIMALES, BigDecimal.ROUND_HALF_UP):BigDecimal.ZERO);
            impuesto.setCodDocSustento(imp.getCodDocSustento());
            impuesto.setNumDocSustento(imp.getNumDocSustento());
            impuesto.setFechaEmisionDocSustento(formatter.format(imp.getFechaEmisionDocSustento()));
            impuesto.setPorcentajeRetener(imp.getPorcentajeRetener()!=null?imp.getPorcentajeRetener().setScale(NUMERO_DECIMALES, BigDecimal.ROUND_HALF_UP):BigDecimal.ZERO);
            impuestos.getImpuesto().add(impuesto);
        }
        target.setImpuestos(impuestos);

        if(source.getCamposAdicionales()!=null && source.getCamposAdicionales().size() > 0) {
            ComprobanteRetencion.InfoAdicional infoAdicional = new ComprobanteRetencion.InfoAdicional();
            for (CampoAdicionalEntity campoAdicional : source.getCamposAdicionales()) {
                if(campoAdicional.getNombre()!=null && campoAdicional.getNombre().length()>0 &&
                        campoAdicional.getValor()!=null && campoAdicional.getValor().length()>0) {
                    ComprobanteRetencion.InfoAdicional.CampoAdicional adicional = new ComprobanteRetencion.InfoAdicional.CampoAdicional();
                    adicional.setNombre(campoAdicional.getNombre());
                    adicional.setValue(campoAdicional.getValor());
                    infoAdicional.getCampoAdicional().add(adicional);
                }
            }
            if(infoAdicional.getCampoAdicional().size()>0) {
                target.setInfoAdicional(infoAdicional);
            }
        }
        return target;

    }
}*/
