package com.aurora.util;


import com.sun.org.apache.xpath.internal.operations.Bool;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class IdentificacionValidator implements ConstraintValidator<Identificacion, String> {

    public boolean validarCedula(String cedula) {
        boolean cedulaCorrecta = false;

        try {

            if (cedula.length() == 10) // ConstantesApp.LongitudCedula
            {
                int tercerDigito = Integer.parseInt(cedula.substring(2, 3));
                if (tercerDigito < 6) {

                    int[] coefValCedula = { 2, 1, 2, 1, 2, 1, 2, 1, 2 };
                    int verificador = Integer.parseInt(cedula.substring(9,10));
                    int suma = 0;
                    int digito = 0;
                    for (int i = 0; i < (cedula.length() - 1); i++) {
                        digito = Integer.parseInt(cedula.substring(i, i + 1))* coefValCedula[i];
                        suma += ((digito % 10) + (digito / 10));
                    }

                    if ((suma % 10 == 0) && (suma % 10 == verificador)) {
                        cedulaCorrecta = true;
                    }
                    else if ((10 - (suma % 10)) == verificador) {
                        cedulaCorrecta = true;
                    } else {
                        cedulaCorrecta = false;
                    }
                } else {
                    cedulaCorrecta = false;
                }
            } else {
                cedulaCorrecta = false;
            }
        } catch (NumberFormatException nfe) {
            cedulaCorrecta = false;
        } catch (Exception err) {
           // System.out.println("Una excepcion ocurrio en el proceso de validadcion");
            cedulaCorrecta = false;
        }


        return cedulaCorrecta;
    }

    ValidarIdentificacion validarIdentificacion = new ValidarIdentificacion();


    public boolean validarRuc(String ruc)
    {

        if(ruc.length()!=13)
            return false;

        Boolean   rucNatural = false;
        Boolean rucEmpresa = false;
        Boolean rucEmpresaPublica = false;
        try {
            rucNatural = validarIdentificacion.validarRucPersonaNatural(ruc);
        } catch (Exception e) {
            //e.printStackTrace();
        }

        try {
            rucEmpresa =  validarIdentificacion.validacionRUC(ruc);
        } catch (Exception e) {
            //e.printStackTrace();
        }

        try {
            rucEmpresaPublica = validarIdentificacion.validaRucEP(ruc);
        } catch (Exception e) {
            //e.printStackTrace();
        }




        return rucNatural || rucEmpresa || rucEmpresaPublica;
    }

    @Override
    public void initialize(Identificacion identificacion) {

    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if(s.equals("9999999999")) return true;
        Boolean cedulaIsValid = validarCedula(s);
        Boolean rucIsValid = validarRuc(s);

        return cedulaIsValid || rucIsValid;
    }
}
