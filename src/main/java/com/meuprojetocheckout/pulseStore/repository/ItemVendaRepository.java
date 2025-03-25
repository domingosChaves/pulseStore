package com.meuprojetocheckout.pulseStore.repository;

import com.meuprojetocheckout.pulseStore.entity.ItemVenda;
import com.meuprojetocheckout.pulseStore.entity.Venda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemVendaRepository extends JpaRepository<ItemVenda,Long> {
    List<ItemVenda> findByVenda(Venda venda);

}
