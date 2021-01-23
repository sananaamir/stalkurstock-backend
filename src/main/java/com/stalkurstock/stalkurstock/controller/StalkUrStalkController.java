package com.stalkurstock.stalkurstock.controller;

import com.stalkurstock.stalkurstock.dto.FinnHubSymbol;
import com.stalkurstock.stalkurstock.dto.Symbol;
import com.stalkurstock.stalkurstock.entity.StockSymbol;
import com.stalkurstock.stalkurstock.repository.StockSymbolRepository;
import com.stalkurstock.stalkurstock.service.FinnhubSymbolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class StalkUrStalkController {

    @Autowired
    private FinnhubSymbolService finnhubSymbolService;

    @Autowired
    private StockSymbolRepository stockSymbolRepository;


    @GetMapping("/finnhub-symbols")
    public List<FinnHubSymbol> getAllSymbolsFromFinnhub() {
        List<FinnHubSymbol> finnHubSymbols = new ArrayList<>();
        try {
            finnHubSymbols = finnhubSymbolService.getStockSymbols();
        } catch(Exception e) {
            finnHubSymbols = null;
            System.out.println("************ERROR**************************");
            System.out.println(e.getMessage());
        }
        return finnHubSymbols;
    }

    @GetMapping("/symbols")
    public List<Symbol> getAllSymbols() {
        try {
            List<StockSymbol> stockSymbols = (List<StockSymbol>) stockSymbolRepository.findAll();
            List<Symbol> symbols = new ArrayList<>();
            stockSymbols.forEach((s) -> symbols.add(new Symbol(s.getSymbol(), s.getDescription())));
            return symbols;
        } catch (Exception e) {
            System.out.println("*****Couldn't get symbols*******");
            return null;
        }
    }

    @GetMapping("/symbol")
    public Symbol getSymbol(@RequestParam("value") String value) {
        try {
            StockSymbol stockSymbol = stockSymbolRepository.findDistinctBySymbol(value);
            return Symbol.builder()
                    .symbol(stockSymbol.getSymbol())
                    .description(stockSymbol.getDescription())
                    .build();
        } catch (Exception e) {
            System.out.println("**********SYMBOL NOT FOUND***************");
            return null;
        }
    }
}
