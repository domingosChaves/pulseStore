package com.meuprojetocheckout.pulseStore.controllers;

import com.meuprojetocheckout.pulseStore.entity.Pedido;
import com.meuprojetocheckout.pulseStore.services.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/pedido")
public class PedidoController {

    @Autowired
    PedidoService pedidoService;

    @PostMapping
    public ResponseEntity<Pedido> criarPedido(@RequestBody Pedido pedido){
        Pedido novoPedido = pedidoService.criarPedido(pedido);
        return ResponseEntity.ok(novoPedido);
    }

    @GetMapping("/{pedidoId}")
    public ResponseEntity<Pedido> obterPedido(@PathVariable Long pedidoId){
        Pedido pedido = pedidoService.obterPedido(pedidoId);
        return ResponseEntity.ok(pedido);
    }
}
