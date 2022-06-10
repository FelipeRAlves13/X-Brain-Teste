package com.testexbrain.testeXBrain.controllerTest;

import com.testexbrain.testeXBrain.dto.VendaDto;
import com.testexbrain.testeXBrain.service.VendaService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import javax.persistence.EntityNotFoundException;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class VendaTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private VendaService vendaService;

    @Test
    public void validaSeAVendaEstaSendoCadastrada() throws Exception {
        mockMvc.perform(post("/venda")
                        .content("{\"idVendedor\":\"1\"}")
                        .content("{\"valor\":\"2\"}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void validaSeOSistemaListaTodasVendasCadastradas() throws Exception {
        var vendaDto = new VendaDto();
        vendaDto.setValor(200F);
        vendaDto.setIdVenda(1L);

        when(vendaService.findAll()).thenReturn(List.of(vendaDto));

        mockMvc.perform(get("/venda"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].valor", is(200.0)))
                .andExpect(jsonPath("$.[0].idVenda", is(1)));
    }

    @Test
    public void validaSeOSistemaListaVendaCadastradaPorID() throws Exception {
        var vendaDto = new VendaDto();
        vendaDto.setValor(200F);
        vendaDto.setIdVenda(1L);

        when(vendaService.getById(1L)).thenReturn(vendaDto);

        mockMvc.perform(get("/venda/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.valor", is(200.0)))
                .andExpect(jsonPath("$.idVenda", is(1)));
    }

    @Test
    public void validaSeOSistemaEstaAtualizandoAVenda() throws Exception {
        mockMvc.perform(put("/venda/{id}", 1)
                        .content("{\"valor\":\"100\"}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void validaSeAoCadastrarComOsDadosVazioRetornaErro() throws Exception {
        mockMvc.perform(post("/venda")
                        .content("{\"idVendedor\":\"\"}")
                        .content("{\"valor\":\"\"}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void validaExceptionQuandoVendaNaoLocalizado() throws Exception {
        doThrow(EntityNotFoundException.class).when(vendaService).getById(1L);

        mockMvc.perform(get("/venda/{id}", 1))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void validaExceptionQuandoVendaNaoLocalizadoAtualizar() throws Exception {
        var vendaDto = new VendaDto();
        vendaDto.setValor(200F);
        vendaDto.setIdVenda(1L);

        doThrow(EntityNotFoundException.class).when(vendaService).atualizarVenda(1L,vendaDto);

        mockMvc.perform(put("/venda/{id}", 1))
                .andExpect(status().isBadRequest());
    }
}
