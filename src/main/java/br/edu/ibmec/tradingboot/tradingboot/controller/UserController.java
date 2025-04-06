package br.edu.ibmec.tradingboot.tradingboot.controller;

import br.edu.ibmec.tradingboot.tradingboot.model.User;
import br.edu.ibmec.tradingboot.tradingboot.model.UserConfiguration;
import br.edu.ibmec.tradingboot.tradingboot.model.UserTrackingTicker;
import br.edu.ibmec.tradingboot.tradingboot.repository.UserConfigurationRepository;
import br.edu.ibmec.tradingboot.tradingboot.repository.UserRepository;
import br.edu.ibmec.tradingboot.tradingboot.repository.UserTrackingTickerRepository;
import br.edu.ibmec.tradingboot.tradingboot.service.IntegracaoBinance;
import br.edu.ibmec.tradingboot.tradingboot.request.OrderRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserConfigurationRepository userConfigRepository;

    @Autowired
    private UserTrackingTickerRepository tickerRepository;

    @PostMapping
    public ResponseEntity<?> registerNewUser(@RequestBody User newUser) {
        try {
            User savedUser = userRepository.save(newUser);
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(savedUser);
        } catch (Exception e) {
            return ResponseEntity
                    .badRequest()
                    .body("Error creating user: " + e.getMessage());
        }
    }

    @GetMapping("/{userId}")
    public ResponseEntity<?> getUser(@PathVariable Integer userId) {
        return userRepository.findById(userId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<User>> getUsers() {
        try {
            List<User> users = userRepository.findAll();

            if(users.isEmpty()) {
                return ResponseEntity.noContent().build();
            }

            return ResponseEntity.ok(users);
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        }
    }

    @PostMapping("/{userId}/configurations")
    public ResponseEntity<?> setupUserConfiguration(
            @PathVariable Integer userId,
            @RequestBody UserConfiguration config) {

        if (!userRepository.existsById(userId)) {
            return ResponseEntity.notFound().build();
        }

        User user = userRepository.findById(userId).orElseThrow();
        if (!user.getConfigurations().isEmpty()) {
            return ResponseEntity
                    .badRequest()
                    .body("Usuário já possui uma configuração, delete a configuração existente para adcionar outra.");
        }

        UserConfiguration savedConfig = userConfigRepository.save(config);

        user.getConfigurations().add(savedConfig);
        userRepository.save(user);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(savedConfig);
    }

    @PostMapping("/{userId}/tracked-tickers")
    public ResponseEntity<?> addTrackedTicker(
            @PathVariable Integer userId,
            @RequestBody UserTrackingTicker ticker) {

        if (!userRepository.existsById(userId)) {
            return ResponseEntity.notFound().build();
        }

        UserTrackingTicker savedTicker = tickerRepository.save(ticker);

        userRepository.findById(userId).ifPresent(user -> {
            user.getTrackingTickers().add(savedTicker);
            userRepository.save(user);
        });

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(savedTicker);
    }

    @Transactional
    @DeleteMapping("/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable Integer userId) {
        try {
            return userRepository.findById(userId)
                    .map(user -> {
                        userConfigRepository.deleteAll(user.getConfigurations());
                        tickerRepository.deleteAll(user.getTrackingTickers());
                        userRepository.delete(user);

                        return ResponseEntity.ok().build();
                    })
                    .orElseGet(() -> ResponseEntity.notFound().build());
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error deleting user: " + e.getMessage());
        }
    }
    @Autowired
    private IntegracaoBinance binanceService; // ou troque pelo nome correto


}