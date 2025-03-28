package com.meuprojetocheckout.pulseStore.services;

import com.meuprojetocheckout.pulseStore.entity.Produto;
import com.meuprojetocheckout.pulseStore.repository.ProdutoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProdutoServiceTest {

    @InjectMocks
    private ProdutoService produtoService;

    @Mock
    private ProdutoRepository produtoRepository;

    private Produto produto;

    @BeforeEach
    void setUp() {
        produto = new Produto();
        produto.setId(1L);
        produto.setNome("Produto Exemplo");
        produto.setEstoque(10);
    }

    @Test
    void testarCadastrarProduto() {
        when(produtoRepository.save(any(Produto.class))).thenReturn(produto);

        Produto produtoResultado = produtoService.cadatrarProduto(produto);

        verify(produtoRepository, times(1)).save(produto);
        assertEquals("Produto Exemplo", produtoResultado.getNome());
    }

    @Test
    void testarBuscarProdutos() {
        List<Produto> listaProdutos = new ArrayList<>();
        listaProdutos.add(produto);
        when(produtoRepository.findByNomeContaining("Exemplo")).thenReturn(listaProdutos);

        List<Produto> produtosResultado = produtoService.buscarProdutos("Exemplo");

        assertEquals(1, produtosResultado.size());
        assertEquals("Produto Exemplo", produtosResultado.get(0).getNome());
        verify(produtoRepository, times(1)).findByNomeContaining("Exemplo");
    }

    @Test
    void testarConsultarProdutos() {
        List<Produto> listaProdutos = new ArrayList<>();
        listaProdutos.add(produto);
        when(produtoRepository.findAll()).thenReturn(listaProdutos);

        List<Produto> produtosResultado = produtoService.consultarProdutos();

        assertEquals(1, produtosResultado.size());
        verify(produtoRepository, times(1)).findAll();
    }

    @Test
    void testarConsultarProdutoPorId() {
        when(produtoRepository.findById(1L)).thenReturn(Optional.of(produto));

        Optional<Produto> produtoResultado = produtoService.consultarProdutoPorId(1L);

        assertEquals("Produto Exemplo", produtoResultado.get().getNome());
        verify(produtoRepository, times(1)).findById(1L);
    }

    @Test
    void testarAtualizarEstoque() {
        when(produtoRepository.findById(1L)).thenReturn(Optional.of(produto));

        produtoService.atualizarEstoque(1L, 5);

        assertEquals(15, produto.getEstoque());
        verify(produtoRepository, times(1)).findById(1L);
        verify(produtoRepository, times(1)).save(produto);
    }

    @Test
    void testarAtualizarEstoqueProdutoNaoEncontrado() {
        when(produtoRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            produtoService.atualizarEstoque(1L, 5);
        });

        assertEquals("Produto não encontrado", exception.getMessage());
    }
}