package br.edu.ibmec.tradingboot.tradingboot.request;

import lombok.Data;

@Data
public class OcoOrderRequest {
    private String symbol;
    private double quantity;
    private String side;
    private double limitprice;
    private double stopprice;
    private double stoplimitprice;
}

