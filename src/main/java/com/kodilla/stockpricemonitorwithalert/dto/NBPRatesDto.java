package com.kodilla.stockpricemonitorwithalert.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class NBPRatesDto {
    private String no;
    private String effectiveDate;
    private double mid;

    @Override
    public String toString() {
        return "mid: " + mid + ", effectiveDate: " + effectiveDate;
    }
}
