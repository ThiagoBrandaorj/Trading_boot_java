package br.edu.ibmec.tradingboot.tradingboot.response;

import lombok.Data;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Data
public class OrderResponse {
    private String symbol;
    private String orderId;
    private BigDecimal executedQty;
    private String type;
    private String side;
    private List<Map<String, Object>> fills; // Lista de maps ao invés de objetos específicos
}