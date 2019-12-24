package com.aurora.pos.servicios;


import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;


@Component
@ConfigurationProperties("emisor")
@Getter @Setter
public class ParametrosEmisor {

   /* private int ambiente;//=1;
    private int numeroContribuyenteEspecial;//=0;
    private boolean obligadoContabilidad;//=false;

    private String razonSocialEmisor;// = "CARLOS ALBERTO MENDOZA MIELES";
    private String nombreComercialEmisor;// = "CARLOS ALBERTO MENDOZA MIELES";
    private String rucEmisor;// = "1309410445001";
    private String direccionMatriz;// = "Bellavista Mz 35 Villa 10";*/

}
