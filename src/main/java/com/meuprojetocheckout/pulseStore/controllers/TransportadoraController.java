package com.meuprojetocheckout.pulseStore.controllers;

import com.meuprojetocheckout.pulseStore.entity.Transportadora;
import com.meuprojetocheckout.pulseStore.services.TransportadoraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transportadora")
public class TransportadoraController {

    @Autowired
    TransportadoraService transportadoraService;

    @PostMapping
    public ResponseEntity<Transportadora> criarTransportadora(@RequestBody Transportadora transportadora){
        Transportadora novaTransportadora = transportadoraService.criarTransportadora(transportadora);
        return ResponseEntity.ok(novaTransportadora);
    }

    @GetMapping
    public ResponseEntity<List<Transportadora>> obterTransportadora(){
        List<Transportadora> transportadoras = transportadoraService.obterTransportadora();
        return ResponseEntity.ok(transportadoras);
    }
}
