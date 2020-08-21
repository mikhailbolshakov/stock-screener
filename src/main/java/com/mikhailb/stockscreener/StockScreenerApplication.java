package com.mikhailb.stockscreener;

import com.mikhailb.stockscreener.configuration.StorageProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(StorageProperties.class)
public class StockScreenerApplication {

	public static void main(String[] args) {
		SpringApplication.run(StockScreenerApplication.class, args);
	}

}
