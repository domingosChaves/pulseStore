package com.meuprojetocheckout.pulseStore.controllers;

import com.meuprojetocheckout.pulseStore.entity.Produto;
import com.meuprojetocheckout.pulseStore.services.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/produtos")
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;

    @PostMapping
    public ResponseEntity<List<Produto>> cadastrarProdutos(@RequestBody List<Produto> produtos) {
        List<Produto> produtosCadastrados = produtos.stream()
                .map(produto -> (produtoService.cadatrarProduto(produto))) // Chama o método correto que cadastra um único produto
                .toList(); // Coleta os produtos cadastrados em uma lista
        return new ResponseEntity<>(produtosCadastrados, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Produto>> consultarProdutos() {
        List<Produto> produtos = produtoService.consultarProdutos();
        return ResponseEntity.ok(produtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Produto> consultarProdutoPorId(@PathVariable Long id) {
        Optional<Produto> produtoOptional = produtoService.consultarProdutoPorId(id);
        return produtoOptional.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PutMapping("/{id}/estoque")
    public ResponseEntity<Void> movimentarEstoque(@PathVariable Long id, @RequestParam int quantidade) {
        try {
            produtoService.atualizarEstoque(id, quantidade);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}