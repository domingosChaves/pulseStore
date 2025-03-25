package com.meuprojetocheckout.pulseStore.services;

import com.meuprojetocheckout.pulseStore.entity.Transportadora;
import com.meuprojetocheckout.pulseStore.repository.TransportadoraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransportadoraService {

    @Autowired
    TransportadoraRepository transportadoraRepository;

    public Transportadora criarTransportadora(Transportadora transportadora){
        return transportadoraRepository.save(transportadora);
    }

    public List<Transportadora> obterTransportadora(){
        return transportadoraRepository.findAll();
    }
}
