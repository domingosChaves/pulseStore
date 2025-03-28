package com.meuprojetocheckout.pulseStore.controllers;

import com.meuprojetocheckout.pulseStore.dto.ItemCarrinhoDTO;
import com.meuprojetocheckout.pulseStore.entity.Carrinho;
import com.meuprojetocheckout.pulseStore.entity.CarrinhoRequest;
import com.meuprojetocheckout.pulseStore.services.CarrinhoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CarrinhoControllerTest {

    @InjectMocks
    private CarrinhoController carrinhoController;

    @Mock
    private CarrinhoService carrinhoService;

    private CarrinhoRequest carrinhoRequest;
    private Carrinho carrinho;

    @BeforeEach
    void setUp() {
        carrinhoRequest = new CarrinhoRequest();
        carrinhoRequest.setUsuarioId(1L);

        carrinho = new Carrinho();
        carrinho.setId(1L);
        carrinho.setUsuarioId(1L);
        carrinho.setItens(new ArrayList<>());
    }

    @Test
    void testarCriarCarrinho() {
        // Arrange
        when(carrinhoService.criarCarrinho(1L)).thenReturn(carrinho);

        // Act
        ResponseEntity<Carrinho> response = carrinhoController.criarCarrinho(carrinhoRequest);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(carrinho, response.getBody());
    }

    @Test
    void testarAdicionarItem() {
        // Act
        ResponseEntity<Void> response = carrinhoController.adicionarItem(1L, 1L, 2);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        verify(carrinhoService, times(1)).adicionarItem(1L, 1L, 2);
    }

    @Test
    void testarObterCarrinho() {
        // Arrange
        when(carrinhoService.consultarCarrinhoPorId(1L)).thenReturn(carrinho);

        // Act
        ResponseEntity<Carrinho> response = carrinhoController.obterCarrinho(1L);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(carrinho, response.getBody());
    }

    @Test
    void testarConsultarCarrinhosPorUsuario() {
        // Arrange
        List<Carrinho> carrinhos = new ArrayList<>();
        carrinhos.add(carrinho);
        when(carrinhoService.consultarCarrinhosPorUsuarioId(1L)).thenReturn(carrinhos);

        // Act
        ResponseEntity<List<Carrinho>> response = carrinhoController.consultarCarrinhosPorUsuario(1L);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(carrinhos, response.getBody());
    }

    @Test
    void testarConsultarCarrinhoPorIdEUsuario() {
        // Arrange
        when(carrinhoService.consultarCarrinhoPorIdEUsuario(1L, 1L)).thenReturn(carrinho);

        // Act
        ResponseEntity<Carrinho> response = carrinhoController.consultarCarrinhoPorIdEUsuario(1L, 1L);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(carrinho, response.getBody());
    }

    @Test
    void testarRemoverItem() {
        // Act
        ResponseEntity<Void> response = carrinhoController.removerItem(1L, 1L);

        // Assert
        assertEquals(204, response.getStatusCodeValue());
        verify(carrinhoService, times(1)).removerItem(1L, 1L);
    }

    @Test
    void testarApagarCarrinho() {
        // Act
        ResponseEntity<Void> response = carrinhoController.apagarCarrinho(1L);

        // Assert
        assertEquals(204, response.getStatusCodeValue());
        verify(carrinhoService, times(1)).apagarCarrinho(1L);
    }

}