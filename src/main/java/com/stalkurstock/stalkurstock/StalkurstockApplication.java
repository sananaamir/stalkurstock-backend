package com.stalkurstock.stalkurstock;

import com.stalkurstock.stalkurstock.dto.FinnHubSymbol;
import com.stalkurstock.stalkurstock.entity.StockSymbol;
import com.stalkurstock.stalkurstock.repository.StockSymbolRepository;
import com.stalkurstock.stalkurstock.service.FinnhubSymbolService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class StalkurstockApplication {

	public static void main(String[] args) {
		SpringApplication.run(StalkurstockApplication.class, args);
	}

	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}

	@Bean
	public CommandLineRunner run(StockSymbolRepository stockSymbolRepository, FinnhubSymbolService finnhubSymbolService) throws Exception {
		return args -> {
			if(stockSymbolRepository.count() == 0) {
				List<FinnHubSymbol> finnHubSymbols = finnhubSymbolService.getStockSymbols();

				stockSymbolRepository.saveAll(convertSymbolsToStockSymbols(finnHubSymbols));
				System.out.println("************DATABASE SEEDED***********************");
			} else {
				System.out.println("***********DATABASE ALREADY SEEDED****************");
			}
		};
	}

	public static List<StockSymbol> convertSymbolsToStockSymbols(List<FinnHubSymbol> finnHubSymbols) {
		List<StockSymbol> stockSymbols = new ArrayList<>();

		for (FinnHubSymbol finnHubSymbol : finnHubSymbols) {
			stockSymbols.add(new StockSymbol(finnHubSymbol.getSymbol(), finnHubSymbol.getDescription()));
		}
		return stockSymbols;
	}
}
