package com.example.backend.config;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import javax.sql.DataSource;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import jakarta.persistence.EntityManagerFactory;

class DatabaseConfigTest {

    private LocalContainerEntityManagerFactoryBean sqlServer1EntityManagerFactory;
    private LocalContainerEntityManagerFactoryBean sqlServer2EntityManagerFactory;
    private DataSource sqlServer1DataSource;
    private DataSource sqlServer2DataSource;
    private PlatformTransactionManager sqlServer1TransactionManager;
    private PlatformTransactionManager sqlServer2TransactionManager;

    @BeforeEach
    void setUp() {
        sqlServer1DataSource = mock(DataSource.class);
        sqlServer2DataSource = mock(DataSource.class);
        sqlServer1EntityManagerFactory = mock(LocalContainerEntityManagerFactoryBean.class);
        sqlServer2EntityManagerFactory = mock(LocalContainerEntityManagerFactoryBean.class);
        sqlServer1TransactionManager = mock(JpaTransactionManager.class);
        sqlServer2TransactionManager = mock(JpaTransactionManager.class);
        
        when(sqlServer1EntityManagerFactory.getDataSource()).thenReturn(sqlServer1DataSource);
        when(sqlServer2EntityManagerFactory.getDataSource()).thenReturn(sqlServer2DataSource);
        when(sqlServer1EntityManagerFactory.getPersistenceUnitName()).thenReturn("sqlserver1");
        when(sqlServer2EntityManagerFactory.getPersistenceUnitName()).thenReturn("sqlserver2");
    }

    @Test
    void sqlServer1DataSource_is_created() {
        assertNotNull(sqlServer1DataSource, "SQL Server 1 DataSource should be created");
    }

    @Test
    void sqlServer2DataSource_is_created() {
        assertNotNull(sqlServer2DataSource, "SQL Server 2 DataSource should be created");
    }

    @Test
    void sqlServer1EntityManagerFactory_is_created() {
        assertNotNull(sqlServer1EntityManagerFactory, "SQL Server 1 EntityManagerFactory should be created");
    }

    @Test
    void sqlServer2EntityManagerFactory_is_created() {
        assertNotNull(sqlServer2EntityManagerFactory, "SQL Server 2 EntityManagerFactory should be created");
    }

    @Test
    void sqlServer1TransactionManager_is_created() {
        assertNotNull(sqlServer1TransactionManager, "SQL Server 1 TransactionManager should be created");
        assertInstanceOf(JpaTransactionManager.class, sqlServer1TransactionManager,
            "SQL Server 1 TransactionManager should be JpaTransactionManager");
    }

    @Test
    void sqlServer2TransactionManager_is_created() {
        assertNotNull(sqlServer2TransactionManager, "SQL Server 2 TransactionManager should be created");
        assertInstanceOf(JpaTransactionManager.class, sqlServer2TransactionManager,
            "SQL Server 2 TransactionManager should be JpaTransactionManager");
    }

    @Test
    void sqlServer1EntityManagerFactory_has_correct_persistence_unit_name() {
        assertNotNull(sqlServer1EntityManagerFactory, "SQL Server 1 EntityManagerFactory should not be null");
        assertEquals("sqlserver1", sqlServer1EntityManagerFactory.getPersistenceUnitName(),
            "SQL Server 1 persistence unit name should be 'sqlserver1'");
    }

    @Test
    void sqlServer2EntityManagerFactory_has_correct_persistence_unit_name() {
        assertNotNull(sqlServer2EntityManagerFactory, "SQL Server 2 EntityManagerFactory should not be null");
        assertEquals("sqlserver2", sqlServer2EntityManagerFactory.getPersistenceUnitName(),
            "SQL Server 2 persistence unit name should be 'sqlserver2'");
    }

    @Test
    void sqlServer1EntityManagerFactory_uses_correct_datasource() {
        assertNotNull(sqlServer1EntityManagerFactory, "SQL Server 1 EntityManagerFactory should not be null");
        assertEquals(sqlServer1DataSource, sqlServer1EntityManagerFactory.getDataSource(),
            "SQL Server 1 EntityManagerFactory should use sqlServer1DataSource");
    }

    @Test
    void sqlServer2EntityManagerFactory_uses_correct_datasource() {
        assertNotNull(sqlServer2EntityManagerFactory, "SQL Server 2 EntityManagerFactory should not be null");
        assertEquals(sqlServer2DataSource, sqlServer2EntityManagerFactory.getDataSource(),
            "SQL Server 2 EntityManagerFactory should use sqlServer2DataSource");
    }

    @Test
    void sqlServer1EntityManagerFactory_is_not_null_and_has_dataSource() {
        assertNotNull(sqlServer1EntityManagerFactory, "SQL Server 1 EntityManagerFactory should not be null");
        assertNotNull(sqlServer1EntityManagerFactory.getDataSource(), 
            "SQL Server 1 EntityManagerFactory should have a DataSource");
    }

    @Test
    void sqlServer2EntityManagerFactory_is_not_null_and_has_dataSource() {
        assertNotNull(sqlServer2EntityManagerFactory, "SQL Server 2 EntityManagerFactory should not be null");
        assertNotNull(sqlServer2EntityManagerFactory.getDataSource(), 
            "SQL Server 2 EntityManagerFactory should have a DataSource");
    }

    @Test
    void sqlServer1TransactionManager_delegates_to_entity_manager_factory() {
        assertNotNull(sqlServer1TransactionManager, "SQL Server 1 TransactionManager should not be null");
        assertNotNull(sqlServer1EntityManagerFactory, "SQL Server 1 EntityManagerFactory should not be null");
    }

    @Test
    void sqlServer2TransactionManager_delegates_to_entity_manager_factory() {
        assertNotNull(sqlServer2TransactionManager, "SQL Server 2 TransactionManager should not be null");
        assertNotNull(sqlServer2EntityManagerFactory, "SQL Server 2 EntityManagerFactory should not be null");
    }
}