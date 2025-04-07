package br.edu.ibmec.tradingboot.tradingboot.response;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class TickerResponse {
    private String symbol;
    private BigDecimal price;
    // Adicione outros campos conforme o retorno da API da Binance se necessário.
}
