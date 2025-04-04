package com.kodilla.stockpricemonitorwithalert.dto.binance;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BinanceCryptoPriceDto {

    private String symbol;
    private BigDecimal price;


}
