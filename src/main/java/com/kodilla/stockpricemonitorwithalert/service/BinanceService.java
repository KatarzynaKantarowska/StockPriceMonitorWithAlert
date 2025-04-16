package com.kodilla.stockpricemonitorwithalert.service;

import com.kodilla.stockpricemonitorwithalert.dto.BinanceCryptoPriceDto;
import com.kodilla.stockpricemonitorwithalert.entity.CryptoInfoSnapshotEty;
import com.kodilla.stockpricemonitorwithalert.repository.BinanceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class BinanceService {

    private final RestTemplate restTemplate;
    private final BinanceRepository binanceRepository;

    public BinanceCryptoPriceDto getPrice(String symbol) {
        String url = UriComponentsBuilder.fromHttpUrl("https://api.binance.com/api/v3/ticker/price")
                .queryParam("symbol", symbol)
                .toUriString();
        return restTemplate.getForObject(url, BinanceCryptoPriceDto.class);
    }

    public CryptoInfoSnapshotEty save(BinanceCryptoPriceDto dto) {
        CryptoInfoSnapshotEty ety = CryptoInfoSnapshotEty.builder()
                .cryptoName(dto.getSymbol())
                .price(dto.getPrice())
                .timestamp(LocalDateTime.now())
                .build();
        return binanceRepository.save(ety);
    }
}
