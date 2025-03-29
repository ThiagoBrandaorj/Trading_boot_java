package br.edu.ibmec.tradingboot.tradingboot.model;

import lombok.Data;
import jakarta.persistence.*;

@Data
@Entity
public class UserTrackingTicker {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String symbol;
}
