package com.kodilla.stockpricemonitorwithalert.views;

import com.kodilla.stockpricemonitorwithalert.dto.BinanceCryptoPriceDto;
import com.kodilla.stockpricemonitorwithalert.entity.CryptoInfoSnapshotEty;
import com.kodilla.stockpricemonitorwithalert.service.BinanceService;
import com.kodilla.stockpricemonitorwithalert.service.CryptoService;
import com.kodilla.stockpricemonitorwithalert.service.NBPService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

//import static jdk.jfr.internal.consumer.EventLog.update;

@Slf4j
@Route("")
@Component
public class CryptoView extends VerticalLayout {

    public CryptoView(@Autowired BinanceService binanceService,
                      @Autowired NBPService nbpService,
                      @Autowired CryptoService cryptoService) {

        add(new Button("Crypto"));

        add(new Button("List of top 5 current prices of crypto currencies:"));

        Grid<BinanceCryptoPriceDto> grid = new Grid<>(BinanceCryptoPriceDto.class);
        grid.setColumns("symbol", "price", "pricePln");
        add(grid);
        BinanceCryptoPriceDto btcusdt = binanceService.getPrice("BTCUSDT");
        BinanceCryptoPriceDto ethusdt = binanceService.getPrice("ETHUSDT");
        BinanceCryptoPriceDto xrpusdt = binanceService.getPrice("XRPUSDT");
        BinanceCryptoPriceDto bnbusdt = binanceService.getPrice("BNBUSDT");
        BinanceCryptoPriceDto solusdt = binanceService.getPrice("SOLUSDT");

        List<BinanceCryptoPriceDto> list = List.of(btcusdt, ethusdt, xrpusdt, bnbusdt, solusdt);

        double rate = nbpService.getPlnToUsdExchangeRate().getRates().stream().findFirst().orElseThrow(RuntimeException::new).getMid();
        BigDecimal rateBd = BigDecimal.valueOf(rate);
        list.forEach(e -> e.setPricePln(e.getPrice().multiply(rateBd)));

        grid.setItems(list);

        add(new Button("List of crypto historic snapshots for btc:"));

        Grid<CryptoInfoSnapshotEty> cryptoInfoSnapshotEtyGrid = new Grid<>(CryptoInfoSnapshotEty.class);
        List<CryptoInfoSnapshotEty> cryptoInfoSnapshotEties = cryptoService.showCurrencyChangesHistory("BTCUSDT");
        cryptoInfoSnapshotEtyGrid.setColumns("id", "cryptoName", "price", "timestamp");
        add(cryptoInfoSnapshotEtyGrid);
        cryptoInfoSnapshotEtyGrid.setItems(cryptoInfoSnapshotEties);

    }
}