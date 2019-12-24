package com.aurora.pos.entidades;

import com.aurora.framework.data.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Setter @Getter
//@Table(name = "forma_pago_tarjeta")
public class FormaPagoTarjeta extends FormaPago {
    @NotNull
    @Size(max = 50)
    @Column(name = "numero_voucher",length = 50)
    private String numeroVoucher;
    @Column(name = "numero_tarjeta",length = 50)
    private String numeroTarjeta;
    @NotNull
    @JoinColumn(name = "tarjeta_credito")
    @ManyToOne
    private TarjetaCredito tarjetaCredito;

    @Override
    public String getCodigoFormaPago() {

        //Tarjeta de Credito
        return "19";
    }

    @Override
    public String getFormaPago() {
        return "Tarjeta de Credito";
    }
}
