package com.aurora.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationHome;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import java.io.IOException;

@Component
@ConfigurationProperties("pos")
@Getter @Setter
public class ParametrosServer {

    private String urlRecepcionProduccion;
    private String urlRecepcionDesarrollo;
    private String urlAutorizacionProduccion;
    private String urlAutorizacionDesarrollo;
    private String pathReport;
    private String pathUpload;
    private String pathDataBase;
    /*private String pathKey;
    private String pathLogo;
    private String passKey;
   */

    @Autowired
    ServletContext context;

    public ParametrosServer()
    {
        pathReport = RelativePath.getRelativePath()+ "WEB-INF/classes/static/reports/";
        pathUpload = RelativePath.getRelativePath() + "user_data/";
    }



    @PostConstruct
    public void init()
    {
        System.out.println("Path report "+pathReport);
    }
}
