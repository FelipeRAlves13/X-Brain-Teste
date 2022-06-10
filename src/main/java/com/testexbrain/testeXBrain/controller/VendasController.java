package com.testexbrain.testeXBrain.controller;

import com.testexbrain.testeXBrain.dto.VendaDto;
import com.testexbrain.testeXBrain.service.VendaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("venda")
public class VendasController {

    @Autowired
    private VendaService service;

    @PostMapping
    public void cadastrar(@RequestBody @Valid VendaDto dadosVenda){
        service.cadastrar(dadosVenda);
    }

    @GetMapping
    public List<VendaDto> findAll(){
        return service.findAll();
    }

    @GetMapping("{id}")
    public VendaDto getById(@PathVariable Long id){
        return service.getById(id);
    }

    @PutMapping("{id}")
    public void atualizarVenda(@PathVariable Long id, @RequestBody @Valid VendaDto vendaDto){
        service.atualizarVenda(id, vendaDto);
    }

}
