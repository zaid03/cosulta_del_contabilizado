package com.example.backend.config;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import jakarta.persistence.EntityManagerFactory;

@Configuration
@EnableTransactionManagement
@Profile("!test")
public class DatabaseConfig {

    // SQL Server DataSource 1 (Primary)
    @Primary
    @Bean(name = "sqlServer1DataSource")
    @ConfigurationProperties(prefix = "spring.datasource.sqlserver1")
    public DataSource sqlServer1DataSource() {
        return DataSourceBuilder.create().build();
    }

    // SQL Server DataSource 2 (Secondary)
    @Bean(name = "sqlServer2DataSource")
    @ConfigurationProperties(prefix = "spring.datasource.sqlserver2")
    public DataSource sqlServer2DataSource() {
        return DataSourceBuilder.create().build();
    }

    // SQL Server 1 EntityManagerFactory
    @Primary
    @Bean(name = "sqlServer1EntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean sqlServer1EntityManagerFactory(
            EntityManagerFactoryBuilder builder,
            @Qualifier("sqlServer1DataSource") DataSource dataSource) {
        
        Map<String, Object> properties = new HashMap<>();
        properties.put("hibernate.dialect", "org.hibernate.dialect.SQLServerDialect");
        properties.put("hibernate.hbm2ddl.auto", "validate");
        properties.put("hibernate.show_sql", true);
        
        return builder
                .dataSource(dataSource)
                .packages("com.example.backend.sqlserver1.model") 
                .persistenceUnit("sqlserver1")
                .properties(properties)
                .build();
    }

    // SQL Server 2 EntityManagerFactory
    @Bean(name = "sqlServer2EntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean sqlServer2EntityManagerFactory(
            EntityManagerFactoryBuilder builder,
            @Qualifier("sqlServer2DataSource") DataSource dataSource) {
        
        Map<String, Object> properties = new HashMap<>();
        properties.put("hibernate.dialect", "org.hibernate.dialect.SQLServerDialect");
        properties.put("hibernate.hbm2ddl.auto", "validate");
        properties.put("hibernate.show_sql", true);
        
        return builder
                .dataSource(dataSource)
                .packages("com.example.backend.sqlserver2.model")
                .persistenceUnit("sqlserver2")
                .properties(properties)
                .build();
    }

    // SQL Server 1 Transaction Manager
    @Primary
    @Bean(name = "sqlServer1TransactionManager")
    public PlatformTransactionManager sqlServer1TransactionManager(
            @Qualifier("sqlServer1EntityManagerFactory") EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }

    // SQL Server 2 Transaction Manager
    @Bean(name = "sqlServer2TransactionManager")
    public PlatformTransactionManager sqlServer2TransactionManager(
            @Qualifier("sqlServer2EntityManagerFactory") EntityManagerFactory entityManagerFactory) {
                
        return new JpaTransactionManager(entityManagerFactory);
    }
}

// SQL Server 1 Repository Configuration
@Configuration
@EnableJpaRepositories(
        basePackages = "com.example.backend.sqlserver1.repository",
        entityManagerFactoryRef = "sqlServer1EntityManagerFactory",
        transactionManagerRef = "sqlServer1TransactionManager"
)
class SQLServer1RepositoryConfig {
}

// SQL Server 2 Repository Configuration
@Configuration
@EnableJpaRepositories(
        basePackages = "com.example.backend.sqlserver2.repository",
        entityManagerFactoryRef = "sqlServer2EntityManagerFactory",
        transactionManagerRef = "sqlServer2TransactionManager"
)
class SQLServer2RepositoryConfig {
}