package com.testexbrain.testeXBrain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Venda {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(nullable = false)
    private LocalDateTime dataDaVenda;
    @Column
    private Float valor;
    @ManyToOne(fetch = FetchType.LAZY)
    private Vendedor vendedor;

    public LocalDateTime dataVendaAtual() {
        return java.time.LocalDateTime.now()
                .withNano(0);
    }
}
