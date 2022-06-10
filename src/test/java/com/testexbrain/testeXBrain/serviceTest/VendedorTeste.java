package com.testexbrain.testeXBrain.serviceTest;

import com.testexbrain.testeXBrain.dto.VendedorDto;
import com.testexbrain.testeXBrain.model.Vendedor;
import com.testexbrain.testeXBrain.repository.VendedorRepository;
import com.testexbrain.testeXBrain.service.VendedorService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class VendedorTeste {
    @Mock
    private VendedorRepository vendedorRepository;

    @InjectMocks
    private VendedorService vendedorService;

    @Test
    public void validaCadastrarVendedor() {
        var vendedorDto = new VendedorDto();
        vendedorDto.setNome("Vendedor Um");

        var vendedor = new Vendedor();
        vendedor.setNome("Vendedor Um");

        vendedorService.cadastrar(vendedorDto);

        verify(vendedorRepository, times(1)).save(vendedor);
    }

    @Test
    public void validaListarTodosVendedores() {
        var vendedorUm = new Vendedor();
        vendedorUm.setNome("Vendedor Um");
        vendedorUm.setId(1L);
        var vendedorDois = new Vendedor();
        vendedorDois.setNome("Vendedor Dois");
        vendedorDois.setId(2L);

        var vendedorUmDto = new VendedorDto();
        vendedorUmDto.setNome("Vendedor Um");
        vendedorUmDto.setId(1L);
        var vendedorDoisDto = new VendedorDto();
        vendedorDoisDto.setNome("Vendedor Dois");
        vendedorDoisDto.setId(2L);

        var listaVendedores = List.of(vendedorUm, vendedorDois);
        var listaVendedoresDto = List.of(vendedorUmDto, vendedorDoisDto);

        when(vendedorRepository.findAll()).thenReturn(listaVendedores);
        var vendedoresLista = vendedorService.findAll();

        assertEquals(listaVendedoresDto.size(), vendedoresLista.size());
        assertEquals(listaVendedoresDto.get(0).getNome(), vendedoresLista.get(0).getNome());
        assertEquals(listaVendedoresDto.get(1).getNome(), vendedoresLista.get(1).getNome());
    }

    @Test
    public void validaListarVendedorPorId() {
        var vendedorUm = new Vendedor();
        vendedorUm.setNome("Vendedor Um");
        vendedorUm.setId(1L);

        when(vendedorRepository.getById(1L)).thenReturn(vendedorUm);

        var vendedorDetalhar = vendedorService.getById(1L);

        assertEquals(vendedorDetalhar.getNome(), vendedorUm.getNome());
        assertEquals(vendedorDetalhar.getId(), vendedorUm.getId());
    }

    @Test
    public void validaAtualizarVendedor() {
        var vendedorUm = new Vendedor();
        vendedorUm.setNome("Vendedor Um");
        vendedorUm.setId(1L);

        var vendedorUmDto = new VendedorDto();
        vendedorUmDto.setNome("Vendedor Um");
        vendedorUmDto.setId(1L);

        when(vendedorRepository.getById(1L)).thenReturn(vendedorUm);

        vendedorService.atualizarVendedor(1L,vendedorUmDto);

        assertEquals(vendedorUm.getNome(), vendedorUmDto.getNome());
        assertEquals(vendedorUm.getId(), vendedorUmDto.getId());
    }
}
