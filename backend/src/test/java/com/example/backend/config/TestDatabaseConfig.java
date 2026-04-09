package com.example.backend.config;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import jakarta.persistence.EntityManagerFactory;

@Configuration
@Profile("test")
public class TestDatabaseConfig {

    @Primary
    @Bean(name = "sqlServer1DataSource")
    public DataSource sqlServer1DataSource() {
        DriverManagerDataSource ds = new DriverManagerDataSource();
        ds.setDriverClassName("org.h2.Driver");
        ds.setUrl("jdbc:h2:mem:testdb1;MODE=MSSQLServer;INIT=CREATE SCHEMA IF NOT EXISTS dbo");
        ds.setUsername("sa");
        ds.setPassword("");
        return ds;
    }

    @Bean(name = "sqlServer2DataSource")
    public DataSource sqlServer2DataSource() {
        DriverManagerDataSource ds = new DriverManagerDataSource();
        ds.setDriverClassName("org.h2.Driver");
        ds.setUrl("jdbc:h2:mem:testdb2;MODE=MSSQLServer;INIT=CREATE SCHEMA IF NOT EXISTS dbo");
        ds.setUsername("sa");
        ds.setPassword("");
        return ds;
    }

    @Primary
    @Bean(name = "sqlServer1EntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean sqlServer1EntityManagerFactory(
            EntityManagerFactoryBuilder builder,
            @Qualifier("sqlServer1DataSource") DataSource dataSource) {
        Map<String, Object> properties = new HashMap<>();
        properties.put("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
        properties.put("hibernate.hbm2ddl.auto", "create-drop");
        properties.put("hibernate.default_schema", "dbo");
        return builder
                .dataSource(dataSource)
                .packages("com.example.backend.sqlserver1.model")
                .persistenceUnit("sqlserver1")
                .properties(properties)
                .build();
    }

    @Bean(name = "sqlServer2EntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean sqlServer2EntityManagerFactory(
            EntityManagerFactoryBuilder builder,
            @Qualifier("sqlServer2DataSource") DataSource dataSource) {
        Map<String, Object> properties = new HashMap<>();
        properties.put("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
        properties.put("hibernate.hbm2ddl.auto", "create-drop");
        properties.put("hibernate.default_schema", "dbo");
        return builder
                .dataSource(dataSource)
                .packages("com.example.backend.sqlserver2.model")
                .persistenceUnit("sqlserver2")
                .properties(properties)
                .build();
    }

    @Primary
    @Bean(name = "sqlServer1TransactionManager")
    public PlatformTransactionManager sqlServer1TransactionManager(
            @Qualifier("sqlServer1EntityManagerFactory") EntityManagerFactory emf) {
        return new JpaTransactionManager(emf);
    }

    @Bean(name = "sqlServer2TransactionManager")
    public PlatformTransactionManager sqlServer2TransactionManager(
            @Qualifier("sqlServer2EntityManagerFactory") EntityManagerFactory emf) {
        return new JpaTransactionManager(emf);
    }
}