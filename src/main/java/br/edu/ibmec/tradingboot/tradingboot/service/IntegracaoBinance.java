package br.edu.ibmec.tradingboot.tradingboot.service;

import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.*;

@Service
public class IntegracaoBinance{
    private String baseUrl = "https://testnet.binance.vision/api/v3";
    private String apiKey;
    private String secretKey;
    private RestTemplate restTemplate = new RestTemplate();

    public String getTickers(List<String> symbols) {
        String symbolParam = String.join(",", symbols);
        String url = baseUrl + "/ticker/price?symbols=[" + symbolParam + "]";

        HttpHeaders headers = new HttpHeaders();
        headers.set("X-MBX-APIKEY", apiKey);

        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

        return response.getBody();
    }

    public String createMarketOrder(String symbol, double quantity, String side) {
        String url = baseUrl + "/order";
        long timestamp = System.currentTimeMillis();

        Map<String, Object> parameters = new LinkedHashMap<>();
        parameters.put("symbol", symbol);
        parameters.put("side", side);
        parameters.put("type", "MARKET");
        parameters.put("quantity", quantity);
        parameters.put("timestamp", timestamp);

        HttpHeaders headers = new HttpHeaders();
        headers.set("X-MBX-APIKEY", apiKey);
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(parameters, headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);

        return response.getBody();
    }
}