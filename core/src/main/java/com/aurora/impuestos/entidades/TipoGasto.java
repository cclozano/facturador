package com.aurora.impuestos.entidades;



public enum TipoGasto {
    VIVIENDA("Vivienda"),
    EDUCACION("Educacion"),
    SALUD("Salud"),
    ALIMENTACION("Alimentacion"),
    VESTIMENTA("Vivienda"),
    OTROS("Otros");

    private String value;

    TipoGasto(String value)
    {
        this.value = value;
    }


    public String getValue() {
        return value;
    }
}
