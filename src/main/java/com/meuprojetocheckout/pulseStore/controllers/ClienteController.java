package com.meuprojetocheckout.pulseStore.controllers;

import com.meuprojetocheckout.pulseStore.entity.Cliente;
import com.meuprojetocheckout.pulseStore.services.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cliente")
public class ClienteController {

    @Autowired
    ClienteService clienteService;

    @PostMapping
    public ResponseEntity<Cliente> criarCliente(@RequestBody Cliente cliente){
        Cliente novoCliente  = clienteService.criarCliente(cliente);
        return ResponseEntity.ok(novoCliente);
    }

    @GetMapping("/{clienteId}")
    public  ResponseEntity<Cliente> obterCliente(@PathVariable Long clienteId){
        Cliente cliente = clienteService.obterCliente(clienteId);
        return ResponseEntity.ok(cliente);
    }

}
