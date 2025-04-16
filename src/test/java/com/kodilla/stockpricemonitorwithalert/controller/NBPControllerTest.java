package com.kodilla.stockpricemonitorwithalert.controller;

import com.kodilla.stockpricemonitorwithalert.dto.NBPCryptoPriceDto;
import com.kodilla.stockpricemonitorwithalert.dto.NBPRatesDto;
import com.kodilla.stockpricemonitorwithalert.service.NBPService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


class NBPControllerTest {

    private NBPService nbpService;
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        nbpService = mock(NBPService.class);
        NBPController nbpController = new NBPController(nbpService);
        mockMvc = MockMvcBuilders.standaloneSetup(nbpController).build();
    }

    @Test
    void shouldReturnPrice() throws Exception {
        // Given
        String symbol = "USD";
        double mid = 3.83;
        BigDecimal price = BigDecimal.valueOf(mid);

        NBPRatesDto e1 = new NBPRatesDto();
        e1.setMid(mid);
        when(nbpService.getPrice(symbol)).thenReturn(new NBPCryptoPriceDto(symbol, List.of(e1)));
        // When & Then
        mockMvc.perform(get("/api/v1/nbp/price/{symbol}", symbol))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(symbol))
                .andExpect(jsonPath("$.rates[0].mid").value(mid));
    }

    @Test
    void shouldGetPlnToUsdExchangeRate() throws Exception {
        //Given
        String symbol = "USD";
        double mid = 3.83;
        NBPRatesDto rate = new NBPRatesDto();
        rate.setMid(mid);

        NBPCryptoPriceDto expectedDto = new NBPCryptoPriceDto(symbol, List.of(rate));
        when(nbpService.getPlnToUsdExchangeRate()).thenReturn(expectedDto);

        //When & Then
        mockMvc.perform(get("/api/v1/nbp/exchange-rate/pln-to-usd"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(symbol))
                .andExpect(jsonPath("$.rates[0].mid").value(mid));
    }
}