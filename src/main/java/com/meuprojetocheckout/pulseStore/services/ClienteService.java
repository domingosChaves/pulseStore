package com.meuprojetocheckout.pulseStore.services;

import com.meuprojetocheckout.pulseStore.entity.Cliente;
import com.meuprojetocheckout.pulseStore.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClienteService {

    @Autowired
    ClienteRepository clienteRepository;

    public Cliente criarCliente(Cliente cliente){
        return clienteRepository.save(cliente);
    }

    public Cliente obterCliente(Long id){
        return clienteRepository.findById(id).orElseThrow(() -> new RuntimeException("Cliente não encontrado"));
    }

}
