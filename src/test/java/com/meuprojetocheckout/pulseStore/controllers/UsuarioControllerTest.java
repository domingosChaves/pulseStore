package com.meuprojetocheckout.pulseStore.controllers;

import com.meuprojetocheckout.pulseStore.entity.Usuario;
import com.meuprojetocheckout.pulseStore.services.UsuarioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UsuarioControllerTest {

    @InjectMocks
    private UsuarioController usuarioController;

    @Mock
    private UsuarioService usuarioService;

    private Usuario usuario;

    @BeforeEach
    void setUp() {
        usuario = new Usuario();
        usuario.setId(1L);
        usuario.setNome("Usuário Exemplo");
    }

    @Test
    void testarRegistrarUsuario() {
        when(usuarioService.cadastrarUsuario(any(Usuario.class))).thenReturn(usuario);

        ResponseEntity<Usuario> response = usuarioController.registrarUsuario(usuario);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(usuario, response.getBody());
        verify(usuarioService, times(1)).cadastrarUsuario(any(Usuario.class));
    }

    @Test
    void testarConsultarUsuarioPorId() {
        when(usuarioService.consultarUsuarioPorId(1L)).thenReturn(Optional.of(usuario));

        ResponseEntity<Usuario> response = usuarioController.consultarUsuarioPorId(1L);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(usuario, response.getBody());
    }

    @Test
    void testarConsultarUsuarioPorIdNaoEncontrado() {
        when(usuarioService.consultarUsuarioPorId(999L)).thenReturn(Optional.empty());

        ResponseEntity<Usuario> response = usuarioController.consultarUsuarioPorId(999L);

        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void testarAtualizarUsuario() {
        when(usuarioService.atualizarUsuario(any(Long.class), any(Usuario.class))).thenReturn(usuario);

        ResponseEntity<Usuario> response = usuarioController.atualizarUsuario(1L, usuario);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(usuario, response.getBody());
        verify(usuarioService, times(1)).atualizarUsuario(any(Long.class), any(Usuario.class));
    }
}