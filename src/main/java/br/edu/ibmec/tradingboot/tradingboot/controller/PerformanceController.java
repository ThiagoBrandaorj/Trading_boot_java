
package br.edu.ibmec.tradingboot.tradingboot.controller;

import br.edu.ibmec.tradingboot.tradingboot.response.DashboardDTO;
import br.edu.ibmec.tradingboot.tradingboot.service.PerformanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/performance")
@CrossOrigin(origins = "*")
public class PerformanceController {

    @Autowired
    private PerformanceService performanceService;

    @GetMapping("/{id}")
    public ResponseEntity<?> getPerformanceData(@PathVariable("id") int id) {
        Optional<DashboardDTO> dashboardData = performanceService.getDashboardData(id);

        // Se o serviço retornou os dados, envia como 'OK'. Senão, 'Não Encontrado'.
        return dashboardData.<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body("Performance data not found for user."));
    }
}