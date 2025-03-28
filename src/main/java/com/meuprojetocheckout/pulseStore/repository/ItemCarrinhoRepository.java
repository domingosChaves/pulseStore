package com.meuprojetocheckout.pulseStore.repository;

import com.meuprojetocheckout.pulseStore.models.ItemCarrinho;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemCarrinhoRepository extends JpaRepository<ItemCarrinho, Long> { }