package com.meuprojetocheckout.pulseStore.controllers;

import com.meuprojetocheckout.pulseStore.entity.Pedido;
import com.meuprojetocheckout.pulseStore.services.PedidoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PedidoControllerTest {

    @InjectMocks
    private PedidoController pedidoController;

    @Mock
    private PedidoService pedidoService;

    private Pedido pedido;

    @BeforeEach
    void setUp() {
        pedido = new Pedido();
        pedido.setId(1L);
    }

    @Test
    void testarCriarPedido() {
        when(pedidoService.criarPedido(any(Pedido.class))).thenReturn(pedido);

        ResponseEntity<Pedido> response = pedidoController.criarPedido(pedido);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(pedido, response.getBody());
        verify(pedidoService, times(1)).criarPedido(any(Pedido.class));
    }

    @Test
    void testarObterPedido() {
        when(pedidoService.obterPedido(1L)).thenReturn(pedido);

        ResponseEntity<Pedido> response = pedidoController.obterPedido(1L);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(pedido, response.getBody());
        verify(pedidoService, times(1)).obterPedido(1L);
    }
}