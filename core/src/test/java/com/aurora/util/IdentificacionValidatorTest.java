package com.aurora.util;

import org.junit.Test;

import static org.junit.Assert.*;

public class IdentificacionValidatorTest {
    @Test
    public void validarCedula() throws Exception {
    }

    @Test
    public void validarRuc() throws Exception {


        IdentificacionValidator validator = new IdentificacionValidator();
        boolean respuesta = validator.validarRuc("1309410445001");
        assertEquals(true,respuesta);


        boolean respuesta2 = validator.validarRuc("1790710319001");
        assertEquals(true,respuesta2);


        boolean respuesta3 = validator.validarRuc("1309410445");
        assertEquals(false, respuesta3);


        boolean respuesta4 = validator.validarRuc("130941044500x");
        assertEquals(false, respuesta4);


    }

}