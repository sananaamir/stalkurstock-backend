package com.stalkurstock.stalkurstock.controller;

import com.stalkurstock.stalkurstock.dto.FinnHubSymbol;
import com.stalkurstock.stalkurstock.repository.StockSymbolRepository;
import com.stalkurstock.stalkurstock.service.FinnhubSymbolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

import static com.stalkurstock.stalkurstock.StalkurstockApplication.convertSymbolsToStockSymbols;

@RestController
public class StalkUrStalkController {

    @Autowired
    private FinnhubSymbolService finnhubSymbolService;

    @Autowired
    private StockSymbolRepository stockSymbolRepository;


    @PostMapping("/finnhub-symbols")
    @ResponseStatus(HttpStatus.CREATED)
    public List<FinnHubSymbol> insertFinnHubStockSymbols() {
        List<FinnHubSymbol> finnHubSymbols = new ArrayList<>();
        try {
            finnHubSymbols = finnhubSymbolService.getStockSymbols();
            if(!finnHubSymbols.isEmpty()) {
                stockSymbolRepository.deleteAll();
                stockSymbolRepository.saveAll(convertSymbolsToStockSymbols(finnHubSymbols));
            }
            System.out.println("************DATABASE SEEDED***********************");
        } catch(Exception e) {
            finnHubSymbols = null;
            System.out.println("************ERROR**************************");
            System.out.println(e.getMessage());
        }
        return finnHubSymbols;
    }
}
