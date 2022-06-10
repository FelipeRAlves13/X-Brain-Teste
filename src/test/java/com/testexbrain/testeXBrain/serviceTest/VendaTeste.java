package com.testexbrain.testeXBrain.serviceTest;

import com.testexbrain.testeXBrain.dto.VendaDto;
import com.testexbrain.testeXBrain.model.Venda;
import com.testexbrain.testeXBrain.model.Vendedor;
import com.testexbrain.testeXBrain.repository.VendaRepository;
import com.testexbrain.testeXBrain.repository.VendedorRepository;
import com.testexbrain.testeXBrain.service.VendaService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class VendaTeste {

    @Mock
    private VendaRepository vendaRepository;

    @InjectMocks
    private VendaService vendaService;

    @Mock
    private VendedorRepository vendedorRepository;

    @Test
    public void validaCadastrarVenda() {
        var vendedor = new Vendedor();
        vendedor.setNome("Vendedor Um");
        vendedor.setId(1L);

        var localDateTime = LocalDateTime.now();

        var vendaDto = new VendaDto();
        vendaDto.setIdVendedor(vendedor.getId());
        vendaDto.setValor(200F);
        vendaDto.setDataDaVenda(localDateTime);

        var venda = new Venda();
        venda.setId(vendaDto.getIdVenda());
        venda.setValor(200F);
        venda.setVendedor(vendedor);
        venda.setDataDaVenda(localDateTime.withNano(0));

        when(vendedorRepository.getById(1L)).thenReturn(venda.getVendedor());
        when(vendaRepository.save(venda)).thenReturn(venda);

        vendaService.cadastrar(vendaDto);

        verify(vendaRepository, times(1)).save(venda);
        verify(vendedorRepository, times(1)).getById(1L);
    }

    @Test
    public void validaListarTodasVendas() {
        var localDateTime = LocalDateTime.now();
        var vendedor = new Vendedor();
        vendedor.setNome("Vendedor Um");
        vendedor.setId(1L);

        var vendaUm = new Venda();
        vendaUm.setId(1L);
        vendaUm.setValor(200F);
        vendaUm.setDataDaVenda(localDateTime);
        vendaUm.setVendedor(vendedor);

        var vendaDois = new Venda();
        vendaDois.setId(2L);
        vendaDois.setValor(200F);
        vendaDois.setDataDaVenda(localDateTime);
        vendaDois.setVendedor(vendedor);

        var vendaUmDto = new VendaDto();
        vendaUmDto.setIdVenda(1L);
        vendaUmDto.setValor(200F);
        vendaUmDto.setDataDaVenda(localDateTime);
        vendaUmDto.setNomeVendedor(vendedor.getNome());
        vendaUmDto.setIdVendedor(vendedor.getId());

        var vendaDoisDto = new VendaDto();
        vendaDoisDto.setIdVenda(2L);
        vendaDoisDto.setValor(200F);
        vendaDoisDto.setDataDaVenda(localDateTime);
        vendaDoisDto.setNomeVendedor(vendedor.getNome());
        vendaDoisDto.setIdVendedor(vendedor.getId());

        var listaVendas = List.of(vendaUm, vendaDois);
        var listaVendasDto = List.of(vendaUmDto, vendaDoisDto);

        when(vendaRepository.findAll()).thenReturn(listaVendas);

        var vendasLista = vendaService.findAll();

        assertEquals(listaVendasDto.size(), vendasLista.size());
        assertEquals(listaVendasDto.get(0).getIdVenda(), vendasLista.get(0).getIdVenda());
        assertEquals(listaVendasDto.get(0).getValor(), vendasLista.get(0).getValor());
        assertEquals(listaVendasDto.get(1).getIdVenda(), vendasLista.get(1).getIdVenda());
        assertEquals(listaVendasDto.get(1).getValor(), vendasLista.get(1).getValor());
    }

    @Test
    public void validaListarVendaPorId() {
        var localDateTime = LocalDateTime.now();
        var vendedor = new Vendedor();
        vendedor.setNome("Vendedor Um");
        vendedor.setId(1L);
        var vendaUm = new Venda();
        vendaUm.setId(1L);
        vendaUm.setValor(200F);
        vendaUm.setDataDaVenda(localDateTime);
        vendaUm.setVendedor(vendedor);

        when(vendaRepository.getById(1L)).thenReturn(vendaUm);

        var vendaDetalhar = vendaService.getById(1L);

        assertEquals(vendaDetalhar.getIdVenda(), vendaUm.getId());
        assertEquals(vendaDetalhar.getValor(), vendaUm.getValor());
        assertEquals(vendaDetalhar.getNomeVendedor(), vendaUm.getVendedor().getNome());
    }

    @Test
    public void validaAtualizarVenda() {
        var localDateTime = LocalDateTime.now();
        var vendedor = new Vendedor();
        vendedor.setNome("Vendedor Um");
        vendedor.setId(1L);
        var vendaUm = new Venda();
        vendaUm.setId(1L);
        vendaUm.setValor(200F);
        vendaUm.setDataDaVenda(localDateTime);
        vendaUm.setVendedor(vendedor);
        var vendaUmDto = new VendaDto();
        vendaUmDto.setIdVenda(1L);
        vendaUmDto.setValor(200F);
        vendaUmDto.setDataDaVenda(localDateTime);
        vendaUmDto.setNomeVendedor(vendedor.getNome());
        vendaUmDto.setIdVendedor(vendedor.getId());

        when(vendaRepository.getById(1L)).thenReturn(vendaUm);

        vendaService.atualizarVenda(1L, vendaUmDto);

        assertEquals(vendaUm.getId(), vendaUmDto.getIdVenda());
        assertEquals(vendaUm.getValor(), vendaUmDto.getValor());
        verify(vendaRepository, times(1)).save(vendaUm);
    }
}
