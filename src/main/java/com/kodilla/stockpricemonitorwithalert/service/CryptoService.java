package com.kodilla.stockpricemonitorwithalert.service;

import com.kodilla.stockpricemonitorwithalert.dto.BinanceCryptoPriceDto;
import com.kodilla.stockpricemonitorwithalert.entity.CryptoInfoSnapshotEty;
import com.kodilla.stockpricemonitorwithalert.repository.CryptoPriceRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;

@Component
@Service
public class CryptoService {

    private final BinanceService binanceService;
    private final CryptoPriceRepository cryptoPriceRepository;

    public CryptoService(BinanceService binanceService, CryptoPriceRepository cryptoPriceRepository) {
        this.binanceService = binanceService;
        this.cryptoPriceRepository = cryptoPriceRepository;
    }

    public CryptoInfoSnapshotEty fetchCryptInfoAndSave(String symbol) {
        CryptoInfoSnapshotEty cryptoInfoSnapshotEty = new CryptoInfoSnapshotEty();
        BinanceCryptoPriceDto crypto = binanceService.getPrice(symbol);
        cryptoInfoSnapshotEty.setPrice(crypto.getPrice());
        cryptoInfoSnapshotEty.setCryptoName(crypto.getSymbol());
        cryptoInfoSnapshotEty.setTimestamp(LocalDateTime.now());
        return cryptoPriceRepository.save(cryptoInfoSnapshotEty);
    }

    public CryptoInfoSnapshotEty updateSnapshotPrice(Long id, BigDecimal newPrice) {
        CryptoInfoSnapshotEty snapshot = cryptoPriceRepository.findById(id).orElseThrow();
        snapshot.setPrice(newPrice);
        return cryptoPriceRepository.save(snapshot);
    }

    public void deleteSnapshot(Long id) {
        cryptoPriceRepository.deleteById(id);
    }

    public void deleteAllByCryptoName(String symbol) {
        List<CryptoInfoSnapshotEty> snapshots = cryptoPriceRepository.findAllByCryptoName(symbol);
        cryptoPriceRepository.deleteAll(snapshots);
    }


    public BigDecimal calculatePercentageChangeFromLastPriceInDb(String symbol) {
        BinanceCryptoPriceDto actual = binanceService.getPrice(symbol);
        CryptoInfoSnapshotEty last = cryptoPriceRepository.findTopByOrderByIdDesc();

        if (last != null) {
            BigDecimal lastPrice = last.getPrice();
            BigDecimal actualPrice = actual.getPrice();
            BigDecimal change = actualPrice.subtract(lastPrice);
            BigDecimal percentageChange = (change.divide(lastPrice, RoundingMode.HALF_UP).multiply(new BigDecimal(100)));

            return percentageChange;
        }
        return calculatePercentageChangeFromLastPriceInDb(symbol);
    }

    public List<CryptoInfoSnapshotEty> showCurrencyChangesHistory(String symbol) {
        return cryptoPriceRepository.findAllByCryptoName(symbol).stream().toList();
    }

    public CryptoInfoSnapshotEty findCryptoSnapshotById(Long cryptoId) {
        return cryptoPriceRepository.findCryptoById(cryptoId);
    }

    public BigDecimal getLastPrice(String symbol) {
        return cryptoPriceRepository.findTopByOrderByIdDesc().getPrice();
    }

    public Long countAllSnapshots() {
        return cryptoPriceRepository.count();
    }

    public boolean existsById(Long id) {
        return cryptoPriceRepository.existsById(id);
    }
}