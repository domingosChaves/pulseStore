package com.meuprojetocheckout.pulseStore.repository;

import com.meuprojetocheckout.pulseStore.entity.Transportadora;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransportadoraRepository extends JpaRepository<Transportadora,Long> {
}
