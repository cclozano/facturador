package com.aurora.pos.servicios;

/**
 * Created by max on 7/11/16.
 */


public class Modulo11 {

    public String invertirCadena(String cadena) {
        String cadenaInvertida = "";
        for (int x = cadena.length() - 1; x >= 0; x--) {
            cadenaInvertida = cadenaInvertida + cadena.charAt(x);
        }
        return cadenaInvertida;
    }

    public int obtenerSumaPorDigitos(String cadena) {
        int pivote = 2;
        int longitudCadena = cadena.length();
        int cantidadTotal = 0;
        int b = 1;
        for (int i = 0; i < longitudCadena; i++) {
            if (pivote == 8) {
                pivote = 2;
            }
            int temporal = Integer.parseInt("" + cadena.substring(i, b));
            b++;
            temporal *= pivote;
            pivote++;
            cantidadTotal += temporal;
        }
        cantidadTotal = 11 - cantidadTotal % 11;

        if(cantidadTotal==10)
        {
            return 1;
        }
        if(cantidadTotal == 11)
        {
            return 0;
        }

        return cantidadTotal;
    }

   /* public static void main(String args[]) throws Exception {
        Modulo11 a = new Modulo11();
        System.out.println(a.obtenerSumaPorDigitos(a.invertirCadena("080520160109914186440012002005000006124090903291")));
    }*/
}