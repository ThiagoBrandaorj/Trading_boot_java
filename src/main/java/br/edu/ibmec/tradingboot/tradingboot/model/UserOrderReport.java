package br.edu.ibmec.tradingboot.tradingboot.model;
import jakarta.persistence.*;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.time.LocalDateTime;
@Data
@Entity

public class UserOrderReport {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private int id;

        @Column
        private String symbol;

        @Column
        private double quantity;

        @Column
        private double buyPrice;

        @Column
        private double sellPrice;

        @Column
        private LocalDateTime dtOperation;
        @JsonIgnore
        @ManyToOne
        @JoinColumn(name = "user_id")
        private User user;

}
