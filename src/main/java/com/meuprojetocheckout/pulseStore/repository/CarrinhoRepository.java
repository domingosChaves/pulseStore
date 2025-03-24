package com.meuprojetocheckout.pulseStore.repository;

import com.meuprojetocheckout.pulseStore.models.Carrinho;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarrinhoRepository extends JpaRepository<Carrinho, Long> {
}