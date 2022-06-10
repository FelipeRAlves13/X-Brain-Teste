package com.testexbrain.testeXBrain.repository;

import com.testexbrain.testeXBrain.model.Venda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface VendaRepository extends JpaRepository<Venda, Long> {

    @Query("select v from Venda v where v.vendedor.id = ?1 and v.dataDaVenda between ?2 and ?3")
    List<Venda> findByVendedorIdAndDataDaVendaBetween(Long id, LocalDateTime dataInicio, LocalDateTime dataFinal);
}
