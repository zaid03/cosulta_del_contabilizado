package com.example.backend.controller;

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.example.backend.sqlserver2.repository.FdeRepository;
import com.example.backend.dto.FdeFacTerProjection;
import com.example.backend.service.ContabilizadoSearch;

class FdeControllerTest {

    private FdeController fdeController;
    private FdeRepository fdeRepository;
    private ContabilizadoSearch contabilizadoSearch;

    private FdeFacTerProjection mockFactura1;
    private FdeFacTerProjection mockFactura2;
    private FdeFacTerProjection mockFacturaWithoutValues;

    @BeforeEach
    void setUp() {
        fdeRepository = mock(FdeRepository.class);
        contabilizadoSearch = mock(ContabilizadoSearch.class);
        fdeController = new FdeController();
        
        injectField(fdeController, "fdeRepository", fdeRepository);
        injectField(fdeController, "contabilizadoSearch", contabilizadoSearch);

        mockFactura1 = mock(FdeFacTerProjection.class);
        when(mockFactura1.getFDEIMP()).thenReturn(1000.00);
        when(mockFactura1.getFDEDIF()).thenReturn(0.0);

        mockFactura2 = mock(FdeFacTerProjection.class);
        when(mockFactura2.getFDEIMP()).thenReturn(0.0);
        when(mockFactura2.getFDEDIF()).thenReturn(500.00);

        mockFacturaWithoutValues = mock(FdeFacTerProjection.class);
        when(mockFacturaWithoutValues.getFDEIMP()).thenReturn(null);
        when(mockFacturaWithoutValues.getFDEDIF()).thenReturn(null);
    }

    /**
     * Helper method to inject fields using reflection for Autowired
     */
    private void injectField(Object target, String fieldName, Object value) {
        try {
            java.lang.reflect.Field field = target.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(target, value);
        } catch (Exception e) {
            throw new RuntimeException("Failed to inject field: " + fieldName, e);
        }
    }

    @Test
    void testFetchContabilizado_Success_WithFacturas() {
        Integer ent = 1;
        String eje = "12345";
        List<FdeFacTerProjection> mockFacturas = Arrays.asList(mockFactura1, mockFactura2);

        when(fdeRepository.findByENTAndEJEAndFac_FACFCOIsNotNull(ent, eje))
            .thenReturn(mockFacturas);

        ResponseEntity<?> response = fdeController.fetchContabilizado(ent, eje);

        assertEquals(HttpStatus.OK, response.getStatusCode(), "Should return 200 OK");
        assertNotNull(response.getBody(), "Response body should not be null");
        assertTrue(response.getBody() instanceof List, "Response body should be a List");
        assertEquals(2, ((List<?>) response.getBody()).size(), "Should return 2 facturas");
        verify(fdeRepository, times(1)).findByENTAndEJEAndFac_FACFCOIsNotNull(ent, eje);
    }

    /**
     * Test fetch with empty results after filtering.
     * When repository returns facturas but all are filtered out (no FDEIMP or FDEDIF > 0),
     * should return HTTP 404 with "Sin resultado"
     */
    @Test
    void testFetchContabilizado_NotFound_EmptyAfterFilter() {
        Integer ent = 1;
        String eje = "12345";
        List<FdeFacTerProjection> mockFacturas = Arrays.asList(mockFacturaWithoutValues);

        when(fdeRepository.findByENTAndEJEAndFac_FACFCOIsNotNull(ent, eje))
            .thenReturn(mockFacturas);

        ResponseEntity<?> response = fdeController.fetchContabilizado(ent, eje);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode(), "Should return 404 NOT_FOUND");
        assertEquals("Sin resultado", response.getBody(), "Should return 'Sin resultado' message");
        verify(fdeRepository, times(1)).findByENTAndEJEAndFac_FACFCOIsNotNull(ent, eje);
    }

    /**
     * Test fetch with no results from repository.
     * Should return HTTP 404 with "Sin resultado"
     */
    @Test
    void testFetchContabilizado_NotFound_EmptyList() {
        Integer ent = 1;
        String eje = "12345";

        when(fdeRepository.findByENTAndEJEAndFac_FACFCOIsNotNull(ent, eje))
            .thenReturn(Collections.emptyList());

        ResponseEntity<?> response = fdeController.fetchContabilizado(ent, eje);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode(), "Should return 404 NOT_FOUND");
        assertEquals("Sin resultado", response.getBody(), "Should return 'Sin resultado' message");
        verify(fdeRepository, times(1)).findByENTAndEJEAndFac_FACFCOIsNotNull(ent, eje);
    }

    /**
     * Test fetch with DataAccessException.
     * Should return HTTP 400 with "Error :" + most specific cause message
     */
    @Test
    void testFetchContabilizado_DataAccessException() {
        Integer ent = 1;
        String eje = "12345";
        String errorMessage = "Database connection failed";

        when(fdeRepository.findByENTAndEJEAndFac_FACFCOIsNotNull(ent, eje))
            .thenThrow(new DataAccessException(errorMessage) {
                @Override
                public Throwable getMostSpecificCause() {
                    return new Throwable(errorMessage);
                }
            });

        ResponseEntity<?> response = fdeController.fetchContabilizado(ent, eje);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode(), "Should return 400 BAD_REQUEST");
        assertNotNull(response.getBody(), "Response body should not be null");
        assertTrue(response.getBody().toString().contains("Error :"), "Should contain 'Error :' message");
        assertTrue(response.getBody().toString().contains(errorMessage), "Should contain error message");
        verify(fdeRepository, times(1)).findByENTAndEJEAndFac_FACFCOIsNotNull(ent, eje);
    }

    /**
     * Test fetch with generic Exception (non-DataAccess).
     * Should return HTTP 400 with "Error :" + exception message
     */
    @Test
    void testFetchContabilizado_GenericException() {
        Integer ent = 1;
        String eje = "12345";
        String errorMessage = "Unexpected error occurred";

        when(fdeRepository.findByENTAndEJEAndFac_FACFCOIsNotNull(ent, eje))
            .thenThrow(new RuntimeException(errorMessage));

        ResponseEntity<?> response = fdeController.fetchContabilizado(ent, eje);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode(), "Should return 400 BAD_REQUEST");
        assertNotNull(response.getBody(), "Response body should not be null");
        assertTrue(response.getBody().toString().contains("Error :"), "Should contain 'Error :' message");
        assertTrue(response.getBody().toString().contains(errorMessage), "Should contain error message");
        verify(fdeRepository, times(1)).findByENTAndEJEAndFac_FACFCOIsNotNull(ent, eje);
    }

    /**
     * Test fetch filter logic - FDEIMP > 0 should pass filter
     */
    @Test
    void testFetchContabilizado_Filter_FDEIMPGreaterThanZero() {
        Integer ent = 1;
        String eje = "12345";

        FdeFacTerProjection factura = mock(FdeFacTerProjection.class);
        when(factura.getFDEIMP()).thenReturn(0.01);
        when(factura.getFDEDIF()).thenReturn(null);

        List<FdeFacTerProjection> mockFacturas = Arrays.asList(factura);

        when(fdeRepository.findByENTAndEJEAndFac_FACFCOIsNotNull(ent, eje))
            .thenReturn(mockFacturas);

        ResponseEntity<?> response = fdeController.fetchContabilizado(ent, eje);

        assertEquals(HttpStatus.OK, response.getStatusCode(), "Should return 200 OK");
        assertEquals(1, ((List<?>) response.getBody()).size(), "Should return 1 factura");
    }

    /**
     * Test fetch filter logic - FDEDIF > 0 should pass filter
     */
    @Test
    void testFetchContabilizado_Filter_FDEDIFGreaterThanZero() {
        Integer ent = 2;
        String eje = "67890";

        FdeFacTerProjection factura = mock(FdeFacTerProjection.class);
        when(factura.getFDEIMP()).thenReturn(null);
        when(factura.getFDEDIF()).thenReturn(0.01);

        List<FdeFacTerProjection> mockFacturas = Arrays.asList(factura);

        when(fdeRepository.findByENTAndEJEAndFac_FACFCOIsNotNull(2, "67890"))
            .thenReturn(mockFacturas);

        ResponseEntity<?> response = fdeController.fetchContabilizado(2, "67890");

        assertEquals(HttpStatus.OK, response.getStatusCode(), "Should return 200 OK");
        assertEquals(1, ((List<?>) response.getBody()).size(), "Should return 1 factura");
    }

    /**
     * Test fetch filter logic - zero values should not pass filter
     */
    @Test
    void testFetchContabilizado_Filter_ZeroValuesShouldNotPass() {
        Integer ent = 1;
        String eje = "12345";

        FdeFacTerProjection factura = mock(FdeFacTerProjection.class);
        when(factura.getFDEIMP()).thenReturn(0.0);
        when(factura.getFDEDIF()).thenReturn(0.0);

        List<FdeFacTerProjection> mockFacturas = Arrays.asList(factura);

        when(fdeRepository.findByENTAndEJEAndFac_FACFCOIsNotNull(ent, eje))
            .thenReturn(mockFacturas);

        ResponseEntity<?> response = fdeController.fetchContabilizado(ent, eje);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode(), "Should return 404 NOT_FOUND");
        assertEquals("Sin resultado", response.getBody(), "Should return 'Sin resultado' message");
    }

    /**
     * Test fetch with mixed valid and invalid facturas - only valid ones should be returned
     */
    @Test
    void testFetchContabilizado_FilterMixed() {
        Integer ent = 1;
        String eje = "12345";

        FdeFacTerProjection validFactura = mock(FdeFacTerProjection.class);
        when(validFactura.getFDEIMP()).thenReturn(1000.0);
        when(validFactura.getFDEDIF()).thenReturn(null);

        FdeFacTerProjection invalidFactura = mock(FdeFacTerProjection.class);
        when(invalidFactura.getFDEIMP()).thenReturn(0.0);
        when(invalidFactura.getFDEDIF()).thenReturn(0.0);

        List<FdeFacTerProjection> mockFacturas = Arrays.asList(validFactura, invalidFactura);

        when(fdeRepository.findByENTAndEJEAndFac_FACFCOIsNotNull(ent, eje))
            .thenReturn(mockFacturas);

        ResponseEntity<?> response = fdeController.fetchContabilizado(ent, eje);

        assertEquals(HttpStatus.OK, response.getStatusCode(), "Should return 200 OK");
        assertEquals(1, ((List<?>) response.getBody()).size(), "Should return 1 factura after filtering");
    }

    @Test
    void testSearchContabilizado_Success_RequiredParamsOnly() {
        Integer ent = 1;
        String eje = "12345";

        List<FdeFacTerProjection> mockFacturas = Arrays.asList(mockFactura1);

        when(contabilizadoSearch.searchContabilizado(ent, eje, null, null, null, null))
            .thenReturn(mockFacturas);

        ResponseEntity<?> response = fdeController.searchContabilizado(ent, eje, null, null, null, null);

        assertEquals(HttpStatus.OK, response.getStatusCode(), "Should return 200 OK");
        assertNotNull(response.getBody(), "Response body should not be null");
        assertTrue(response.getBody() instanceof List, "Response body should be a List");
        assertEquals(1, ((List<?>) response.getBody()).size(), "Should return 1 factura");
        verify(contabilizadoSearch, times(1))
            .searchContabilizado(ent, eje, null, null, null, null);
    }

    /**
     * Test search with all optional parameters provided.
     * Should pass all parameters to the service
     */
    @Test
    void testSearchContabilizado_Success_AllParameters() {
        Integer ent = 1;
        String eje = "12345";
        String proveedor = "ACME Corp";
        String centroGestor = "CG001";
        String economica = "ECON001";
        Integer ano = 2024;

        List<FdeFacTerProjection> mockFacturas = Arrays.asList(mockFactura1, mockFactura2);

        when(contabilizadoSearch.searchContabilizado(ent, eje, proveedor, centroGestor, economica, ano))
            .thenReturn(mockFacturas);

        ResponseEntity<?> response = fdeController.searchContabilizado(ent, eje, proveedor, centroGestor, economica, ano);

        assertEquals(HttpStatus.OK, response.getStatusCode(), "Should return 200 OK");
        assertEquals(2, ((List<?>) response.getBody()).size(), "Should return 2 facturas");
        verify(contabilizadoSearch, times(1))
            .searchContabilizado(ent, eje, proveedor, centroGestor, economica, ano);
    }

    /**
     * Test search with some optional parameters.
     * Should pass provided optional params and null for others
     */
    @Test
    void testSearchContabilizado_Success_SomeOptionalParameters() {
        Integer ent = 1;
        String eje = "12345";
        String proveedor = "ACME Corp";
        Integer ano = 2024;

        List<FdeFacTerProjection> mockFacturas = Arrays.asList(mockFactura1);

        when(contabilizadoSearch.searchContabilizado(ent, eje, proveedor, null, null, ano))
            .thenReturn(mockFacturas);

        ResponseEntity<?> response = fdeController.searchContabilizado(ent, eje, proveedor, null, null, ano);

        assertEquals(HttpStatus.OK, response.getStatusCode(), "Should return 200 OK");
        assertEquals(1, ((List<?>) response.getBody()).size(), "Should return 1 factura");
        verify(contabilizadoSearch, times(1))
            .searchContabilizado(ent, eje, proveedor, null, null, ano);
    }

    /**
     * Test search with empty result.
     * Should return HTTP 404 with "Sin resultado"
     */
    @Test
    void testSearchContabilizado_NotFound_EmptyResult() {
        Integer ent = 1;
        String eje = "99999";

        when(contabilizadoSearch.searchContabilizado(ent, eje, null, null, null, null))
            .thenReturn(Collections.emptyList());

        ResponseEntity<?> response = fdeController.searchContabilizado(ent, eje, null, null, null, null);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode(), "Should return 404 NOT_FOUND");
        assertEquals("Sin resultado", response.getBody(), "Should return 'Sin resultado' message");
        verify(contabilizadoSearch, times(1))
            .searchContabilizado(ent, eje, null, null, null, null);
    }

    /**
     * Test search with DataAccessException.
     * Should return HTTP 400 with "Error :" + most specific cause message
     */
    @Test
    void testSearchContabilizado_DataAccessException() {
        Integer ent = 1;
        String eje = "12345";
        String errorMessage = "Database connection timeout";

        when(contabilizadoSearch.searchContabilizado(ent, eje, null, null, null, null))
            .thenThrow(new DataAccessException(errorMessage) {
                @Override
                public Throwable getMostSpecificCause() {
                    return new Throwable(errorMessage);
                }
            });

        ResponseEntity<?> response = fdeController.searchContabilizado(ent, eje, null, null, null, null);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode(), "Should return 400 BAD_REQUEST");
        assertTrue(response.getBody().toString().contains("Error :"), "Should contain 'Error :' message");
        assertTrue(response.getBody().toString().contains(errorMessage), "Should contain error message");
        verify(contabilizadoSearch, times(1))
            .searchContabilizado(ent, eje, null, null, null, null);
    }

    /**
     * Test search with proveedor parameter only.
     * Should pass proveedor and null for other optional params
     */
    @Test
    void testSearchContabilizado_WithProveedorOnly() {
        Integer ent = 1;
        String eje = "12345";
        String proveedor = "Supplier Inc";

        FdeFacTerProjection factura = mock(FdeFacTerProjection.class);
        when(factura.getFDEIMP()).thenReturn(500.0);
        List<FdeFacTerProjection> mockFacturas = Arrays.asList(factura);

        when(contabilizadoSearch.searchContabilizado(ent, eje, proveedor, null, null, null))
            .thenReturn(mockFacturas);

        ResponseEntity<?> response = fdeController.searchContabilizado(ent, eje, proveedor, null, null, null);

        assertEquals(HttpStatus.OK, response.getStatusCode(), "Should return 200 OK");
        assertEquals(1, ((List<?>) response.getBody()).size(), "Should return 1 factura");
        verify(contabilizadoSearch, times(1))
            .searchContabilizado(ent, eje, proveedor, null, null, null);
    }

    /**
     * Test search with centroGestor parameter only.
     * Should pass centroGestor and null for other optional params
     */
    @Test
    void testSearchContabilizado_WithCentroGestorOnly() {
        Integer ent = 1;
        String eje = "12345";
        String centroGestor = "CG-2024";

        FdeFacTerProjection factura = mock(FdeFacTerProjection.class);
        List<FdeFacTerProjection> mockFacturas = Arrays.asList(factura);

        when(contabilizadoSearch.searchContabilizado(ent, eje, null, centroGestor, null, null))
            .thenReturn(mockFacturas);

        ResponseEntity<?> response = fdeController.searchContabilizado(ent, eje, null, centroGestor, null, null);

        assertEquals(HttpStatus.OK, response.getStatusCode(), "Should return 200 OK");
        verify(contabilizadoSearch, times(1))
            .searchContabilizado(ent, eje, null, centroGestor, null, null);
    }

    /**
     * Test search with economica parameter only.
     * Should pass economica and null for other optional params
     */
    @Test
    void testSearchContabilizado_WithEconomicaOnly() {
        Integer ent = 1;
        String eje = "12345";
        String economica = "ECON-2024";

        FdeFacTerProjection factura = mock(FdeFacTerProjection.class);
        List<FdeFacTerProjection> mockFacturas = Arrays.asList(factura);

        when(contabilizadoSearch.searchContabilizado(ent, eje, null, null, economica, null))
            .thenReturn(mockFacturas);

        ResponseEntity<?> response = fdeController.searchContabilizado(ent, eje, null, null, economica, null);

        assertEquals(HttpStatus.OK, response.getStatusCode(), "Should return 200 OK");
        verify(contabilizadoSearch, times(1))
            .searchContabilizado(ent, eje, null, null, economica, null);
    }

    /**
     * Test search with ano parameter only.
     * Should pass ano and null for other optional params
     */
    @Test
    void testSearchContabilizado_WithAnoOnly() {
        Integer ent = 1;
        String eje = "12345";
        Integer ano = 2023;

        FdeFacTerProjection factura = mock(FdeFacTerProjection.class);
        List<FdeFacTerProjection> mockFacturas = Arrays.asList(factura);

        when(contabilizadoSearch.searchContabilizado(ent, eje, null, null, null, ano))
            .thenReturn(mockFacturas);

        ResponseEntity<?> response = fdeController.searchContabilizado(ent, eje, null, null, null, ano);

        assertEquals(HttpStatus.OK, response.getStatusCode(), "Should return 200 OK");
        verify(contabilizadoSearch, times(1))
            .searchContabilizado(ent, eje, null, null, null, ano);
    }

    /**
     * Test search parameter combinations: proveedor + centroGestor
     */
    @Test
    void testSearchContabilizado_ProveedorAndCentroGestor() {
        Integer ent = 1;
        String eje = "12345";
        String proveedor = "Supplier";
        String centroGestor = "CG001";

        FdeFacTerProjection factura = mock(FdeFacTerProjection.class);
        List<FdeFacTerProjection> mockFacturas = Arrays.asList(factura);

        when(contabilizadoSearch.searchContabilizado(ent, eje, proveedor, centroGestor, null, null))
            .thenReturn(mockFacturas);

        ResponseEntity<?> response = fdeController.searchContabilizado(ent, eje, proveedor, centroGestor, null, null);

        assertEquals(HttpStatus.OK, response.getStatusCode(), "Should return 200 OK");
        verify(contabilizadoSearch, times(1))
            .searchContabilizado(ent, eje, proveedor, centroGestor, null, null);
    }

    /**
     * Test search parameter combinations: economica + ano
     */
    @Test
    void testSearchContabilizado_EconomicaAndAno() {
        Integer ent = 1;
        String eje = "12345";
        String economica = "ECON001";
        Integer ano = 2024;

        FdeFacTerProjection factura = mock(FdeFacTerProjection.class);
        List<FdeFacTerProjection> mockFacturas = Arrays.asList(factura);

        when(contabilizadoSearch.searchContabilizado(ent, eje, null, null, economica, ano))
            .thenReturn(mockFacturas);

        ResponseEntity<?> response = fdeController.searchContabilizado(ent, eje, null, null, economica, ano);

        assertEquals(HttpStatus.OK, response.getStatusCode(), "Should return 200 OK");
        verify(contabilizadoSearch, times(1))
            .searchContabilizado(ent, eje, null, null, economica, ano);
    }

    /**
     * Test search with multiple facturas in result
     */
    @Test
    void testSearchContabilizado_MultipleResults() {
        Integer ent = 1;
        String eje = "12345";

        FdeFacTerProjection factura1 = mock(FdeFacTerProjection.class);
        FdeFacTerProjection factura2 = mock(FdeFacTerProjection.class);
        FdeFacTerProjection factura3 = mock(FdeFacTerProjection.class);

        List<FdeFacTerProjection> mockFacturas = Arrays.asList(factura1, factura2, factura3);

        when(contabilizadoSearch.searchContabilizado(ent, eje, null, null, null, null))
            .thenReturn(mockFacturas);

        ResponseEntity<?> response = fdeController.searchContabilizado(ent, eje, null, null, null, null);

        assertEquals(HttpStatus.OK, response.getStatusCode(), "Should return 200 OK");
        assertEquals(3, ((List<?>) response.getBody()).size(), "Should return 3 facturas");
        verify(contabilizadoSearch, times(1))
            .searchContabilizado(ent, eje, null, null, null, null);
    }

    @Test
    void testHttpStatusCodes_FetchContabilizado() {
        Integer ent = 1;
        String eje = "test";

        when(fdeRepository.findByENTAndEJEAndFac_FACFCOIsNotNull(ent, eje))
            .thenReturn(Arrays.asList(mockFactura1));

        ResponseEntity<?> response = fdeController.fetchContabilizado(ent, eje);
        assertEquals(HttpStatus.OK, response.getStatusCode(), "Should return 200 OK");

        when(fdeRepository.findByENTAndEJEAndFac_FACFCOIsNotNull(ent, eje))
            .thenReturn(Collections.emptyList());

        response = fdeController.fetchContabilizado(ent, eje);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode(), "Should return 404 NOT_FOUND");

        when(fdeRepository.findByENTAndEJEAndFac_FACFCOIsNotNull(ent, eje))
            .thenThrow(new RuntimeException("Error"));

        response = fdeController.fetchContabilizado(ent, eje);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode(), "Should return 400 BAD_REQUEST");
    }

    @Test
    void testHttpStatusCodes_SearchContabilizado() {
        Integer ent = 1;
        String eje = "test";

        when(contabilizadoSearch.searchContabilizado(ent, eje, null, null, null, null))
            .thenReturn(Arrays.asList(mockFactura1));

        ResponseEntity<?> response = fdeController.searchContabilizado(ent, eje, null, null, null, null);
        assertEquals(HttpStatus.OK, response.getStatusCode(), "Should return 200 OK");

        when(contabilizadoSearch.searchContabilizado(ent, eje, null, null, null, null))
            .thenReturn(Collections.emptyList());

        response = fdeController.searchContabilizado(ent, eje, null, null, null, null);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode(), "Should return 404 NOT_FOUND");

        when(contabilizadoSearch.searchContabilizado(ent, eje, null, null, null, null))
            .thenThrow(new DataAccessException("Error") {
                @Override
                public Throwable getMostSpecificCause() {
                    return new Throwable("Error");
                }
            });

        response = fdeController.searchContabilizado(ent, eje, null, null, null, null);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode(), "Should return 400 BAD_REQUEST");
    }

    /**
     * Test that exception messages are properly included in responses
     */
    @Test
    void testExceptionMessageHandlingInResponse() {
        Integer ent = 1;
        String eje = "12345";
        String detailedErrorMessage = "SQL Error: Connection pool exhausted";

        when(fdeRepository.findByENTAndEJEAndFac_FACFCOIsNotNull(ent, eje))
            .thenThrow(new RuntimeException(detailedErrorMessage));

        ResponseEntity<?> response = fdeController.fetchContabilizado(ent, eje);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode(), "Should return 400 BAD_REQUEST");
        String responseBody = response.getBody().toString();
        assertTrue(responseBody.contains("Error :"), "Should contain 'Error :' prefix");
        assertTrue(responseBody.contains(detailedErrorMessage), "Should contain detailed error message");
    }

    /**
     * Test empty/null parameter handling - should still call service
     */
    @Test
    void testSearchWithNullOptionalParamsHandling() {
        Integer ent = 2;
        String eje = "54321";

        when(contabilizadoSearch.searchContabilizado(ent, eje, null, null, null, null))
            .thenReturn(Arrays.asList(mockFactura1));

        ResponseEntity<?> response = fdeController.searchContabilizado(ent, eje, null, null, null, null);

        assertEquals(HttpStatus.OK, response.getStatusCode(), "Should return 200 OK");
        verify(contabilizadoSearch, times(1))
            .searchContabilizado(ent, eje, null, null, null, null);
    }

    /**
     * Test constant values are used correctly
     */
    @Test
    void testConstantValues_SinResultado() {
        Integer ent = 1;
        String eje = "test";

        when(fdeRepository.findByENTAndEJEAndFac_FACFCOIsNotNull(ent, eje))
            .thenReturn(Collections.emptyList());

        ResponseEntity<?> response = fdeController.fetchContabilizado(ent, eje);

        assertEquals("Sin resultado", response.getBody(), "Should use SIN_RESULTADO constant");
    }

    /**
     * Test constant values are used correctly for error
     */
    @Test
    void testConstantValues_ErrorPrefix() {
        Integer ent = 1;
        String eje = "test";
        String testError = "Test error message";

        when(fdeRepository.findByENTAndEJEAndFac_FACFCOIsNotNull(ent, eje))
            .thenThrow(new RuntimeException(testError));

        ResponseEntity<?> response = fdeController.fetchContabilizado(ent, eje);

        assertTrue(response.getBody().toString().startsWith("Error :"), "Should start with ERROR constant");
    }
}