package com.aurora.pos.servicios;

public class ServiceException extends Exception {
    public ServiceException(String comprobante_approbado) {
        super(comprobante_approbado);
    }
}
