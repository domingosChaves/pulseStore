package com.meuprojetocheckout.pulseStore.controllers;

import com.meuprojetocheckout.pulseStore.models.Carrinho;
import com.meuprojetocheckout.pulseStore.models.ItemCarrinho;
import com.meuprojetocheckout.pulseStore.models.Usuario;
import com.meuprojetocheckout.pulseStore.services.CarrinhoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/carrinhos")
public class CarrinhoController {

    @Autowired
    private CarrinhoService carrinhoService;

    // Endpoint para criar um carrinho
    @PostMapping
    public ResponseEntity<Carrinho> criarCarrinho(@RequestBody Usuario usuario) {
        Carrinho novoCarrinho = carrinhoService.criarCarrinho(usuario);
        return ResponseEntity.ok(novoCarrinho);
    }

    // Endpoint para adicionar um item ao carrinho
    @PostMapping("/{carrinhoId}/itens")
    public ResponseEntity<Void> adicionarItem(@PathVariable Long carrinhoId, @RequestBody ItemCarrinho item) {
        carrinhoService.adicionarItem(carrinhoId, item);
        return ResponseEntity.ok().build();
    }

    // Endpoint para visualizar o carrinho
    @GetMapping("/{carrinhoId}")
    public ResponseEntity<Carrinho> obterCarrinho(@PathVariable Long carrinhoId) {
        Carrinho carrinho = carrinhoService.obterCarrinho(carrinhoId);
        return ResponseEntity.ok(carrinho);
    }

    // Endpoint para remover um item do carrinho
    @DeleteMapping("/{carrinhoId}/itens/{itemId}")
    public ResponseEntity<Void> removerItem(@PathVariable Long carrinhoId, @PathVariable Long itemId) {
        carrinhoService.removerItem(carrinhoId, itemId);
        return ResponseEntity.noContent().build();
    }
}