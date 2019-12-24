package com.aurora.pos.server.ui.util;

import org.junit.Test;
import com.aurora.util.IdentificacionValidator;

import static org.junit.Assert.*;

public class CedulaValidatorTest {
    @Test
    public void validar() throws Exception {


        IdentificacionValidator identificacionValidator = new IdentificacionValidator();
        assertEquals(true, identificacionValidator.validarCedula("1309410445"));
        assertEquals(true, identificacionValidator.validarCedula("0923495816"));

        assertEquals(false, identificacionValidator.validarCedula("1309410425"));
        assertEquals(false, identificacionValidator.validarCedula("0923495814"));
    }

}