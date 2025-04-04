package com.kodilla.stockpricemonitorwithalert;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.converter.json.GsonBuilderUtils;

@SpringBootApplication
public class StockPriceMonitorWithAlertApplication {

	public static void main(String[] args) {
		SpringApplication.run(StockPriceMonitorWithAlertApplication.class, args);
	}
}
