package com.kodilla.stockpricemonitorwithalert.scheduler;

import com.kodilla.stockpricemonitorwithalert.service.BinanceService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
public class CryptoScheduler {
    private final BinanceService binanceService;
    private BigDecimal previousPrice = BigDecimal.ZERO;
    private final BigDecimal bdMultiplier = new BigDecimal("1.1");


    @Scheduled(initialDelay = 10000, fixedRate = 3600000)
    public void checkCryptoPriceAndSendMsg() throws MessagingException {
        BigDecimal currentPrice = binanceService.getPrice("USDTBTC").getPrice();

        if (priceIncreased(previousPrice, currentPrice)) {
            sendPriceAlertEmail(currentPrice);
        }
        previousPrice = currentPrice;
    }

    private boolean priceIncreased(BigDecimal previousPrice, BigDecimal currentPrice) {
        BigDecimal prevPriceAfterMultiplicationByMultiplier = previousPrice.multiply(bdMultiplier);
        return currentPrice.compareTo(prevPriceAfterMultiplicationByMultiplier) >= 0;

    }

    private void sendPriceAlertEmail(BigDecimal currentPrice) throws MessagingException {
        String msg = "The price of the cryptocurrency has increased by 10%! The current price is: " + currentPrice;
    }
}
