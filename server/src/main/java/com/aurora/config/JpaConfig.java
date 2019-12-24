package com.aurora.config;

import com.aurora.framework.config.EclipseLinkJpaConfig;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.autoconfigure.transaction.TransactionManagerCustomizers;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.jta.JtaTransactionManager;

import javax.sql.DataSource;

@Configuration
@EntityScan(value ={"com.aurora.pos.entidades","com.aurora.inventario.entidades","com.aurora.seguridad.entidades",
        "com.aurora.impuestos.entidades"})
@EnableJpaRepositories(value = {"com.aurora.pos.repositorios",
        "com.aurora.inventario.repositorios","com.aurora.impuestos.repositorios"}  )
@EnableAutoConfiguration
public class JpaConfig extends EclipseLinkJpaConfig{

    @Autowired
    public JpaConfig(DataSource dataSource, JpaProperties properties, ObjectProvider<JtaTransactionManager> jtaTransactionManager, ObjectProvider<TransactionManagerCustomizers> transactionManagerCustomizers) {
        super(dataSource, properties, jtaTransactionManager, transactionManagerCustomizers);
    }


}
