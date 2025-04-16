package com.kodilla.stockpricemonitorwithalert.controller;

import com.kodilla.stockpricemonitorwithalert.entity.CryptoInfoSnapshotEty;
import com.kodilla.stockpricemonitorwithalert.service.CryptoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
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

    @GetMapping("/ping")
    public String ping() {
        return "Crypto Api is up and running!";
    }

    @GetMapping("/{symbol}/last-price")
    public String getLastPrice(@PathVariable String symbol) {
        BigDecimal lastPrice = cryptoService.getLastPrice(symbol);
        return "Last price for " + symbol + ": " + lastPrice;
    }

    @GetMapping("/count")
    public String countSnapshots() {
        long count = cryptoService.countAllSnapshots();
        return "Total snapshots in DB: " + count;
    }

    @GetMapping("/{id}/exists")
    public boolean existsById(@PathVariable Long id) {
        return cryptoService.existsById(id);
    }

    @PutMapping("/snapshot/{id}/price")
    public ResponseEntity<CryptoInfoSnapshotEty> updatePrice(@PathVariable Long id, @RequestParam BigDecimal price) {
        return ResponseEntity.ok(cryptoService.updateSnapshotPrice(id, price));
    }

    @DeleteMapping("/snapshot/{id}")
    public ResponseEntity<Void> deleteSnapshot(@PathVariable Long id) {
        cryptoService.deleteSnapshot(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/snapshot")
    public ResponseEntity<Void> deleteByCryptoName(@RequestParam String name) {
        cryptoService.deleteAllByCryptoName(name);
        return ResponseEntity.noContent().build();
    }
}