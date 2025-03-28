package com.meuprojetocheckout.pulseStore.services;

import com.meuprojetocheckout.pulseStore.entity.Cliente;
import com.meuprojetocheckout.pulseStore.repository.ClienteRepository;
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
public class ClienteServiceTest {

    @InjectMocks
    private ClienteService clienteService;

    @Mock
    private ClienteRepository clienteRepository;

    private Cliente cliente;

    @BeforeEach
    void setUp() {
        cliente = new Cliente();
        cliente.setId(1L);
        cliente.setNome("Cliente Exemplo");
    }

    @Test
    void testarCriarCliente() {
        when(clienteRepository.save(any(Cliente.class))).thenReturn(cliente);

        Cliente clienteResultado = clienteService.criarCliente(cliente);

        verify(clienteRepository, times(1)).save(cliente);
        assertEquals("Cliente Exemplo", clienteResultado.getNome());
    }

    @Test
    void testarObterCliente() {
        when(clienteRepository.findById(1L)).thenReturn(Optional.of(cliente));

        Cliente clienteResultado = clienteService.obterCliente(1L);

        assertEquals("Cliente Exemplo", clienteResultado.getNome());
        verify(clienteRepository, times(1)).findById(1L);
    }

    @Test
    void testarObterClienteNaoEncontrado() {
        when(clienteRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            clienteService.obterCliente(1L);
        });

        assertEquals("Cliente não encontrado", exception.getMessage());
    }
}