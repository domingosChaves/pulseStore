package com.meuprojetocheckout.pulseStore.services;

import com.meuprojetocheckout.pulseStore.entity.Pedido;
import com.meuprojetocheckout.pulseStore.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PedidoService {

    @Autowired
    PedidoRepository pedidoRepository;

    public Pedido criarPedido(Pedido pedido){
        return pedidoRepository.save(pedido);
    }

    public Pedido obterPedido(Long pedidoId) {
        return pedidoRepository.findById(pedidoId)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado com ID: " + pedidoId));
    }
}
