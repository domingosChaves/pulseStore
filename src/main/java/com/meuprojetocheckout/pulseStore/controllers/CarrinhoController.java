package com.meuprojetocheckout.pulseStore.controllers;

import com.meuprojetocheckout.pulseStore.dto.ItemCarrinhoDTO;
import com.meuprojetocheckout.pulseStore.entity.Carrinho;
import com.meuprojetocheckout.pulseStore.entity.CarrinhoRequest;
import com.meuprojetocheckout.pulseStore.services.CarrinhoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ResponseEntity<Void> adicionarItem(@PathVariable Long carrinhoId, @PathVariable Long produtoId, @PathVariable int quantidade) {
        carrinhoService.adicionarItem(carrinhoId, produtoId, quantidade);
        return ResponseEntity.ok().build();
    }

    // Endpoint para visualizar o carrinho
    @GetMapping("/{carrinhoId}")
    public ResponseEntity<Carrinho> obterCarrinho(@PathVariable Long carrinhoId) {
        Carrinho carrinho = carrinhoService.consultarCarrinhoPorId(carrinhoId);
        return ResponseEntity.ok(carrinho);
    }

    // Endpoint para consultar carrinhos por ID de usuário
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<Carrinho>> consultarCarrinhosPorUsuario(@PathVariable Long usuarioId) {
        List<Carrinho> carrinhos = carrinhoService.consultarCarrinhosPorUsuarioId(usuarioId);
        return ResponseEntity.ok(carrinhos);
    }

    // Endpoint para consultar carrinho por ID e ID do usuário
    @GetMapping("/{carrinhoId}/usuario/{usuarioId}")
    public ResponseEntity<Carrinho> consultarCarrinhoPorIdEUsuario(@PathVariable Long carrinhoId, @PathVariable Long usuarioId) {
        Carrinho carrinho = carrinhoService.consultarCarrinhoPorIdEUsuario(carrinhoId, usuarioId);
        return ResponseEntity.ok(carrinho);
    }

    // Endpoint para remover um item do carrinho
    @DeleteMapping("/{carrinhoId}/itens/{itemId}")
    public ResponseEntity<Void> removerItem(@PathVariable Long carrinhoId, @PathVariable Long itemId) {
        carrinhoService.removerItem(carrinhoId, itemId);
        return ResponseEntity.noContent().build();
    }

    // Endpoint para apagar o carrinho
    @DeleteMapping("/{carrinhoId}")
    public ResponseEntity<Void> apagarCarrinho(@PathVariable Long carrinhoId) {
        carrinhoService.apagarCarrinho(carrinhoId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{carrinhoId}/produtos")
    public ResponseEntity<List<ItemCarrinhoDTO>> obterProdutosNoCarrinho(@PathVariable Long carrinhoId) {
        List<ItemCarrinhoDTO> itens = carrinhoService.obterProdutosNoCarrinho(carrinhoId);
        return ResponseEntity.ok(itens);
    }
}