package com.kodilla.stockpricemonitorwithalert.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NBPCryptoPriceDto {
    private String code;
    private List<NBPRatesDto> rates;
}
