package com.aurora.pos.entidades;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.Size;

@Embeddable
@Getter @Setter
public class CorreoEmisorConfig {
    @Basic
    @Column(name = "mail_host")
    @Size(min = 4,message = "Ingrese Host")
    private String host;

    @Basic
    @Column(name = "mail_port")

    private int  port;

    @Basic
    @Column(name = "mail_username")
    @Size(min = 4,message = "Ingrese usuario de COrreo")
    private String username;

    @Basic
    @Column(name = "mail_password")
    @Size(min = 4,message = "Ingrese Clave de correo")
    private String password;

    @Basic
    @Column(name = "mail_auth")
    private boolean auth;

    private String protocol = "smtp";

    @Basic
    @Column(name = "mail_starttls_enable")
    private boolean starttlsEnable;


    private String asuntoCorreo = "Comprobante Electronico Emitido";

    private String cuerpoCorreo = "Adjunto comprobante electronico";



}
