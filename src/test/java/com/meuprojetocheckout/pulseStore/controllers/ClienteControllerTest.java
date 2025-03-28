package com.meuprojetocheckout.pulseStore.controllers;

import com.meuprojetocheckout.pulseStore.entity.Cliente;
import com.meuprojetocheckout.pulseStore.services.ClienteService;
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
public class ClienteControllerTest {

    @InjectMocks
    private ClienteController clienteController;

    @Mock
    private ClienteService clienteService;

    private Cliente cliente;

    @BeforeEach
    void setUp() {
        cliente = new Cliente();
        cliente.setId(1L);
        cliente.setNome("Cliente Exemplo");
    }

    @Test
    void testarCriarCliente() {
        when(clienteService.criarCliente(any(Cliente.class))).thenReturn(cliente);

        ResponseEntity<Cliente> response = clienteController.criarCliente(cliente);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(cliente, response.getBody());
        verify(clienteService, times(1)).criarCliente(any(Cliente.class));
    }

    @Test
    void testarObterCliente() {
        when(clienteService.obterCliente(1L)).thenReturn(cliente);

        ResponseEntity<Cliente> response = clienteController.obterCliente(1L);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(cliente, response.getBody());
        verify(clienteService, times(1)).obterCliente(1L);
    }
}