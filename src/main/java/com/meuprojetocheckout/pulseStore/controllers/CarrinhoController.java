package com.meuprojetocheckout.pulseStore.controllers;

import com.meuprojetocheckout.pulseStore.entity.*;
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
    public ResponseEntity<Carrinho> criarCarrinho(@RequestBody CarrinhoRequest carrinhoRequest) {
        Long usuarioId = carrinhoRequest.getUsuarioId();
        Carrinho novoCarrinho = carrinhoService.criarCarrinho(usuarioId);
        return ResponseEntity.ok(novoCarrinho);
    }

    // Endpoint para adicionar um item ao carrinho
    @PostMapping("/{carrinhoId}/itens/{produtoId}/{quantidade}")
    public ResponseEntity<Void> adicionarItem(@PathVariable Long carrinhoId, @PathVariable Long produtoId,@PathVariable int quantidade) {

        Carrinho carrinho = new Carrinho();
        carrinho.setId(carrinhoId);

        Produto produto = new Produto();
        produto.setId(produtoId);

        ItemCarrinho item = new ItemCarrinho();
        item.setCarrinho(carrinho);
        item.setProduto(produto);
        item.setQuantidade(quantidade);

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