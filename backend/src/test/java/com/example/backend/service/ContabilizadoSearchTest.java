package com.example.backend.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.example.backend.dto.FdeFacTerProjection;
import com.example.backend.dto.FdeFacTerProjection.FacInfo;
import com.example.backend.sqlserver2.repository.FdeRepository;

class ContabilizadoSearchTest {

    private ContabilizadoSearch contabilizadoSearch;
    private FdeRepository fdeRepository;

    @BeforeEach
    void setUp() {
        fdeRepository = mock(FdeRepository.class);
        contabilizadoSearch = new ContabilizadoSearch();
        injectField(contabilizadoSearch, "fdeRepository", fdeRepository);
    }

    private void injectField(Object target, String fieldName, Object value) {
        try {
            java.lang.reflect.Field field = target.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(target, value);
        } catch (Exception e) {
            throw new RuntimeException("Failed to inject field: " + fieldName, e);
        }
    }

    private <T> T invokePrivateMethod(String methodName, Class<?>[] paramTypes, Object[] params) {
        try {
            java.lang.reflect.Method method = ContabilizadoSearch.class.getDeclaredMethod(methodName, paramTypes);
            method.setAccessible(true);
            @SuppressWarnings("unchecked")
            T result = (T) method.invoke(contabilizadoSearch, params);
            return result;
        } catch (Exception e) {
            throw new RuntimeException("Failed to invoke method: " + methodName, e);
        }
    }

    @Test
    void testIsNumbersOnly_WithNumericString() {
        Boolean result = invokePrivateMethod("isNumbersOnly", new Class<?>[] {String.class}, new Object[] {"12345"});
        assertTrue(result, "Should return true for numeric string");
    }

    @Test
    void testIsNumbersOnly_WithMixedString() {
        Boolean result = invokePrivateMethod("isNumbersOnly", new Class<?>[] {String.class}, new Object[] {"12345ABC"});
        assertFalse(result, "Should return false for mixed alphanumeric string");
    }

    @Test
    void testIsNumbersOnly_WithAlphabeticString() {
        Boolean result = invokePrivateMethod("isNumbersOnly", new Class<?>[] {String.class}, new Object[] {"ABCDE"});
        assertFalse(result, "Should return false for alphabetic string");
    }

    @Test
    void testIsNumbersOnly_WithSpecialCharacters() {
        Boolean result = invokePrivateMethod("isNumbersOnly", new Class<?>[] {String.class}, new Object[] {"123@45"});
        assertFalse(result, "Should return false for string with special characters");
    }

    @Test
    void testIsNumbersOnly_WithSpaces() {
        Boolean result = invokePrivateMethod("isNumbersOnly", new Class<?>[] {String.class}, new Object[] {"123 45"});
        assertFalse(result, "Should return false for string with spaces");
    }

    @Test
    void testIsMixed_WithAlphabeticString() {
        Boolean result = invokePrivateMethod("isMixed", new Class<?>[] {String.class}, new Object[] {"ABCDE"});
        assertTrue(result, "Should return true for alphabetic string");
    }

    @Test
    void testIsMixed_WithMixedString() {
        Boolean result = invokePrivateMethod("isMixed", new Class<?>[] {String.class}, new Object[] {"Test123"});
        assertTrue(result, "Should return true for mixed string");
    }

    @Test
    void testIsMixed_WithNumericString() {
        Boolean result = invokePrivateMethod("isMixed", new Class<?>[] {String.class}, new Object[] {"12345"});
        assertFalse(result, "Should return false for numeric string");
    }

    @Test
    void testSearchContabilizado_NoOptionalParameters() {
        FdeFacTerProjection factura1 = createMockFactura(1000.0, 0.0);
        FdeFacTerProjection factura2 = createMockFactura(0.0, 500.0);

        List<FdeFacTerProjection> mockFacturas = Arrays.asList(factura1, factura2);

        when(fdeRepository.findByENTAndEJEAndFac_FACFCOIsNotNull(1, "12345"))
            .thenReturn(mockFacturas);

        List<FdeFacTerProjection> result = contabilizadoSearch.searchContabilizado(1, "12345", null, null, null, null);

        assertEquals(2, result.size(), "Should return 2 facturas");
        verify(fdeRepository, times(1)).findByENTAndEJEAndFac_FACFCOIsNotNull(1, "12345");
    }

    @Test
    void testSearchContabilizado_FiltersZeroValues() {
        FdeFacTerProjection validFactura = createMockFactura(1000.0, 0.0);
        FdeFacTerProjection invalidFactura = createMockFactura(0.0, 0.0);

        List<FdeFacTerProjection> mockFacturas = Arrays.asList(validFactura, invalidFactura);

        when(fdeRepository.findByENTAndEJEAndFac_FACFCOIsNotNull(1, "12345"))
            .thenReturn(mockFacturas);

        List<FdeFacTerProjection> result = contabilizadoSearch.searchContabilizado(1, "12345", null, null, null, null);

        assertEquals(1, result.size(), "Should filter out factura with zero values");
    }

    @Test
    void testSearchContabilizado_OnlyFDEIMPGreaterThanZero() {
        FdeFacTerProjection factura = createMockFactura(100.0, 0.0);

        List<FdeFacTerProjection> mockFacturas = Arrays.asList(factura);

        when(fdeRepository.findByENTAndEJEAndFac_FACFCOIsNotNull(1, "12345"))
            .thenReturn(mockFacturas);

        List<FdeFacTerProjection> result = contabilizadoSearch.searchContabilizado(1, "12345", null, null, null, null);

        assertEquals(1, result.size(), "Should include factura with FDEIMP > 0");
    }

    @Test
    void testSearchContabilizado_OnlyFDEDIFGreaterThanZero() {
        FdeFacTerProjection factura = createMockFactura(0.0, 500.0);

        List<FdeFacTerProjection> mockFacturas = Arrays.asList(factura);

        when(fdeRepository.findByENTAndEJEAndFac_FACFCOIsNotNull(1, "12345"))
            .thenReturn(mockFacturas);

        List<FdeFacTerProjection> result = contabilizadoSearch.searchContabilizado(1, "12345", null, null, null, null);

        assertEquals(1, result.size(), "Should include factura with FDEDIF > 0");
    }

    @Test
    void testSearchContabilizado_EmptyRepository() {
        when(fdeRepository.findByENTAndEJEAndFac_FACFCOIsNotNull(1, "12345"))
            .thenReturn(Collections.emptyList());

        List<FdeFacTerProjection> result = contabilizadoSearch.searchContabilizado(1, "12345", null, null, null, null);

        assertEquals(0, result.size(), "Should return empty list");
    }

    @Test
    void testSearchContabilizado_WithEmptyProveedorString() {
        FdeFacTerProjection factura = createMockFacturaWithProvider("12345", "ACME Corp");

        List<FdeFacTerProjection> mockFacturas = Arrays.asList(factura);

        when(fdeRepository.findByENTAndEJEAndFac_FACFCOIsNotNull(1, "12345"))
            .thenReturn(mockFacturas);

        List<FdeFacTerProjection> result = contabilizadoSearch.searchContabilizado(1, "12345", "", null, null, null);

        assertEquals(1, result.size(), "Should ignore empty proveedor string");
    }

    @Test
    void testSearchContabilizado_WithCentroGestorFilter() {
        FdeFacTerProjection factura = createMockFacturaWithCentroGestor("CG001");

        List<FdeFacTerProjection> mockFacturas = Arrays.asList(factura);

        when(fdeRepository.findByENTAndEJEAndFac_FACFCOIsNotNull(1, "12345"))
            .thenReturn(mockFacturas);

        List<FdeFacTerProjection> result = contabilizadoSearch.searchContabilizado(1, "12345", null, "CG001", null, null);

        assertEquals(1, result.size(), "Should filter by centroGestor");
    }

    @Test
    void testSearchContabilizado_CentroGestorCaseInsensitive() {
        FdeFacTerProjection factura = createMockFacturaWithCentroGestor("CG001");

        List<FdeFacTerProjection> mockFacturas = Arrays.asList(factura);

        when(fdeRepository.findByENTAndEJEAndFac_FACFCOIsNotNull(1, "12345"))
            .thenReturn(mockFacturas);

        List<FdeFacTerProjection> result = contabilizadoSearch.searchContabilizado(1, "12345", null, "cg001", null, null);

        assertEquals(1, result.size(), "Should be case insensitive for centroGestor");
    }

    @Test
    void testSearchContabilizado_WithEconomicaFilter() {
        FdeFacTerProjection factura = createMockFacturaWithEconomica("ECON001");

        List<FdeFacTerProjection> mockFacturas = Arrays.asList(factura);

        when(fdeRepository.findByENTAndEJEAndFac_FACFCOIsNotNull(1, "12345"))
            .thenReturn(mockFacturas);

        List<FdeFacTerProjection> result = contabilizadoSearch.searchContabilizado(1, "12345", null, null, "ECON001", null);

        assertEquals(1, result.size(), "Should filter by economica");
    }

    @Test
    void testSearchContabilizado_EconomicaCaseInsensitive() {
        FdeFacTerProjection factura = createMockFacturaWithEconomica("ECON001");

        List<FdeFacTerProjection> mockFacturas = Arrays.asList(factura);

        when(fdeRepository.findByENTAndEJEAndFac_FACFCOIsNotNull(1, "12345"))
            .thenReturn(mockFacturas);

        List<FdeFacTerProjection> result = contabilizadoSearch.searchContabilizado(1, "12345", null, null, "econ001", null);

        assertEquals(1, result.size(), "Should be case insensitive for economica");
    }

    @Test
    void testSearchContabilizado_WithAnoFilter() {
        FdeFacTerProjection factura = createMockFacturaWithAno(2024);

        List<FdeFacTerProjection> mockFacturas = Arrays.asList(factura);

        when(fdeRepository.findByENTAndEJEAndFac_FACFCOIsNotNull(1, "12345"))
            .thenReturn(mockFacturas);

        List<FdeFacTerProjection> result = contabilizadoSearch.searchContabilizado(1, "12345", null, null, null, 2024);

        assertEquals(1, result.size(), "Should filter by ano");
    }

    @Test
    void testSearchContabilizado_WithAnoFilterNoMatch() {
        FdeFacTerProjection factura = createMockFacturaWithAno(2024);

        List<FdeFacTerProjection> mockFacturas = Arrays.asList(factura);

        when(fdeRepository.findByENTAndEJEAndFac_FACFCOIsNotNull(1, "12345"))
            .thenReturn(mockFacturas);

        List<FdeFacTerProjection> result = contabilizadoSearch.searchContabilizado(1, "12345", null, null, null, 2023);

        assertEquals(0, result.size(), "Should return empty when ano doesn't match");
    }

    @Test
    void testSearchContabilizado_WithNullFacturasList() {
        when(fdeRepository.findByENTAndEJEAndFac_FACFCOIsNotNull(1, "12345"))
            .thenReturn(null);

        assertThrows(NullPointerException.class, () -> 
            contabilizadoSearch.searchContabilizado(1, "12345", null, null, null, null),
            "Should throw NPE when repository returns null list"
        );
    }

    private FdeFacTerProjection createMockFactura(Double fdeimp, Double fdedif) {
        FdeFacTerProjection factura = mock(FdeFacTerProjection.class);
        when(factura.getFDEIMP()).thenReturn(fdeimp);
        when(factura.getFDEDIF()).thenReturn(fdedif);
        when(factura.getFac()).thenReturn(null);
        when(factura.getFDEECO()).thenReturn(null);
        return factura;
    }

    private FdeFacTerProjection createMockFacturaWithProvider(String nif, String nombre) {
        FdeFacTerProjection factura = mock(FdeFacTerProjection.class);
        when(factura.getFDEIMP()).thenReturn(1000.0);
        when(factura.getFDEDIF()).thenReturn(0.0);
        when(factura.getFDEECO()).thenReturn(null);
        try {
            Class<?> facInfoClass = Class.forName("com.example.backend.dto.FdeFacTerProjection$FacInfo");
            Object facMock = mock(facInfoClass);
            
            Object terMock = mock(Object.class);
            
            java.lang.reflect.Method getTerMethod = facInfoClass.getMethod("getTer");
            when(getTerMethod.invoke(facMock)).thenReturn(terMock);
            
            setTerMockValues(terMock, nif, nombre);
            
            @SuppressWarnings("unchecked")
            FacInfo facInfoMock = (FacInfo) facMock;
            when(factura.getFac()).thenReturn(facInfoMock);
        } catch (Exception e) {
            when(factura.getFac()).thenReturn(null);
        }
        return factura;
    }

    private void setTerMockValues(Object terMock, String nif, String nombre) {
        try {
            java.lang.reflect.Method setNifMethod = terMock.getClass().getMethod("getTERNIF");
            java.lang.reflect.Method setNameMethod = terMock.getClass().getMethod("getTERNOM");
            when(setNifMethod.invoke(terMock)).thenReturn(nif);
            when(setNameMethod.invoke(terMock)).thenReturn(nombre);
        } catch (Exception e) {
        }
    }

    private FdeFacTerProjection createMockFacturaWithCentroGestor(String cg) {
        FdeFacTerProjection factura = mock(FdeFacTerProjection.class);
        when(factura.getFDEIMP()).thenReturn(1000.0);
        when(factura.getFDEDIF()).thenReturn(0.0);
        when(factura.getFDEECO()).thenReturn(null);

        try {
            Class<?> facInfoClass = Class.forName("com.example.backend.dto.FdeFacTerProjection$FacInfo");
            Object facMock = mock(facInfoClass);
            java.lang.reflect.Method getCGMethod = facInfoClass.getMethod("getCGECOD");
            when(getCGMethod.invoke(facMock)).thenReturn(cg);
            @SuppressWarnings("unchecked")
            FacInfo cg_facInfoMock = (FacInfo) facMock;
            when(factura.getFac()).thenReturn(cg_facInfoMock);
        } catch (Exception e) {
            when(factura.getFac()).thenReturn(null);
        }
        return factura;
    }

    private FdeFacTerProjection createMockFacturaWithEconomica(String eco) {
        FdeFacTerProjection factura = mock(FdeFacTerProjection.class);
        when(factura.getFDEIMP()).thenReturn(1000.0);
        when(factura.getFDEDIF()).thenReturn(0.0);
        when(factura.getFDEECO()).thenReturn(eco);
        when(factura.getFac()).thenReturn(null);
        return factura;
    }

    private FdeFacTerProjection createMockFacturaWithAno(Integer ano) {
        FdeFacTerProjection factura = mock(FdeFacTerProjection.class);
        when(factura.getFDEIMP()).thenReturn(1000.0);
        when(factura.getFDEDIF()).thenReturn(0.0);
        when(factura.getFDEECO()).thenReturn(null);

        try {
            Class<?> facInfoClass = Class.forName("com.example.backend.dto.FdeFacTerProjection$FacInfo");
            Object facMock = mock(facInfoClass);
            java.lang.reflect.Method getAnoMethod = facInfoClass.getMethod("getFACANN");
            when(getAnoMethod.invoke(facMock)).thenReturn(ano);
            @SuppressWarnings("unchecked")
            FacInfo ano_facInfoMock = (FacInfo) facMock;
            when(factura.getFac()).thenReturn(ano_facInfoMock);
        } catch (Exception e) {
            when(factura.getFac()).thenReturn(null);
        }
        return factura;
    }

    private FdeFacTerProjection createCompleteFactura(String nif, String nombre, String cg, String eco, Integer ano) {
        FdeFacTerProjection factura = mock(FdeFacTerProjection.class);
        when(factura.getFDEIMP()).thenReturn(1000.0);
        when(factura.getFDEDIF()).thenReturn(0.0);
        when(factura.getFDEECO()).thenReturn(eco);

        try {
            Class<?> facInfoClass = Class.forName("com.example.backend.dto.FdeFacTerProjection$FacInfo");
            Object facMock = mock(facInfoClass);
            Object terMock = mock(Object.class);

            java.lang.reflect.Method getTerMethod = facInfoClass.getMethod("getTer");
            java.lang.reflect.Method getCGMethod = facInfoClass.getMethod("getCGECOD");
            java.lang.reflect.Method getAnoMethod = facInfoClass.getMethod("getFACANN");

            when(getTerMethod.invoke(facMock)).thenReturn(terMock);
            when(getCGMethod.invoke(facMock)).thenReturn(cg);
            when(getAnoMethod.invoke(facMock)).thenReturn(ano);

            setTerMockValues(terMock, nif, nombre);
            @SuppressWarnings("unchecked")
            FacInfo facInfoMock = (FacInfo) facMock;
            when(factura.getFac()).thenReturn(facInfoMock);
        } catch (Exception e) {
            when(factura.getFac()).thenReturn(null);
        }
        return factura;
    }
}