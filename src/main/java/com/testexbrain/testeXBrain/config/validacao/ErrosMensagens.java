package com.testexbrain.testeXBrain.config.validacao;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrosMensagens {

    private String campo;
    private String erro;

    public ErrosMensagens (String erro) {
        this.erro = erro;
    }

}
