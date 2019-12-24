package com.aurora.pos.servicios;

import java.text.SimpleDateFormat;
import java.util.Date;

public class GeneradorClaves {

    private Modulo11 modulo11 = new Modulo11();

    public String getClaveAcceso(String rucEmsisor, int ambiente,String codigoEstablecimiento, String codigoPuntoEmision,
                                String secuencial, Date fechaEmision, String codigoDocumento){

        /*if(ambiente!=1&&ambiente!=2)
        {
            throw new Exception("ambiente solo puede ser 1 o 2");
        }*/
        SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy");
        StringBuilder sp = new StringBuilder();
        sp.append(sdf.format(fechaEmision));
        sp.append(codigoDocumento);
        sp.append(rucEmsisor);
        sp.append(String.valueOf(ambiente));
        sp.append(codigoEstablecimiento + codigoPuntoEmision);
        sp.append(secuencial);
        String numero = secuencial.substring(1);
        sp.append(numero);
        sp.append(1);
        String x= sp.toString();
        int digito = modulo11.obtenerSumaPorDigitos(modulo11.invertirCadena(x));
        sp.append(  digito>=10?1     :digito     );
        return sp.toString();
    }
}
