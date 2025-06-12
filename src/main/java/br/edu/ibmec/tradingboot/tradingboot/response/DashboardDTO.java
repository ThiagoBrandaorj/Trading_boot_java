
package br.edu.ibmec.tradingboot.tradingboot.response;

import java.util.List;
import java.util.Set;

import lombok.Data;



@Data
public class DashboardDTO {
    private double initialBalance;
    private double currentBalance;
    private int totalTrades;
    private double totalPnl;
    private double pnlPercent;
    private double winRate;
    private long winCount;
    private long lossCount;
    private List<TradeDTO> trades;
    private Set<String> trackedTickers;
}