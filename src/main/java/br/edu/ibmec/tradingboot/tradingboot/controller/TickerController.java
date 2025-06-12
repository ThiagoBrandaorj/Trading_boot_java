package br.edu.ibmec.tradingboot.tradingboot.controller;

import br.edu.ibmec.tradingboot.tradingboot.model.User;
import br.edu.ibmec.tradingboot.tradingboot.model.UserTrackingTicker;
import br.edu.ibmec.tradingboot.tradingboot.repository.UserRepository;
import br.edu.ibmec.tradingboot.tradingboot.response.TickerResponse;
import br.edu.ibmec.tradingboot.tradingboot.service.IntegracaoBinance;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users/{id}/tickers")
public class TickerController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private IntegracaoBinance binanceService;

    @GetMapping
    public ResponseEntity<?> fetchTickers(@PathVariable("id") Integer userId) {
        Optional<User> userOpt = userRepository.findById(userId);
        if (userOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
        }
        User user = userOpt.get();
        List<String> symbols = new ArrayList<>();
        if (user.getTrackingTickers() != null) {
            for (UserTrackingTicker ticker : user.getTrackingTickers()) {
                symbols.add(ticker.getSymbol());
            }
        }
        // Chama o método getTickers passando os símbolos e as credenciais do usuário.
        String jsonResponse = binanceService.getTickers(symbols, user.getBinanceApiKey(), user.getBinanceSecretKey());

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        try {
            List<TickerResponse> tickers = mapper.readValue(
                    jsonResponse,
                    mapper.getTypeFactory().constructCollectionType(List.class, TickerResponse.class)
            );
            return ResponseEntity.ok(tickers);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error parsing ticker data: " + e.getMessage());
        }
    }
}
