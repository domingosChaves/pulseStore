package com.meuprojetocheckout.pulseStore.services;

import com.meuprojetocheckout.pulseStore.entity.Pedido;
import com.meuprojetocheckout.pulseStore.repository.PedidoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PedidoServiceTest {

    @InjectMocks
    private PedidoService pedidoService;

    @Mock
    private PedidoRepository pedidoRepository;

    private Pedido pedido;

    @BeforeEach
    void setUp() {
        pedido = new Pedido();
        pedido.setId(1L);
    }

    @Test
    void testarCriarPedido() {
        when(pedidoRepository.save(any(Pedido.class))).thenReturn(pedido);

        Pedido pedidoResultado = pedidoService.criarPedido(pedido);

        verify(pedidoRepository, times(1)).save(pedido);
        assertEquals(pedido.getId(), pedidoResultado.getId());
    }

    @Test
    void testarObterPedido() {
        when(pedidoRepository.findById(1L)).thenReturn(Optional.of(pedido));

        Pedido pedidoResultado = pedidoService.obterPedido(1L);

        assertEquals(pedido.getId(), pedidoResultado.getId());
        verify(pedidoRepository, times(1)).findById(1L);
    }

    @Test
    void testarObterPedidoNaoEncontrado() {
        when(pedidoRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            pedidoService.obterPedido(1L);
        });

        assertEquals("Pedido não encontrado com ID: 1", exception.getMessage());
    }
}