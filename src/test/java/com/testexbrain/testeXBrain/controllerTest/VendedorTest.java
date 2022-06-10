package com.testexbrain.testeXBrain.controllerTest;

import com.testexbrain.testeXBrain.dto.RelatorioVendaDto;
import com.testexbrain.testeXBrain.dto.VendedorDto;
import com.testexbrain.testeXBrain.service.VendedorService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class VendedorTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private VendedorService vendedorService;

    @Test
    public void validaSeOVendedorEstaSendoCadastrado() throws Exception {
        mockMvc.perform(post("/vendedor")
                        .content("{\"nome\":\"Felipe\"}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void validaSeOSistemaListaTodosVendedoresCadastrados() throws Exception {
        var vendedorDto = new VendedorDto();
        vendedorDto.setNome("Vendedor Um");

        when(vendedorService.findAll()).thenReturn(List.of(vendedorDto));

        mockMvc.perform(get("/vendedor"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].nome", is("Vendedor Um")));
    }

    @Test
    public void validaSeOSistemaListaVendedoresCadastradosPorID() throws Exception {
        var vendedorDto = new VendedorDto();
        vendedorDto.setNome("Vendedor Um");
        vendedorDto.setId(1L);

        when(vendedorService.getById(1L)).thenReturn(vendedorDto);

        mockMvc.perform(get("/vendedor/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome", is("Vendedor Um")))
                .andExpect(jsonPath("$.id", is(1)));
    }

    @Test
    public void validaSeOSistemaEstaAtualizandoOVendedor() throws Exception {
        mockMvc.perform(put("/vendedor/{id}", 1)
                        .content("{\"nome\":\"Felipe Atualizado\"}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void validaSeAoCadastrarComONomeVazioRetornaErro() throws Exception {
        mockMvc.perform(post("/vendedor")
                        .content("{\"nome\":\"\"}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void validaRelatorioPorData() throws Exception {
        var vendedorDto = new VendedorDto();
        vendedorDto.setNome("Vendedor Um");
        vendedorDto.setId(1L);

        var relatorio = new RelatorioVendaDto();
        relatorio.setId(vendedorDto.getId());
        relatorio.setNome(vendedorDto.getNome());
        relatorio.setTotalVendas(4);
        relatorio.setMediaVendasDiarias(4);

        LocalDate dataInicial = LocalDate.of(2022, 6, 10);
        LocalDate dataFinal = LocalDate.of(2022, 6, 10);

        when(vendedorService.relatorio(dataInicial, dataFinal)).thenReturn(List.of(relatorio));

        mockMvc.perform(get("/vendedor/relatorio?dataInicio=10/06/2022&dataFinal=10/06/2022"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].nome", is("Vendedor Um")))
                .andExpect(jsonPath("$.[0].id", is(1)))
                .andExpect(jsonPath("$.[0].totalVendas", is(4.0)))
                .andExpect(jsonPath("$.[0].mediaVendasDiarias", is(4.0)));
    }

    @Test
    public void validaExceptionQuandoVendedorNaoLocalizado() throws Exception {

        doThrow(EntityNotFoundException.class).when(vendedorService).getById(1L);

        mockMvc.perform(get("/vendedor/{id}", 1))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void validaExceptionQuandoVendedorNaoLocalizadoAtualizar() throws Exception {
        var vendedorDto = new VendedorDto();
        vendedorDto.setNome("Vendedor Um");
        vendedorDto.setId(1L);

        doThrow(EntityNotFoundException.class).when(vendedorService).atualizarVendedor(1L, vendedorDto);

        mockMvc.perform(put("/vendedor/{id}", 1))
                .andExpect(status().isBadRequest());
    }

}
