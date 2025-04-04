package com.kodilla.stockpricemonitorwithalert.controller;

import com.kodilla.stockpricemonitorwithalert.entity.CryptoInfoSnapshotEty;
import com.kodilla.stockpricemonitorwithalert.service.CryptoService;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
//TODO zmień
@RequestMapping("/api/v1/crypto-controller")
public class CryptoController {

    private final CryptoService cryptoService;

    public CryptoController(CryptoService cryptoService) {
        this.cryptoService = cryptoService;
    }

    @PostMapping("/{symbol}")
    public CryptoInfoSnapshotEty fetchCryptInfoAndSave(@PathVariable String symbol) {
        return cryptoService.fetchCryptInfoAndSave(symbol);
    }

    @GetMapping("/symbols/{symbol}/getPercentageChangeFromLastPriceInDb")
    public String abc(@PathVariable String symbol) {
        BigDecimal abc = cryptoService.calculatePercentageChangeFromLastPriceInDb(symbol);
        return "Difference from last price is: " + abc.toString() + "%.";
    }

    @GetMapping("/{symbol}/history")
    public List<CryptoInfoSnapshotEty> history(@PathVariable String symbol) {
        return cryptoService.showCurrencyChangesHistory(symbol);
    }

    @GetMapping("/{cryptoId}")
    public CryptoInfoSnapshotEty fetchCryptoSnapshotById(@PathVariable Long cryptoId) {
        return cryptoService.findCryptoSnapshotById(cryptoId);
    }
}
