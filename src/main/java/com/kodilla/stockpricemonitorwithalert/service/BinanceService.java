package com.kodilla.stockpricemonitorwithalert.service;

import com.kodilla.stockpricemonitorwithalert.dto.BinanceCryptoPriceDto;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class BinanceService {

    private final RestTemplate restTemplate;

    public BinanceService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public BinanceCryptoPriceDto getPrice(String symbol) {
        String url = UriComponentsBuilder.fromHttpUrl("https://api.binance.com/api/v3/ticker/price")
                .queryParam("symbol", symbol)
                .toUriString();
        return restTemplate.getForObject(url, BinanceCryptoPriceDto.class);
    }
}
