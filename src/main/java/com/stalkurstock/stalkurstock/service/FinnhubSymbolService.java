package com.stalkurstock.stalkurstock.service;

import com.stalkurstock.stalkurstock.dto.FinnHubSymbol;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class FinnhubSymbolService {

    private RestTemplate restTemplate;

    @Value("${finnhub.symbol.url}")
    private String finnHubSymbolUrl;

    @Value("${finnhub.api.key}")
    private String finnHubApiKey;

    @Autowired
    public FinnhubSymbolService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<FinnHubSymbol> getStockSymbols() {
        String url = finnHubSymbolUrl + finnHubApiKey;

        ResponseEntity<FinnHubSymbol[]> responseEntity = restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<>(createHttpHeaders()), FinnHubSymbol[].class);

        return Arrays.asList(responseEntity.getBody().clone());
    }

    private HttpHeaders createHttpHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }
}
