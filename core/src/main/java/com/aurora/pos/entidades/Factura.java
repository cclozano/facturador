package com.aurora.pos.entidades;

import com.aurora.impuestos.entidades.RetencionImpuesto;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.eclipse.persistence.annotations.PrivateOwned;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter @Setter
@Entity
@Table(name = "FACTURA")
public class Factura extends DocumentoElectronicoImp {

    private static final String CODIGO_DOCUMENTO ="01";

    @Column(nullable = false)
    @NotNull
    private Long numero = 0l;

    @Column(nullable = false, name = "fechaEmision")
    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaEmision = new Date();

    @NotNull
    @JoinColumn(name = "cliente_id",nullable = false)
    private Cliente cliente;


    @NotNull
    @JoinColumn(name = "punto_emsion_id",nullable = false)
    private PuntoEmision puntoEmision;

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER,mappedBy = "factura")
    @PrivateOwned
    private List<DetalleFactura> detalles = new ArrayList<>();


    @OneToOne
    @JoinColumn(name = "retencion_id")
    private RetencionImpuesto retencionImpuesto = null;

   // @ManyToOne
   // @JoinColumn(name = "lista_precios")
   // private ListaPrecios listaPrecios;

    @OneToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @PrivateOwned
    private List<FormaPago> formaPagos = new ArrayList<>();


    public Date getFechaEmision()
    {
        return this.fechaEmision;
    }


    public BigDecimal getTotalSinImpuestos()
    {
        BigDecimal totalSinImpuesto = BigDecimal.ZERO;
        for(DetalleFactura det : detalles)
        {
            totalSinImpuesto = totalSinImpuesto.add(det.getSubTotal());
        }
        return totalSinImpuesto;
    }

    public BigDecimal getTotalDescuento()
    {
        BigDecimal totalDescuento = BigDecimal.ZERO;
        for(DetalleFactura det : detalles)
        {
            totalDescuento = totalDescuento.add(det.getDescuento());
        }
        return totalDescuento;
    }

    public List<TotalImpuesto> getTotalesImpuestos()
    {

        TotalImpuesto totalIva12 = null;
        TotalImpuesto totalIva0 = null;

        List<TotalImpuesto> lista = new ArrayList<>();

        for(DetalleFactura detalleFactura : detalles)
        {

            if(detalleFactura.getTotalImpuestoIva12() !=null)
            {
                if(totalIva12  == null) {
                    totalIva12 = new TotalImpuesto(detalleFactura.getTotalImpuestoIva12().getCodigo(),detalleFactura.getTotalImpuestoIva12().getCodigoPorcentaje());
                    lista.add(totalIva12);
                }
                totalIva12.setBaseImponible(totalIva12.getBaseImponible().add(detalleFactura.getTotalImpuestoIva12().getBaseImponible()));
                totalIva12.setValor(totalIva12.getValor().add(detalleFactura.getTotalImpuestoIva12().getValor()));
            }else if(detalleFactura.getTotalImpuestoIva0()!=null)
            {
                if(totalIva0  == null){
                    totalIva0 = new TotalImpuesto(detalleFactura.getTotalImpuestoIva0().getCodigo(),detalleFactura.getTotalImpuestoIva0().getCodigoPorcentaje());
                    lista.add(totalIva0);
                }
                totalIva0.setBaseImponible(totalIva0.getBaseImponible().add(detalleFactura.getTotalImpuestoIva0().getBaseImponible()));
                totalIva0.setValor(totalIva0.getValor().add(detalleFactura.getTotalImpuestoIva0().getValor()));
            }



        }

        return lista;

    }

    public BigDecimal getTotalFactura()
    {
        BigDecimal totalFactura = BigDecimal.ZERO;
        for(DetalleFactura det : detalles)
        {
            totalFactura = totalFactura.add(det.getTotalDetalle());
        }
        return totalFactura;

    }

    public BigDecimal getTotalImpuestos()
    {
        BigDecimal totalImpuestos = BigDecimal.ZERO;
        for(DetalleFactura det : detalles)
        {
            totalImpuestos = totalImpuestos.add(det.getTotalImpuesto());
        }
        return totalImpuestos;


    }

    public void agregarDetalle(DetalleFactura detalleFactura)
    {
        detalleFactura.setFactura(this);
        this.detalles.add(detalleFactura);
    }

    public BigDecimal getTotalIva12()
    {
        BigDecimal totalIva = BigDecimal.ZERO;
        for(DetalleFactura det : detalles)
        {
            totalIva = totalIva.add( det.getTotalImpuestoIva12()!=null? det.getTotalImpuestoIva12().getValor():BigDecimal.ZERO);
        }
        return totalIva;
    }

    public BigDecimal getSubTotalIva12()
    {
        BigDecimal totalIva = BigDecimal.ZERO;
        for(DetalleFactura det : detalles)
        {
            //totalIva = det.getTotalImpuesto(Impuesto.CODIGO_IVA).compareTo(BigDecimal.ZERO) == 1?
            //        totalIva.add(det.getSubTotal()) : totalIva.add(BigDecimal.ZERO);

            totalIva = totalIva.add(det.getTotalImpuestoIva12()!=null ?det.getTotalImpuestoIva12().getBaseImponible() : BigDecimal.ZERO);
        }
        return totalIva;
    }

    public BigDecimal getSubTotalZero()
    {
        BigDecimal subTotalIva0 = BigDecimal.ZERO;
        for(DetalleFactura det : detalles)
        {

            subTotalIva0 = subTotalIva0.add(det.getTotalImpuestoIva0()!=null?
                    det.getTotalImpuestoIva0().getBaseImponible():BigDecimal.ZERO);
        }
        return subTotalIva0;
    }


    public BigDecimal getSubTotalIce()
    {
        BigDecimal subtotalICe = BigDecimal.ZERO;
        for(DetalleFactura det : detalles)
        {
            //totalIva =det.getImpuestos() == null || det.getImpuestos().size()==0?
            //        totalIva.add(det.getSubTotal()) : totalIva.add(BigDecimal.ZERO);
            subtotalICe = subtotalICe.add(det.getTotalICE()!=null?
                    det.getTotalICE().getBaseImponible():BigDecimal.ZERO);
        }
        return subtotalICe;
    }



    public BigDecimal getTotalIce()
    {
        BigDecimal totalIce = BigDecimal.ZERO;
        for(DetalleFactura det : detalles)
        {
            totalIce = totalIce.add(det.getTotalImpuesto(Impuesto.CODIGO_ICE));
        }
        return totalIce;
    }

   /* public void setPuntoEmision(PuntoEmision puntoEmision)
    {
        this.puntoEmision=puntoEmision;
        this.numero = puntoEmision.getSiguienteNumero();
    }*/

    public String getNumeroDisplay()
    {

        return StringUtils.leftPad(getNumero().toString(), 9, '0');
    }



    @Override
    public String getCodigoDocumento() {
        return CODIGO_DOCUMENTO;
    }

    @Override
    public String getSerie() {
        return getPuntoEmision().getEstablecimiento().getCodigo() + getPuntoEmision().getCodigo();
    }

    @Override
    public String getSecuencial() {
        return getNumeroDisplay();
    }



    public void generarFormaPago()
    {
        FormaPago formaPago = new Efectivo();
        formaPago.setValor(getTotalFactura());
        formaPago.setTipo(FormaPago.TipoFormaPago.CONTADO);
        formaPago.setPlazo(0);

        if(formaPago ==null)
            this.formaPagos = new ArrayList<>();
        this.formaPagos.add(formaPago);

    }



    public String getNumeroCompleto()
    {
        StringBuilder sb = new StringBuilder();
        sb.append(this.puntoEmision.getEstablecimiento().getCodigo());
        sb.append("-");
        sb.append(this.puntoEmision.getCodigo());
        sb.append("-");
        sb.append(this.getNumeroDisplay());
        return sb.toString();
    }


}
