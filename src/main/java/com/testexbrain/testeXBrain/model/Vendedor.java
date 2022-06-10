package com.testexbrain.testeXBrain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Vendedor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String nome;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "vendedor", orphanRemoval = true)
    private List<Venda> vendas;

    public Vendedor(String nome){
        this.nome = nome;
    }
}
