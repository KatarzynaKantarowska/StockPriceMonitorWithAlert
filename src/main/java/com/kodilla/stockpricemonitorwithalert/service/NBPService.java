package com.kodilla.stockpricemonitorwithalert.service;

import com.kodilla.stockpricemonitorwithalert.dto.NBPCryptoPriceDto;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class NBPService {

    private final RestTemplate restTemplate;

    public NBPService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public NBPCryptoPriceDto getPrice(String symbol) {
        String url = UriComponentsBuilder.fromHttpUrl("https://api.nbp.pl/api/exchangerates/rates/A/" + symbol + "/?format=json")
                .queryParam("symbol", symbol)
                .toUriString();
        return restTemplate.getForObject(url, NBPCryptoPriceDto.class);
    }

    public NBPCryptoPriceDto getPlnToUsdExchangeRate() {
        String url = "https://api.nbp.pl/api/exchangerates/rates/A/USD?format=json";
        return restTemplate.getForObject(url, NBPCryptoPriceDto.class);
    }
}
