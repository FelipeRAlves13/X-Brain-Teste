package com.testexbrain.testeXBrain.repository;

import com.testexbrain.testeXBrain.model.Vendedor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface VendedorRepository extends JpaRepository<Vendedor, Long> {

    @Query("select distinct v from Vendedor v inner join v.vendas vendas where vendas.dataDaVenda between ?1 and ?2")
    List<Vendedor> findByVendasDataDaVendaBetween(LocalDateTime dataInicio, LocalDateTime dataFinal);
}
