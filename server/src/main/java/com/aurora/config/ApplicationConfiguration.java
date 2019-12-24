package com.aurora.config;

import com.aurora.pos.entidades.Emisor;
import com.aurora.pos.servicios.ServicioEmisor;
import com.vaadin.spring.access.SecuredViewAccessControl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;

@Configuration
public class ApplicationConfiguration {


	@Autowired
	private ServicioEmisor servicioEmisor;


	/**
	 * The password encoder to use when encrypting passwords.
	 */
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	SecuredViewAccessControl securedViewAccessControl() {
		return new SecuredViewAccessControl();
	}



}
