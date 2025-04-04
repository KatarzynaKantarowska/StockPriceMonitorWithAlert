package com.kodilla.stockpricemonitorwithalert.service;
import com.kodilla.stockpricemonitorwithalert.dto.binance.BinanceCryptoPriceDto;
import com.kodilla.stockpricemonitorwithalert.entity.CryptoInfoSnapshotEty;
import com.kodilla.stockpricemonitorwithalert.repository.CryptoPriceRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;

//import static jdk.internal.org.jline.reader.impl.LineReaderImpl.CompletionType.List;

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
        return calculatePercentageChangeFromLastPriceInDb(symbol); // ?
    }

    public List<CryptoInfoSnapshotEty> showCurrencyChangesHistory(String symbol) {
        return cryptoPriceRepository.findAllByCryptoName(symbol).stream().toList();
    }

    public CryptoInfoSnapshotEty findCryptoSnapshotById(Long cryptoId) {
        return cryptoPriceRepository.findCryptoById(cryptoId);
    }

//    public CryptoEty changeCryptoCurrency(Long cryptoId) {
//        return cryptoPriceRepository.
//    }
}