package com.aurora.pos.entidades;

import com.aurora.framework.data.BaseEntity;
import com.aurora.inventario.entidades.Item;
import com.aurora.inventario.entidades.UnidadMedida;
import lombok.Getter;
import lombok.Setter;
import org.eclipse.persistence.annotations.PrivateOwned;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Setter @Getter
@Table(name = "detalle_factura")
public class DetalleFactura extends BaseEntity{


    @NotNull
    @JoinColumn(nullable = false,name = "factura_id")
    @ManyToOne
    private Factura factura;

    @NotNull
    @JoinColumn(name = "item_id",nullable = false)
    private Item item;

    @NotNull
    @DecimalMin("0.000001")
    @Column(name = "cantidad",nullable = false, precision = 10,scale = 6)
    private BigDecimal cantidad = BigDecimal.ONE;

    @Column(name = "precio_unitario",nullable = false, precision = 10,scale = 6)
    private BigDecimal precioUnitario = BigDecimal.ZERO;

    @Column(name = "descuento",nullable = false, precision = 10,scale = 6)
    private BigDecimal descuento = BigDecimal.ZERO;

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private List<DetalleAdicional> detallesAdicionales = new ArrayList<>();

    @OneToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL,mappedBy = "detalleFactura")
    @PrivateOwned
    private List<ImpuestoFactura> impuestos = new ArrayList<>();

    private String descripcion = " ";

    @JoinColumn(name = "unidad_medida")
    @ManyToOne
    private UnidadMedida unidadMedida;

    @Transient
    private DetalleListaPrecios detalleListaPrecios;

    public void setDetalleListaPrecios(DetalleListaPrecios detalleListaPrecios)
    {
        this.detalleListaPrecios = detalleListaPrecios;
        if(detalleListaPrecios!=null)
        {
           this.item = detalleListaPrecios.getItem();
           this.precioUnitario = detalleListaPrecios.getPrecio();
            this.unidadMedida = detalleListaPrecios.getUnidadMedida();
           this.impuestos  = new ArrayList<>();
           if(detalleListaPrecios.getItem()!=null && detalleListaPrecios.getItem().getImpuestos()!=null) {
                for (Impuesto impuesto : detalleListaPrecios.getItem().getImpuestos()) {
                    addImpuesto(impuesto);
                }
            }
        }
    }



    public BigDecimal getSubTotal()
    {
        return (cantidad.multiply(this.precioUnitario)).subtract(descuento);
    }


    public TotalImpuesto getTotalImpuestoIva12()
    {
        for(ImpuestoFactura imp :  this.getImpuestos()) {

            if (imp.getCodigo().equals(Impuesto.CODIGO_IVA) && imp.getCodigoPorcentaje().equals(Impuesto.CODIGO_PORCENTAJE_IVA_12)) {

               TotalImpuesto totalIva12 = new TotalImpuesto(imp.getCodigo(), imp.getCodigoPorcentaje());
               totalIva12.setBaseImponible(totalIva12.getBaseImponible().add(imp.getBaseImponible()));
               totalIva12.setValor(totalIva12.getValor().add(imp.getValor()));
               return totalIva12;
            }
        }
        return null;
    }

    public TotalImpuesto getTotalImpuestoIva0()
    {
        for(ImpuestoFactura imp :  this.getImpuestos()) {

            if (imp.getCodigo().equals(Impuesto.CODIGO_IVA) && imp.getCodigoPorcentaje().equals(Impuesto.CODIGO_PORCENTAJE__IVA_0)) {

                TotalImpuesto totalIva0 = new TotalImpuesto(imp.getCodigo(), imp.getCodigoPorcentaje());
                totalIva0.setBaseImponible(totalIva0.getBaseImponible().add(imp.getBaseImponible()));
                totalIva0.setValor(totalIva0.getValor().add(imp.getValor()));
                return totalIva0;
            }
        }
        return null;
    }


    public TotalImpuesto getTotalICE()
    {
        for(ImpuestoFactura imp :  this.getImpuestos()) {

            if (imp.getCodigo().equals(Impuesto.CODIGO_ICE) ) {

                TotalImpuesto totalIva0 = new TotalImpuesto(imp.getCodigo(), imp.getCodigoPorcentaje());
                totalIva0.setBaseImponible(totalIva0.getBaseImponible().add(imp.getBaseImponible()));
                totalIva0.setValor(totalIva0.getValor().add(imp.getValor()));
                return totalIva0;
            }
        }
        return null;
    }







    public BigDecimal getTotalImpuesto()
    {
        BigDecimal totalImpuestos = BigDecimal.ZERO;
        for(ImpuestoFactura impuestoFactura : impuestos)
        {
            totalImpuestos = totalImpuestos.add(impuestoFactura.getValor());
        }
        return totalImpuestos;
    }

    public BigDecimal getTotalDetalle()
    {
        return getSubTotal().add(getTotalImpuesto());
    }


    public void addImpuesto(Impuesto impuesto)
    {
        ImpuestoFactura impuestoFactura = new ImpuestoFactura();
        impuestoFactura.setCodigo(impuesto.getCodigo());
        impuestoFactura.setCodigoPorcentaje(impuesto.getCodigoPorcentaje());
        impuestoFactura.setPorcentaje(impuesto.getTarifa());
        impuestoFactura.setNombre(impuesto.getNombre());
        addImpuesto(impuestoFactura);
    }


    public void addImpuesto(ImpuestoFactura impuestoFactura)
    {
        impuestoFactura.setDetalleFactura(this);
        this.impuestos.add(impuestoFactura);
    }

    public BigDecimal getTotalImpuesto(String codigoImpuesto)
    {
        BigDecimal totalIva = BigDecimal.ZERO;
        for (ImpuestoFactura impuesto : impuestos)
        {
            if(impuesto.getCodigo().equals(codigoImpuesto))
                totalIva = totalIva.add(impuesto.getValor());
        }
        return totalIva;
    }







}

