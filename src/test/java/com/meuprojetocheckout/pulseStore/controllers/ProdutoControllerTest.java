package com.meuprojetocheckout.pulseStore.controllers;

import com.meuprojetocheckout.pulseStore.entity.Produto;
import com.meuprojetocheckout.pulseStore.services.ProdutoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProdutoControllerTest {

    @InjectMocks
    private ProdutoController produtoController;

    @Mock
    private ProdutoService produtoService;

    private Produto produto;

    @BeforeEach
    void setUp() {
        produto = new Produto();
        produto.setId(1L);
        produto.setNome("Produto Exemplo");
        produto.setPreco(BigDecimal.valueOf(100.0));
    }

    @Test
    void testarCadastrarProdutos() {
        List<Produto> produtos = new ArrayList<>();
        produtos.add(produto);
        when(produtoService.cadatrarProduto(any(Produto.class))).thenReturn(produto);

        ResponseEntity<List<Produto>> response = produtoController.cadastrarProdutos(produtos);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(produtos.size(), response.getBody().size());
        verify(produtoService, times(1)).cadatrarProduto(any(Produto.class));
    }

    @Test
    void testarConsultarProdutos() {
        List<Produto> produtos = new ArrayList<>();
        produtos.add(produto);
        when(produtoService.consultarProdutos()).thenReturn(produtos);

        ResponseEntity<List<Produto>> response = produtoController.consultarProdutos();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(produtos.size(), response.getBody().size());
    }

    @Test
    void testarConsultarProdutoPorId() {
        when(produtoService.consultarProdutoPorId(1L)).thenReturn(Optional.of(produto));

        ResponseEntity<Produto> response = produtoController.consultarProdutoPorId(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(produto, response.getBody());
    }

    @Test
    void testarConsultarProdutoPorIdNaoEncontrado() {
        when(produtoService.consultarProdutoPorId(999L)).thenReturn(Optional.empty());

        ResponseEntity<Produto> response = produtoController.consultarProdutoPorId(999L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testarMovimentarEstoque() {
        ResponseEntity<Void> response = produtoController.movimentarEstoque(1L, 10);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(produtoService, times(1)).atualizarEstoque(1L, 10);
    }

    @Test
    void testarMovimentarEstoqueProdutoNaoEncontrado() {
        doThrow(new RuntimeException("Produto não encontrado")).when(produtoService).atualizarEstoque(999L, 5);

        ResponseEntity<Void> response = produtoController.movimentarEstoque(999L, 5);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}