package com.kodilla.stockpricemonitorwithalert.scheduler;

import com.kodilla.stockpricemonitorwithalert.dto.BinanceCryptoPriceDto;
import com.kodilla.stockpricemonitorwithalert.service.BinanceService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;

import static org.mockito.Mockito.*;

class CryptoSchedulerTest {

    private BinanceService binanceService;
    private CryptoScheduler cryptoScheduler;

    @BeforeEach
    void setUp() {
        binanceService = mock(BinanceService.class);
        cryptoScheduler = new CryptoScheduler(binanceService);
    }

    @Test
    void shouldPersistHistoricCryptoData() {
        // Given
        BinanceCryptoPriceDto btcusdt = new BinanceCryptoPriceDto("BTCUSDT", new BigDecimal("50000.00"), null);
        BinanceCryptoPriceDto ethusdt = new BinanceCryptoPriceDto("ETHUSDT", new BigDecimal("4000.00"), null);
        BinanceCryptoPriceDto xrpusdt = new BinanceCryptoPriceDto("XRPUSDT", new BigDecimal("1.00"), null);

        when(binanceService.getPrice("BTCUSDT")).thenReturn(btcusdt);
        when(binanceService.getPrice("ETHUSDT")).thenReturn(ethusdt);
        when(binanceService.getPrice("XRPUSDT")).thenReturn(xrpusdt);

        // When
        cryptoScheduler.persistHistoricCryptoData();

        // Then
        verify(binanceService, times(1)).getPrice("BTCUSDT");
        verify(binanceService, times(1)).getPrice("ETHUSDT");
        verify(binanceService, times(1)).getPrice("XRPUSDT");

        verify(binanceService, times(1)).save(btcusdt);
        verify(binanceService, times(1)).save(ethusdt);
        verify(binanceService, times(1)).save(xrpusdt);
    }

    @Test
    void shouldHandleExceptionDuringScheduledTask() {
        // Given
        when(binanceService.getPrice("BTCUSDT")).thenThrow(new RuntimeException("API error"));

        // When
        cryptoScheduler.persistHistoricCryptoData();

        // Then
        verify(binanceService, times(1)).getPrice("BTCUSDT");
        verifyNoMoreInteractions(binanceService);
    }
}