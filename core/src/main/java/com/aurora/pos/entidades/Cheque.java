package com.aurora.pos.entidades;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
@Getter @Setter
public class Cheque extends FormaPago{

    @NotNull
    @JoinColumn(name = "banco_id")
    @ManyToOne
    private Banco banco;
    @Temporal(TemporalType.DATE)
    @NotNull
    @Column(name = "fecha_cobro")
    private Date fechaCobro;

    @Size(max = 50)
    @Column(length = 50)
    private String numero;

    @Override
    public String getCodigoFormaPago() {
        //OTROS CON UTILIZACION DEL SISTEMA  FINANCIERO
        return "20";
    }

    @Override
    public String getFormaPago() {
        return "Cheque";
    }
}
