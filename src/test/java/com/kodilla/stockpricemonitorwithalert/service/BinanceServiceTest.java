package com.kodilla.stockpricemonitorwithalert.service;

import com.kodilla.stockpricemonitorwithalert.dto.BinanceCryptoPriceDto;
import com.kodilla.stockpricemonitorwithalert.entity.CryptoInfoSnapshotEty;
import com.kodilla.stockpricemonitorwithalert.repository.BinanceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class BinanceServiceTest {

    private RestTemplate restTemplate;
    private BinanceService binanceService;
    private BinanceRepository binanceRepository;

    @BeforeEach
    void setUp() {
        restTemplate = Mockito.mock(RestTemplate.class);
        binanceRepository = Mockito.mock(BinanceRepository.class);
        binanceService = new BinanceService(restTemplate, binanceRepository);
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

    @Test
    void shouldSaveCryptoInfoSnapshot() {
        // Given
        BinanceCryptoPriceDto dto = new BinanceCryptoPriceDto("BTCUSDT", new BigDecimal("50000.00"), null);
        CryptoInfoSnapshotEty expectedEntity = CryptoInfoSnapshotEty.builder()
                .cryptoName("BTCUSDT")
                .price(dto.getPrice())
                .build();

        when(binanceRepository.save(any(CryptoInfoSnapshotEty.class))).thenReturn(expectedEntity);

        // When
        CryptoInfoSnapshotEty result = binanceService.save(dto);

        // Then
        ArgumentCaptor<CryptoInfoSnapshotEty> captor = ArgumentCaptor.forClass(CryptoInfoSnapshotEty.class);
        verify(binanceRepository, times(1)).save(captor.capture());
        CryptoInfoSnapshotEty capturedEntity = captor.getValue();

        assertEquals("BTCUSDT", capturedEntity.getCryptoName());
        assertEquals(dto.getPrice(), capturedEntity.getPrice());
        assertEquals(expectedEntity, result);
    }
}