package com.meuprojetocheckout.pulseStore.repository;

import com.meuprojetocheckout.pulseStore.models.NotaFiscal;
import com.meuprojetocheckout.pulseStore.models.Venda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotaFiscalRepository extends JpaRepository<NotaFiscal,Long> {
    List<NotaFiscal> findByVenda(Venda venda);

}
