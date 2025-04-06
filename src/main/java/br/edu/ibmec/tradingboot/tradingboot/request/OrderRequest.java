package br.edu.ibmec.tradingboot.tradingboot.request;

import lombok.Data;

@Data
public class OrderRequest {
    private String symbol;
    private String side;
    private double quantity;
    private double price;

}
