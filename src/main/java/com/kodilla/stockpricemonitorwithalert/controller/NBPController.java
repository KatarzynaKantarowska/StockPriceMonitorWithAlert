package com.kodilla.stockpricemonitorwithalert.controller;


import com.kodilla.stockpricemonitorwithalert.dto.NBPCryptoPriceDto;
import com.kodilla.stockpricemonitorwithalert.service.NBPService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/nbp")
public class NBPController {

    private final NBPService nbpService;

    @Autowired
    public NBPController(NBPService nbpService) {
        this.nbpService = nbpService;
    }

    @GetMapping("/price/{symbol}")
    public NBPCryptoPriceDto getPrice(@PathVariable String symbol) {
        return nbpService.getPrice(symbol);
    }

    @GetMapping("/exchange-rate/pln-to-usd")
    public NBPCryptoPriceDto getPlnToUsdExchangeRate() {
        return nbpService.getPlnToUsdExchangeRate();
    }
}
