package com.kodilla.stockpricemonitorwithalert.controller;

import com.kodilla.stockpricemonitorwithalert.entity.CryptoInfoSnapshotEty;
import com.kodilla.stockpricemonitorwithalert.service.CryptoService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class CryptoControllerTest {

    private CryptoService cryptoService;
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        cryptoService = mock(CryptoService.class);
        CryptoController cryptoController = new CryptoController(cryptoService);
        mockMvc = MockMvcBuilders.standaloneSetup(cryptoController).build();
    }

    @Test
    void fetchCryptInfoAndSave() throws Exception {
        Assertions.assertTrue(true);
        //Given
        CryptoInfoSnapshotEty cryptoInfoSnapshotEty = new CryptoInfoSnapshotEty();
        when(cryptoService.fetchCryptInfoAndSave("BTC")).thenReturn(cryptoInfoSnapshotEty);

        //When & Then
        mockMvc.perform(post("/api/v1/crypto-controller/BTC"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void shouldReturnPercentageChange() throws Exception {
        when(cryptoService.calculatePercentageChangeFromLastPriceInDb("BTC"))
                .thenReturn(new BigDecimal("5.32"));

        mockMvc.perform(get("/api/v1/crypto-controller/symbols/BTC/getPercentageChangeFromLastPriceInDb"))
                .andExpect(status().isOk())
                .andExpect(content().string("Difference from last price is: 5.32%."));
    }

    @Test
    void shouldReturnHistory() throws Exception {
        List<CryptoInfoSnapshotEty> historyList = List.of(new CryptoInfoSnapshotEty(), new CryptoInfoSnapshotEty());
        when(cryptoService.showCurrencyChangesHistory("BTC")).thenReturn(historyList);
        mockMvc.perform(get("/api/v1/crypto-controller/BTC/history"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

    }

    @Test
    void shouldFetchCryptoSnapshotById() throws Exception {
        CryptoInfoSnapshotEty dummy = new CryptoInfoSnapshotEty();
        when(cryptoService.findCryptoSnapshotById(1L)).thenReturn(dummy);

        mockMvc.perform(get("/api/v1/crypto-controller/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void shouldReturnPingMessage() throws Exception {
        mockMvc.perform(get("/api/v1/crypto-controller/ping"))
                .andExpect(status().isOk())
                .andExpect(content().string("Crypto Api is up and running!"));
    }

    @Test
    void shouldReturnLastPriceForSymbol() throws Exception {
        String symbol = "BTC";
        BigDecimal mockPrice = new BigDecimal("42000.00");

        when(cryptoService.getLastPrice(symbol)).thenReturn(mockPrice);

        mockMvc.perform(get("/api/v1/crypto-controller/" + symbol + "/last-price"))
                .andExpect(status().isOk())
                .andExpect(content().string("Last price for BTC: 42000.00"));
    }

    @Test
    void shouldReturnSnapshotCount() throws Exception {
        when(cryptoService.countAllSnapshots()).thenReturn(5L);

        mockMvc.perform(get("/api/v1/crypto-controller/count"))
                .andExpect(status().isOk())
                .andExpect(content().string("Total snapshots in DB: 5"));
    }

    @Test
    void shouldCheckIfSnapshotExist() throws Exception {
        //Given
        when(cryptoService.existsById(1L)).thenReturn(true);

        //When & Then
        assertTrue(cryptoService.existsById(1L));
    }

    @Test
    void shouldCheckIfSnapshotExists() throws Exception {
        // Given
        when(cryptoService.existsById(1L)).thenReturn(true);

        // When & Then
        mockMvc.perform(get("/api/v1/crypto-controller/1/exists"))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
    }

    @Test
    void shouldUpdateSnapshotPrice() throws Exception {
        // Given
        CryptoInfoSnapshotEty updatedSnapshot = new CryptoInfoSnapshotEty();
        when(cryptoService.updateSnapshotPrice(1L, new BigDecimal("50000.00"))).thenReturn(updatedSnapshot);

        // When & Then
        mockMvc.perform(put("/api/v1/crypto-controller/snapshot/1/price")
                        .param("price", "50000.00")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void shouldDeleteSnapshotById() throws Exception {
        // Given
        doNothing().when(cryptoService).deleteSnapshot(1L);

        // When & Then
        mockMvc.perform(delete("/api/v1/crypto-controller/snapshot/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void shouldDeleteSnapshotsByCryptoName() throws Exception {
        // Given
        doNothing().when(cryptoService).deleteAllByCryptoName("BTC");

        // When & Then
        mockMvc.perform(delete("/api/v1/crypto-controller/snapshot")
                        .param("name", "BTC"))
                .andExpect(status().isNoContent());
    }


}