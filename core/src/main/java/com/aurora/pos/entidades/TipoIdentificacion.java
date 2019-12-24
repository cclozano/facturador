package com.aurora.pos.entidades;

/**
 * Created by max on 09/08/17.
 */
public enum TipoIdentificacion {
    CEDULA("05"),
    RUC("04"),
    PASAPORTE("06"),
    CONSUMIDOR_FINAL("07"),
    IDENTIFICACION_EXTERIOR("08"),
    PLACA("09");

    private String value;

    public String getValue()
    {
        return value;
    }

    TipoIdentificacion(String value)
    {

        this.value = value;
    }
}
