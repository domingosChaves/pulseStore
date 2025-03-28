package com.meuprojetocheckout.pulseStore.services;

import com.meuprojetocheckout.pulseStore.dto.ItemCarrinhoDTO;
import com.meuprojetocheckout.pulseStore.entity.Carrinho;
import com.meuprojetocheckout.pulseStore.entity.ItemCarrinho;
import com.meuprojetocheckout.pulseStore.entity.Produto;
import com.meuprojetocheckout.pulseStore.entity.Usuario;
import com.meuprojetocheckout.pulseStore.repository.CarrinhoRepository;
import com.meuprojetocheckout.pulseStore.repository.ItemCarrinhoRepository;
import com.meuprojetocheckout.pulseStore.repository.ProdutoRepository;
import com.meuprojetocheckout.pulseStore.repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CarrinhoServiceTest {

    @InjectMocks
    private CarrinhoService carrinhoService;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private CarrinhoRepository carrinhoRepository;

    @Mock
    private ItemCarrinhoRepository itemCarrinhoRepository;

    @Mock
    private ProdutoRepository produtoRepository;

    private Carrinho carrinho;
    private ItemCarrinho itemCarrinho;

    @BeforeEach
    void setUp() {
        carrinho = new Carrinho();
        carrinho.setId(1L);
        carrinho.setUsuarioId(1L);
        carrinho.setItens(new ArrayList<>());

        itemCarrinho = new ItemCarrinho(1L, 1L, 2);
        carrinho.adicionarItem(itemCarrinho);
    }


    @Test
    void testarConsultarCarrinhoPorId() {
        when(carrinhoRepository.findById(1L)).thenReturn(Optional.of(carrinho));

        Carrinho carrinhoResultado = carrinhoService.consultarCarrinhoPorId(1L);

        assertEquals(carrinho.getId(), carrinhoResultado.getId());
        verify(carrinhoRepository, times(1)).findById(1L);
    }

    @Test
    void testarConsultarCarrinhoPorIdNaoEncontrado() {
        when(carrinhoRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            carrinhoService.consultarCarrinhoPorId(1L);
        });
        assertEquals("Carrinho não encontrado", exception.getMessage());
    }

    @Test
    void testarAdicionarItem() {
        when(carrinhoRepository.findById(1L)).thenReturn(Optional.of(carrinho));
        when(itemCarrinhoRepository.save(any(ItemCarrinho.class))).thenReturn(itemCarrinho);

        carrinhoService.adicionarItem(1L, 1L, 5);

        assertEquals(2, carrinho.getItens().get(0).getQuantidade());
        verify(itemCarrinhoRepository, times(1)).save(any(ItemCarrinho.class));
    }


    @Test
    void testarRemoverItemNaoPertenceCarrinho() {
        when(carrinhoRepository.findById(1L)).thenReturn(Optional.of(carrinho));
        when(itemCarrinhoRepository.findById(2L)).thenReturn(Optional.of(new ItemCarrinho(2L, 2L, 1)));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            carrinhoService.removerItem(1L, 2L);
        });
        assertEquals("O item não pertence ao carrinho especificado", exception.getMessage());
    }

    @Test
    void testarApagarCarrinho() {
        when(carrinhoRepository.findById(1L)).thenReturn(Optional.of(carrinho));

        carrinhoService.apagarCarrinho(1L);

        verify(carrinhoRepository, times(1)).delete(carrinho);
    }

    @Test
    void testarObterProdutosNoCarrinho() {
        Produto produto = new Produto();
        produto.setId(1L);
        produto.setNome("Produto Exemplo");
        produto.setPreco(BigDecimal.valueOf(10));

        when(carrinhoRepository.findById(1L)).thenReturn(Optional.of(carrinho));
        when(produtoRepository.findById(1L)).thenReturn(Optional.of(produto));

        List<ItemCarrinhoDTO> produtosResultado = carrinhoService.obterProdutosNoCarrinho(1L);

        assertEquals(1, produtosResultado.size());
        assertEquals("Produto Exemplo", produtosResultado.get(0).getNomeProduto());
        assertEquals(2, produtosResultado.get(0).getQuantidade());
    }

    @Test
    void testarObterProdutosNoCarrinhoProdutoNaoEncontrado() {
        when(carrinhoRepository.findById(1L)).thenReturn(Optional.of(carrinho));
        when(produtoRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            carrinhoService.obterProdutosNoCarrinho(1L);
        });
        assertEquals("Produto não encontrado", exception.getMessage());
    }
}