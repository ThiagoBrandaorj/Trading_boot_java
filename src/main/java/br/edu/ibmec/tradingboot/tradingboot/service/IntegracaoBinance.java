package br.edu.ibmec.tradingboot.tradingboot.service;

import com.binance.connector.client.impl.SpotClientImpl;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;

@Service
public class IntegracaoBinance {

    // URL da Binance Testnet (testes de trade)
    private static final String BINANCE_TESTNET_URL = "https://testnet.binance.vision";

    public String createMarketOrder(String symbol, double quantity, String side, String apiKey, String secretKey) {
        // Usa o client com a URL de testnet
        SpotClientImpl client = new SpotClientImpl(apiKey, secretKey, BINANCE_TESTNET_URL);

        LinkedHashMap<String, Object> parameters = new LinkedHashMap<>();
        parameters.put("symbol", symbol);
        parameters.put("side", side);
        parameters.put("type", "MARKET");
        parameters.put("quantity", Double.valueOf(quantity));

        return client.createTrade().newOrder(parameters);
    }
}
