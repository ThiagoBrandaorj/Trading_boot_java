package br.edu.ibmec.tradingboot.tradingboot.response;

import lombok.Data;
import java.time.LocalDateTime;
@Data
public class TradeDTO {
    private LocalDateTime timestamp;
    private String symbol;
    private String side;
    private double quantity;
    private double price; // Preço de venda
    private double pnl;
}