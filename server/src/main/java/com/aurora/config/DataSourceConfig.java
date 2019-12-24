package com.aurora.config;

import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

//@Configuration
public class DataSourceConfig {
   /* @Bean
    @Primary
    public DataSource dataSource() {
        //String path = DataSourceConfig.class.getResource("").getPath();
        //String fullPath = path.replace("/WEB-INF/classes/com/aurora/config/", "/user_data/data_base");
        String path = RelativePath.getRelativePath() + "user_data/data_base";

        return DataSourceBuilder
                .create()
                .username("sa")
                .password("")
                .url("jdbc:hsqldb:file:" + path)
                .driverClassName("org.hsqldb.jdbc.JDBCDriver")
                .build();
    }*/
}
