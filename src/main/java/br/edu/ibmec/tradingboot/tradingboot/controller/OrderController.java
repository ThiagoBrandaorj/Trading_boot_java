package br.edu.ibmec.tradingboot.tradingboot.controller;

import br.edu.ibmec.tradingboot.tradingboot.model.User;
import br.edu.ibmec.tradingboot.tradingboot.model.UserOrderReport;
import br.edu.ibmec.tradingboot.tradingboot.repository.UserOrderRepository;
import br.edu.ibmec.tradingboot.tradingboot.repository.UserRepository;
import br.edu.ibmec.tradingboot.tradingboot.request.OrderRequest;
import br.edu.ibmec.tradingboot.tradingboot.response.OrderResponse;
import br.edu.ibmec.tradingboot.tradingboot.service.IntegracaoBinance;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping("/users/{id}/order")
public class OrderController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserOrderRepository orderRepository;

    @Autowired
    private IntegracaoBinance binanceService;

    @PostMapping
    public ResponseEntity<?> sendOrder(@PathVariable("id") int id, @RequestBody OrderRequest request) {
        Optional<User> optUser = userRepository.findById(Integer.valueOf(id));

        if (optUser.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado.");
        }

        User user = optUser.get();

        try {
            // Enviar ordem para Binance com as credenciais do usuário
            String result = binanceService.createMarketOrder(
                    request.getSymbol(),
                    request.getQuantity(),
                    request.getSide(),
                    user.getBinanceApiKey(),
                    user.getBinanceSecretKey()
            );

            // Parsear JSON de retorno para OrderResponse
            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            OrderResponse response = mapper.readValue(result, OrderResponse.class);

            if ("BUY".equalsIgnoreCase(request.getSide())) {
                UserOrderReport report = new UserOrderReport();
                report.setSymbol(request.getSymbol());
                report.setQuantity(request.getQuantity());
                report.setBuyPrice(Double.parseDouble(response.getFills().get(0).get("price").toString()));
                report.setDtOperation(LocalDateTime.now());
                report.setUser(user); // associa o usuário

                orderRepository.save(report);
                user.getOrderReports().add(report);
                userRepository.save(user);

            } else if ("SELL".equalsIgnoreCase(request.getSide())) {
                UserOrderReport orderToUpdate = null;

                for (UserOrderReport report : user.getOrderReports()) {
                    if (report.getSymbol().equals(request.getSymbol()) && report.getSellPrice() == 0) {
                        orderToUpdate = report;
                        break;
                    }
                }

                if (orderToUpdate == null) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Não foi encontrada uma compra anterior para esse ativo.");
                }

                orderToUpdate.setSellPrice(Double.parseDouble(response.getFills().get(0).get("price").toString()));
                orderRepository.save(orderToUpdate);
            }

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro ao processar ordem: " + e.getMessage());
        }
    }
}
