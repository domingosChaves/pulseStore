package com.meuprojetocheckout.pulseStore.controllers;

import com.meuprojetocheckout.pulseStore.entity.Transportadora;
import com.meuprojetocheckout.pulseStore.services.TransportadoraService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TransportadoraControllerTest {

    @InjectMocks
    private TransportadoraController transportadoraController;

    @Mock
    private TransportadoraService transportadoraService;

    private Transportadora transportadora;

    @BeforeEach
    void setUp() {
        transportadora = new Transportadora();
        transportadora.setId(1L);
        transportadora.setNome("Transportadora Exemplo");
    }

    @Test
    void testarCriarTransportadora() {
        when(transportadoraService.criarTransportadora(any(Transportadora.class))).thenReturn(transportadora);

        ResponseEntity<Transportadora> response = transportadoraController.criarTransportadora(transportadora);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(transportadora, response.getBody());
        verify(transportadoraService, times(1)).criarTransportadora(any(Transportadora.class));
    }

    @Test
    void testarObterTransportadora() {
        List<Transportadora> transportadoras = new ArrayList<>();
        transportadoras.add(transportadora);
        when(transportadoraService.obterTransportadora()).thenReturn(transportadoras);

        ResponseEntity<List<Transportadora>> response = transportadoraController.obterTransportadora();

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(transportadoras.size(), response.getBody().size());
        assertEquals(transportadora, response.getBody().get(0));
    }
}