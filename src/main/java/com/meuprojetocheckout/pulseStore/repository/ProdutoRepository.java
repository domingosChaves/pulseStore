package com.meuprojetocheckout.pulseStore.repository;

import com.meuprojetocheckout.pulseStore.models.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto,Long> {
    List<Produto> findByNomeContaining(String nome);
}
