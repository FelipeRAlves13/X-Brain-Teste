package com.testexbrain.testeXBrain.dto;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VendaDto {

    private Long idVenda;
    @NotNull
    private Long idVendedor;
    private String nomeVendedor;
    private LocalDateTime dataDaVenda;
    @NotNull
    private Float valor;

}
