package br.edu.ibmec.tradingboot.tradingboot.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import java.util.List;
import java.util.ArrayList;

@Data
@Entity
@Table(name = "user") // É uma boa prática especificar o nome da tabela
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(unique = true, nullable = false)
    private String login;

    @Column(nullable = false)
    private String password;

    private String binanceApiKey;
    private String binanceSecretKey;
    private Double saldoInicio;

    // RELAÇÃO CORRIGIDA COM UserOrderReport
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonManagedReference("user-report-ref") // O "pai" da relação
    private List<UserOrderReport> orderReports = new ArrayList<>();

    // RELAÇÃO COM UserConfiguration (não precisa de anotações JSON se não houver referência de volta)
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "user_id") // Ligação através da chave estrangeira
    private List<UserConfiguration> configurations = new ArrayList<>();

    // RELAÇÃO COM UserTrackingTicker (não precisa de anotações JSON se não houver referência de volta)
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "user_id") // Ligação através da chave estrangeira
    private List<UserTrackingTicker> trackingTickers = new ArrayList<>();

}