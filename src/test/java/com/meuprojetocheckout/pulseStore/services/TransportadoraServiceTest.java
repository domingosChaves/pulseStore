package com.meuprojetocheckout.pulseStore.services;

import com.meuprojetocheckout.pulseStore.entity.Transportadora;
import com.meuprojetocheckout.pulseStore.repository.TransportadoraRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TransportadoraServiceTest {

    @InjectMocks
    private TransportadoraService transportadoraService;

    @Mock
    private TransportadoraRepository transportadoraRepository;

    private Transportadora transportadora;

    @BeforeEach
    void setUp() {
        transportadora = new Transportadora();
        transportadora.setId(1L);
        transportadora.setNome("Transportadora XYZ");
    }

    @Test
    void testarCriarTransportadora() {
        when(transportadoraRepository.save(any(Transportadora.class))).thenReturn(transportadora);

        Transportadora transportadoraResultado = transportadoraService.criarTransportadora(transportadora);

        verify(transportadoraRepository, times(1)).save(transportadora);
        assertEquals("Transportadora XYZ", transportadoraResultado.getNome());
    }

    @Test
    void testarObterTransportadora() {
        List<Transportadora> listaTransportadoras = new ArrayList<>();
        listaTransportadoras.add(transportadora);
        when(transportadoraRepository.findAll()).thenReturn(listaTransportadoras);

        List<Transportadora> transportadorasResultado = transportadoraService.obterTransportadora();

        assertEquals(1, transportadorasResultado.size());
        assertEquals("Transportadora XYZ", transportadorasResultado.get(0).getNome());
        verify(transportadoraRepository, times(1)).findAll();
    }
}