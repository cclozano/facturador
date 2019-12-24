package com.aurora.impuestos.entidades;


import com.aurora.util.Identificacion;
import lombok.Getter;
import lombok.Setter;
import org.eclipse.persistence.annotations.PrivateOwned;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter
@Entity
@Table(name = "FACTURA_COMPRA")
public class FacturaCompra extends ComprobanteBase{

    @JoinColumn(name = "tipo_factura_compra")
    @ManyToOne
    private TipoFacturaCompra tipoFacturaCompra;

    private String detalle;

    @NotNull
    private String numero;

    @NotNull
    @Identificacion
    private String ruc;

    //Nombre Emisor
    private String nombre;

    private BigDecimal subtotal12 = BigDecimal.ZERO;

    private BigDecimal subtotal0 = BigDecimal.ZERO;

    @OneToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL,mappedBy = "facturaCompra")
    @PrivateOwned
    private List<OtroImpuestosCompra> otrosImpestos = new ArrayList<>() ;



    public BigDecimal getIva()
    {
        if(subtotal12==null)
            return BigDecimal.ZERO;
        return subtotal12.multiply(new BigDecimal("0.12")).setScale(2,BigDecimal.ROUND_HALF_UP);
    }

    public BigDecimal getSubTotalOtrosImpuestos()
    {
        BigDecimal total = BigDecimal.ZERO;
        for(OtroImpuestosCompra item : otrosImpestos)
        {
            total = total.add(item.getValor());
        }
        return total.setScale(2,BigDecimal.ROUND_HALF_UP);
    }

    public BigDecimal getTotalOtrosImpuestos()
    {
        BigDecimal total = BigDecimal.ZERO;
        for(OtroImpuestosCompra item : otrosImpestos)
        {
            total = total.add(item.getValorTotal());
        }
        return total.setScale(2,BigDecimal.ROUND_HALF_UP);
    }


    public BigDecimal getTotalFactura()
    {

        BigDecimal iva = subtotal12!=null ?  getIva().add(subtotal12) : BigDecimal.ZERO;
        BigDecimal cero = getSubtotal0() == null ? BigDecimal.ZERO:getSubtotal0();
        BigDecimal otros = getTotalOtrosImpuestos() == null ? BigDecimal.ZERO:getTotalOtrosImpuestos();
        return iva.add(cero).add(otros).setScale(2,BigDecimal.ROUND_HALF_UP);
    }


}
