package br.edu.ibmec.tradingboot.tradingboot.service;

import com.binance.connector.client.SpotClient;
import com.binance.connector.client.impl.SpotClientImpl;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
@Data
public class IntegracaoBinance {
    private String baseUrl = "https://testnet.binance.vision";
    private String apiKey;
    private String secretKey;

    // Método para obter tickers
    public String getTickers(List<String> symbols) {
        SpotClient client = new SpotClientImpl(this.apiKey, this.secretKey, this.baseUrl);
        Map<String, Object> parameters = new LinkedHashMap<>();
        parameters.put("symbols", symbols); // A biblioteca aceita diretamente a lista de símbolos
        String result = client.createMarket().ticker(parameters);
        return result;
    }

    // Método para criar uma ordem de mercado
    public String createMarketOrder(String symbol, double quantity, String side) {
        SpotClient client = new SpotClientImpl(this.apiKey, this.secretKey, this.baseUrl);
        Map<String, Object> parameters = new LinkedHashMap<>();
        parameters.put("symbol", symbol);
        parameters.put("side", side);
        parameters.put("type", "MARKET");
        parameters.put("quantity", quantity);
        String result = client.createTrade().newOrder(parameters);
        return result;
    }
}