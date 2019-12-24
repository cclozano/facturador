package com.aurora.util;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.junit.Test;

/**
 * @author maniac787@gmail.com
 *
 */
public class ValidarIdentificacion {
	@Test
	public void testCedula(){
		try {
			System.out.println(validarCedula("1717171717"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * @param n&uacutemero de c&eacutedula
	 * @return true si es un documento v&aacutelido
	 * @throws Exception
	 */
	public boolean validarCedula(String numero) throws Exception{   
        try {
        	validarInicial(numero, 10);
            validarCodigoProvincia(numero.substring(0, 2));
            validarTercerDigito(String.valueOf(numero.charAt(2)), TipoDocumento.getTipoCedula());
            algoritmoModulo10(numero, Integer.parseInt(String.valueOf(numero.charAt(9))));
        } catch (Exception e) {
        	e.printStackTrace();
            return false; 
        }

        return true;
    }
	
	/**
	 * @param numero de ruc persona natural
	 * @return true si es un documento v&aacutelido
	 * @throws Exception
	 */
	public boolean validarRucPersonaNatural(String numero) throws Exception{
        try {
            validarInicial(numero, 13);
            validarCodigoProvincia(numero.substring(0, 2));
            validarTercerDigito(String.valueOf(numero.charAt(2)), TipoDocumento.getTipoRucNatural());
            validarCodigoEstablecimiento(numero.substring(10, 13));
            algoritmoModulo10(numero.substring(0, 9), Integer.parseInt(String.valueOf(numero.charAt(9))));
        } catch (Exception e) {
            //e.printStackTrace();
            return false; 
        }

        return true;
    }
	
	/**
	 * 
	 * @param numero ruc empresa privada
	 * @return
	 * @throws Exception
	 */
	public boolean validarRucSociedadPrivada(String numero) throws Exception{
		
        // validaciones
        try {
            validarInicial(numero, 13);
            validarCodigoProvincia(numero.substring(0, 2));
            validarTercerDigito(String.valueOf(numero.charAt(2)), TipoDocumento.getRucPrivada());
            validarCodigoEstablecimiento(numero.substring(10, 13));
            algoritmoModulo11(numero.substring(0, 9), Integer.parseInt(String.valueOf(numero.charAt(9))), TipoDocumento.getRucPrivada());
        } catch (Exception e) {
            return false; 
        }

        return true;
    }
	
	/**
	 * @param numero
	 * @param caracteres
	 * @return
	 * @throws Exception
	 */
	protected boolean validarInicial(String numero, int caracteres) throws Exception {   
        if (StringUtils.isEmpty(numero)) {
            throw new Exception("Valor no puede estar vacio");
        }
        
        if (! NumberUtils.isDigits(numero)) {
            throw new Exception("Valor ingresado solo puede tener dígitos");
        }

        if (numero.length() != caracteres) {
            throw new Exception("Valor ingresado debe tener "+ caracteres + " caracteres");
        }

        return true;
    }
	
	/**
	 * @param n&uacutemero en el rango de n&uacutemeros de provincias del ecuador 
	 * @return
	 * @throws Exception
	 */
	protected boolean validarCodigoProvincia(String numero) throws Exception{
        if (Integer.parseInt(numero) < 0 || Integer.parseInt(numero) > 24) {
            throw new Exception("Codigo de Provincia (dos primeros dígitos) no deben ser mayor a 24 ni menores a 0");
        }
        
        return true;
    }
	
	/**
	 * @param numero
	 * @param tipo de documento cedula, ruc
	 * @return
	 * @throws Exception
	 */
	protected boolean validarTercerDigito(String numero, Integer tipo) throws Exception{
        switch (tipo) {
            case 1:
            case 2:
            	
                if (Integer.parseInt(numero) < 0 || Integer.parseInt(numero) > 5) {
                    throw new Exception("Tercer dígito debe ser mayor o igual a 0 y menor a 6 para cédulas y RUC de persona natural ... permitidos de 0 a 5");
                }
                break;
            case 3:
                if (Integer.parseInt(numero) != 9) {
                    throw new Exception("Tercer dígito debe ser igual a 9 para sociedades privadas");
                }
                break;

            case 4:
                if (Integer.parseInt(numero) != 6) {
                    throw new Exception("Tercer dígito debe ser igual a 6 para sociedades públicas");
                }
                break;
            default:
                throw new Exception("Tipo de Identificacion no existe.");
        }

        return true;
    }
	
	/**
	 * @param digitosIniciales
	 * @param digitoVerificador
	 * @return
	 * @throws Exception
	 */
	protected boolean algoritmoModulo10(String digitosIniciales, int digitoVerificador) throws Exception{
        Integer [] arrayCoeficientes = new Integer[]{2,1,2,1,2,1,2,1,2};
        
        Integer [] digitosInicialesTMP = new Integer[digitosIniciales.length()];
        int indice=0;
        for(char valorPosicion: digitosIniciales.toCharArray()){
        	digitosInicialesTMP[indice] = NumberUtils.createInteger(String.valueOf(valorPosicion));
        	indice++;
        }
        
        int total = 0;
        int key = 0;
        for(Integer valorPosicion: digitosInicialesTMP){
        	if(key<arrayCoeficientes.length){
        		valorPosicion = (digitosInicialesTMP[key] * arrayCoeficientes[key] );
        		
        		if (valorPosicion >= 10) {
                	char[] valorPosicionSplit = String.valueOf(valorPosicion).toCharArray();
                    valorPosicion = (Integer.parseInt(String.valueOf(valorPosicionSplit[0]))) + (Integer.parseInt(String.valueOf(valorPosicionSplit[1])));
                    
                }
                total = total + valorPosicion;
        	}
            
            key++;
        }
        int residuo =  total % 10;
        int resultado ;

        if (residuo == 0) {
            resultado = 0;        
        } else {
            resultado = 10 - residuo;
        }

        if (resultado != digitoVerificador) {
            throw new Exception("Dígitos iniciales no validan contra Dígito Idenficador");
        }

        return true;
    }
	
	/**
	 * @param numero
	 * @return
	 * @throws Exception
	 */
	protected boolean validarCodigoEstablecimiento(String numero) throws Exception{
        if (Integer.parseInt(numero) < 1) {
            throw new Exception("Código de establecimiento no puede ser 0");
        }
        return true;
    }
	
	/**
	 * @param digitosIniciales
	 * @param digitoVerificador
	 * @param tipo
	 * @return
	 * @throws Exception
	 */
	protected boolean algoritmoModulo11(String digitosIniciales, int digitoVerificador, Integer tipo) throws Exception{
		Integer [] arrayCoeficientes = null;
		
        switch (tipo) {
        	
            case 3:
                arrayCoeficientes = new Integer[]{4, 3, 2, 7, 6, 5, 4, 3, 2};
                break;
            case 4:
            	arrayCoeficientes = new Integer[]{3, 2, 7, 6, 5, 4, 3, 2};
                break;
            default:
                throw new Exception("Tipo de Identificacion no existe.");
        }

        Integer [] digitosInicialesTMP = new Integer[digitosIniciales.length()];
        int indice=0;
        for(char valorPosicion: digitosIniciales.toCharArray()){
        	digitosInicialesTMP[indice] = NumberUtils.createInteger(String.valueOf(valorPosicion));
        	indice++;
        }
        
        int total = 0;
        int key = 0;
        for(Integer valorPosicion: digitosInicialesTMP){
        	if(key<arrayCoeficientes.length){
        		valorPosicion = (digitosInicialesTMP[key] * arrayCoeficientes[key] );
        		
        		if (valorPosicion >= 10) {
                	char[] valorPosicionSplit = String.valueOf(valorPosicion).toCharArray();
                    valorPosicion = (Integer.parseInt(String.valueOf(valorPosicionSplit[0]))) + (Integer.parseInt(String.valueOf(valorPosicionSplit[1])));
                    System.out.println(valorPosicion);
                }
                total = total + valorPosicion;
        	}
            
            key++;
        }
        
        int residuo =  total % 11;
        int resultado;
        
        if (residuo == 0) {
            resultado = 0;        
        } else {
            resultado = (11 - residuo);
        }

        if (resultado != digitoVerificador) {
            throw new Exception("Dígitos iniciales no validan contra Dígito Idenficador");
        }

        return true;
    }


    public static final int NUM_PROVINCIAS = 24;

    public  Boolean validaRucEP(String ruc) {
        final int prov = Integer.parseInt(ruc.substring(0, 2));
        boolean resp = false;

        if (!((prov > 0) && (prov <= NUM_PROVINCIAS))) {
            resp = false;
        }

        // boolean val = false;
        Integer v1, v2, v3, v4, v5, v6, v7, v8, v9;
        Integer sumatoria;
        Integer modulo;
        Integer digito;
        int[] d = new int[ruc.length()];

        for (int i = 0; i < d.length; i++) {
            d[i] = Integer.parseInt(ruc.charAt(i) + "");
        }

        v1 = d[0] * 3;
        v2 = d[1] * 2;
        v3 = d[2] * 7;
        v4 = d[3] * 6;
        v5 = d[4] * 5;
        v6 = d[5] * 4;
        v7 = d[6] * 3;
        v8 = d[7] * 2;
        v9 = d[8];

        sumatoria = v1 + v2 + v3 + v4 + v5 + v6 + v7 + v8;
        modulo = sumatoria % 11;
        if (modulo == 0) {
            return true;
        }
        digito = 11 - modulo;

        if (digito.equals(v9)) {
            resp = true;
        } else {
            resp = false;
        }
        return resp;
    }


    private static int[] coeficientes = { 4, 3, 2, 7, 6, 5, 4, 3, 2 };
    private static int constante = 11;

    public  Boolean validacionRUC(String ruc) {
        boolean resp_dato = false;
        final int prov = Integer.parseInt(ruc.substring(0, 2));
        if (!((prov > 0) && (prov <= NUM_PROVINCIAS))) {
            resp_dato = false;
        }

        int[] d = new int[10];
        int suma = 0;

        for (int i = 0; i < d.length; i++) {
            d[i] = Integer.parseInt(ruc.charAt(i) + "");
        }

        for (int i = 0; i < d.length - 1; i++) {
            d[i] = d[i] * coeficientes[i];
            suma += d[i];
        }

        int aux, resp;

        aux = suma % constante;
        resp = constante - aux;

        resp = (aux == 0) ? 0 : resp;

        if (resp == d[9]) {
            resp_dato = true;
        } else {
            resp_dato = false;
        }
        return resp_dato;
    }


    public  Boolean validacionRucPersonaNatural(String ruc) {
        boolean isValid = false;
        if (ruc == null || ruc.length() != 10) {
            isValid = false;
        }
        final int prov = Integer.parseInt(ruc.substring(0, 2));

        if (!((prov > 0) && (prov <= NUM_PROVINCIAS))) {
            isValid = false;
        }

        int[] d = new int[10];
        for (int i = 0; i < d.length; i++) {
            d[i] = Integer.parseInt(ruc.charAt(i) + "");
        }

        int imp = 0;
        int par = 0;

        for (int i = 0; i < d.length; i += 2) {
            d[i] = ((d[i] * 2) > 9) ? ((d[i] * 2) - 9) : (d[i] * 2);
            imp += d[i];
        }

        for (int i = 1; i < (d.length - 1); i += 2) {
            par += d[i];
        }

        final int suma = imp + par;

        int d10 = Integer.parseInt(String.valueOf(suma + 10).substring(0, 1)
                + "0")
                - suma;

        d10 = (d10 == 10) ? 0 : d10;

        if (d10 == d[9]) {
            isValid = true;
        } else {
            isValid = false;
        }

        return isValid;
    }



}
