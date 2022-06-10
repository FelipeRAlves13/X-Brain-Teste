package com.testexbrain.testeXBrain.dto;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VendedorDto {

    @NotEmpty(message = "Não pode estar vazio!")
    private String nome;
    @NotNull
    private Long id;

}
