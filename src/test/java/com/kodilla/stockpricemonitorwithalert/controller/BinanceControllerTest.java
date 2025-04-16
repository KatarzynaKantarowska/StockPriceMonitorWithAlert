package com.kodilla.stockpricemonitorwithalert.controller;

import com.kodilla.stockpricemonitorwithalert.dto.BinanceCryptoPriceDto;
import com.kodilla.stockpricemonitorwithalert.service.BinanceService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class BinanceControllerTest {

    private MockMvc mockMvc;
    private BinanceService binanceService;

    @BeforeEach
    void setUp() {
        binanceService = mock(BinanceService.class);
        BinanceController binanceController = new BinanceController(binanceService);
        mockMvc = MockMvcBuilders.standaloneSetup(binanceController).build();
    }

    @Test
    void shouldReturnCryptoPrice() throws Exception {
        // Given
        String symbol = "BTCUSDT";
        BigDecimal price = new BigDecimal("50000");
        BigDecimal pricePln = new BigDecimal("2367");
        when(binanceService.getPrice(symbol)).thenReturn(new BinanceCryptoPriceDto(symbol, price, pricePln));

        // When & Then
        mockMvc.perform(get("/api/v1/binance/price/{symbol}", symbol))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.symbol").value(symbol))
                .andExpect(jsonPath("$.price").value(price.doubleValue()));
    }
}