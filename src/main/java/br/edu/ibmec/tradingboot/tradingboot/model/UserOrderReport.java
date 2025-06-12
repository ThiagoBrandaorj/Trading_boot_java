package br.edu.ibmec.tradingboot.tradingboot.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
public class UserOrderReport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String symbol;
    private double quantity;
    private double buyPrice;
    private double sellPrice;
    private LocalDateTime dtOperation;

    // RELAÇÃO CORRIGIDA COM User
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonBackReference("user-report-ref") // O "filho" da relação com o mesmo nome
    private User user;

}