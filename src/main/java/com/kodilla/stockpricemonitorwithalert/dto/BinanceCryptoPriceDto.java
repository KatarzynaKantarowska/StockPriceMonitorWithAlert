package com.kodilla.stockpricemonitorwithalert.dto;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BinanceCryptoPriceDto {
    private String symbol;
    private BigDecimal price;
    private BigDecimal pricePln;


}
