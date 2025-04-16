package com.kodilla.stockpricemonitorwithalert.service;

import com.kodilla.stockpricemonitorwithalert.dto.BinanceCryptoPriceDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class BinanceServiceTest {

    private RestTemplate restTemplate;
    private BinanceService binanceService;
    private BinanceCryptoPriceDto mockedResponse;

    @BeforeEach
    void setUp() {
        restTemplate = Mockito.mock(RestTemplate.class);
        binanceService = new BinanceService(restTemplate);
    }

    @Test
    void shouldReturnPrice() throws Exception {

        BigDecimal price = new BigDecimal("45000");
        String symbol = "BTCUSDT";
        BinanceCryptoPriceDto mockedResponse = new BinanceCryptoPriceDto();
        mockedResponse.setPrice(price);
        Mockito.when(restTemplate.getForObject(Mockito.anyString(), Mockito.eq(BinanceCryptoPriceDto.class)))
                .thenReturn(mockedResponse);
        BinanceCryptoPriceDto result = binanceService.getPrice(symbol);
        assertNotNull(result);
        assertEquals(price, result.getPrice());

    }
}