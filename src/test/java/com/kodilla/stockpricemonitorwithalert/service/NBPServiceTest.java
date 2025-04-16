package com.kodilla.stockpricemonitorwithalert.service;

import com.kodilla.stockpricemonitorwithalert.dto.NBPCryptoPriceDto;
import com.kodilla.stockpricemonitorwithalert.dto.NBPRatesDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class NBPServiceTest {

    private RestTemplate restTemplate;
    private NBPService nbpService;

    @BeforeEach
    void setUp() {
        restTemplate = mock(RestTemplate.class);
        nbpService = new NBPService(restTemplate);
    }

    @Test
    void shouldReturnCryptoPrice() {
        // Given
        String symbol = "USD";
        double mid = 3.83;

        NBPRatesDto rate = new NBPRatesDto();
        rate.setMid(mid);

        NBPCryptoPriceDto expectedDto = new NBPCryptoPriceDto(symbol, List.of(rate));

        String expectedUrl = "https://api.nbp.pl/api/exchangerates/rates/A/USD/?format=json&symbol=USD";
        when(restTemplate.getForObject(eq(expectedUrl), eq(NBPCryptoPriceDto.class)))
                .thenReturn(expectedDto);

        // When
        NBPCryptoPriceDto result = nbpService.getPrice(symbol);

        // Then
        assertEquals(expectedDto, result);
        verify(restTemplate, times(1)).getForObject(eq(expectedUrl), eq(NBPCryptoPriceDto.class));
    }

    @Test
    void getPlnToUsdExchangeRate() {
        // Given
        String expectedUrl = "https://api.nbp.pl/api/exchangerates/rates/A/USD?format=json";
        NBPRatesDto rate = new NBPRatesDto();
        rate.setMid(3.83);

        NBPCryptoPriceDto expectedDto = new NBPCryptoPriceDto("USD", List.of(rate));

        when(restTemplate.getForObject(eq(expectedUrl), eq(NBPCryptoPriceDto.class)))
                .thenReturn(expectedDto);

        // When
        NBPCryptoPriceDto result = nbpService.getPlnToUsdExchangeRate();

        // Then
        assertEquals(expectedDto, result);
        verify(restTemplate, times(1)).getForObject(eq(expectedUrl), eq(NBPCryptoPriceDto.class));
    }
}