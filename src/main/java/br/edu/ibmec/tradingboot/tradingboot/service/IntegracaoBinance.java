package br.edu.ibmec.tradingboot.tradingboot.service;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.Map;
@Service
public class IntegracaoBinance {


        private  String BASE_URL = "https://testnet.binance.vision";
        private  String API_KEY;
        private  String SECRET_KEY ;
        private final RestTemplate restTemplate = new RestTemplate();

        public String getTickers(String symbol) {
            String url = BASE_URL + "/ticker/price?symbol=" + symbol;
            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
            return response.getBody();
        }

        public String createMarketOrder(String symbol, double quantity, String side) {
            String url = BASE_URL + "/order";
            Map<String, Object> parameters = Map.of(
                "symbol", symbol,
                "side", side,       // "BUY" ou "SELL"
                "type", "MARKET",
                "quantity", quantity
        );
            ResponseEntity<String> response = restTemplate.postForEntity(url, parameters, String.class);
            return response.getBody();
    }
}

