package com.kodilla.stockpricemonitorwithalert.scheduler;

import com.kodilla.stockpricemonitorwithalert.dto.BinanceCryptoPriceDto;
import com.kodilla.stockpricemonitorwithalert.service.BinanceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j(topic = "CryptoScheduler")
public class CryptoScheduler {
    private final BinanceService binanceService;

    @Scheduled(cron = "0 */5 * * * ?")
    void persistHistoricCryptoData() {
        log.info("Starting scheduled task: persistHistoricCryptoData");

        try {
            BinanceCryptoPriceDto btcusdt = binanceService.getPrice("BTCUSDT");
            log.info("Fetched BTCUSDT price: {}", btcusdt);

            BinanceCryptoPriceDto ethusdt = binanceService.getPrice("ETHUSDT");
            log.info("Fetched ETHUSDT price: {}", ethusdt);

            BinanceCryptoPriceDto xrpusdt = binanceService.getPrice("XRPUSDT");
            log.info("Fetched XRPUSDT price: {}", xrpusdt);

            binanceService.save(btcusdt);
            log.info("Saved BTCUSDT price to database");

            binanceService.save(ethusdt);
            log.info("Saved ETHUSDT price to database");

            binanceService.save(xrpusdt);
            log.info("Saved XRPUSDT price to database");

        } catch (Exception e) {
            log.error("Error occurred during scheduled task execution: {}", e.getMessage(), e);
        }

        log.info("Scheduled task: persistHistoricCryptoData completed");
    }
}