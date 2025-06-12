package br.edu.ibmec.tradingboot.tradingboot.service;
import br.edu.ibmec.tradingboot.tradingboot.model.User;
import br.edu.ibmec.tradingboot.tradingboot.model.UserOrderReport;
import br.edu.ibmec.tradingboot.tradingboot.repository.UserRepository;
import br.edu.ibmec.tradingboot.tradingboot.response.DashboardDTO;
import br.edu.ibmec.tradingboot.tradingboot.response.TradeDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional; 
import java.util.*;
import java.util.stream.Collectors;

@Service
public class PerformanceService {

    @Autowired
    private UserRepository userRepository;
    @Transactional(readOnly = true) 
    public Optional<DashboardDTO> getDashboardData(int userId) {
        Optional<User> optUser = userRepository.findById(userId);
        if (optUser.isEmpty()) {
            return Optional.empty(); // Usuário não encontrado
        }

        User user = optUser.get();
        DashboardDTO dto = new DashboardDTO();

        // 1. Filtrar apenas trades completos (que têm preço de venda)
        List<UserOrderReport> completedOrders = user.getOrderReports().stream()
                .filter(order -> order.getSellPrice() > 0)
                .sorted(Comparator.comparing(UserOrderReport::getDtOperation))
                .collect(Collectors.toList());

        // 2. Calcular as métricas
        double totalPnl = 0;
        long wins = 0;
        List<TradeDTO> tradesForTable = new ArrayList<>();
        Set<String> uniqueSymbols = new HashSet<>();

        for (UserOrderReport order : completedOrders) {
            // REUTILIZANDO SUA LÓGICA DO OrderController!
            double pnl = (order.getSellPrice() - order.getBuyPrice()) * order.getQuantity();
            totalPnl += pnl;

            if (pnl >= 0) {
                wins++;
            }
            uniqueSymbols.add(order.getSymbol());

            // Criar DTO para a tabela do dashboard
            TradeDTO tradeDto = new TradeDTO();
            tradeDto.setTimestamp(order.getDtOperation());
            tradeDto.setSymbol(order.getSymbol());
            tradeDto.setQuantity(order.getQuantity());
            tradeDto.setPrice(order.getSellPrice());
            tradeDto.setPnl(pnl);
            tradeDto.setSide(pnl >= 0 ? "WIN" : "LOSS"); // Apenas um exemplo de como usar o campo
            tradesForTable.add(tradeDto);
        }

        // 3. Montar o DTO final com todos os dados calculados
        long losses = completedOrders.size() - wins;
        // Supondo um saldo inicial. O ideal é ter isso no seu modelo User.
        double initialBalance =(user.getSaldoInicio() != null) ? user.getSaldoInicio() : 0.0;

        dto.setTotalTrades(completedOrders.size());
        dto.setWinCount(wins);
        dto.setLossCount(losses);
        dto.setTotalPnl(totalPnl);
        dto.setInitialBalance(initialBalance);
        dto.setCurrentBalance(initialBalance + totalPnl);
        dto.setWinRate(completedOrders.isEmpty() ? 0 : ((double) wins / completedOrders.size()) * 100);
        dto.setPnlPercent(initialBalance > 0 ? (totalPnl / initialBalance) * 100 : 0);
        dto.setTrades(tradesForTable);
        dto.setTrackedTickers(uniqueSymbols);

        return Optional.of(dto);
    }
}