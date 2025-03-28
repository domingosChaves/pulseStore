package com.meuprojetocheckout.pulseStore.services;

import com.meuprojetocheckout.pulseStore.Enum.Pagamento;
import com.meuprojetocheckout.pulseStore.entity.*;
import com.meuprojetocheckout.pulseStore.repository.ItemVendaRepository;
import com.meuprojetocheckout.pulseStore.repository.ProdutoRepository;
import com.meuprojetocheckout.pulseStore.repository.VendaRepository;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class VendaServiceTest {

    @InjectMocks
    private VendaService vendaService;

    @Mock
    private VendaRepository vendaRepository;

    @Mock
    private ItemVendaRepository itemVendaRepository;

    @Mock
    private ProdutoRepository produtoRepository;

    private Usuario usuario;
    private Produto produto;
    private ItemVenda itemVenda;

    @BeforeEach
    void setUp() {
        usuario = new Usuario();
        usuario.setId(1L);

        produto = new Produto();
        produto.setId(1L);
        produto.setEstoque(10);

        itemVenda = new ItemVenda();
        itemVenda.setProduto(produto);
        itemVenda.setQuantidade(2);
    }

    @Test
    void testarRealizarVenda() {
        List<ItemVenda> itens = new ArrayList<>();
        itens.add(itemVenda);

        when(vendaRepository.save(any(Venda.class))).thenReturn(new Venda());
        when(produtoRepository.findById(produto.getId())).thenReturn(Optional.of(produto));
        when(itemVendaRepository.save(any(ItemVenda.class))).thenAnswer(invocation -> invocation.getArguments()[0]);

        Venda vendaResultado = vendaService.realizarVenda(itens, usuario, Pagamento.CARTAO, new Endereco());

        verify(vendaRepository, times(1)).save(any(Venda.class));
        verify(produtoRepository, times(1)).findById(produto.getId());
        verify(itemVendaRepository, times(1)).save(any(ItemVenda.class));

        assertEquals(8, produto.getEstoque());
    }
}