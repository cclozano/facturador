package com.aurora.pos.entidades;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
public class Efectivo extends FormaPago{


    @Override
    public String getCodigoFormaPago() {
        //SIN UTILIZACION DEL SISTEMA FINANCIERO
        return "01";
    }

    @Override
    public String getFormaPago() {
        return "Efectivo";
    }
}
