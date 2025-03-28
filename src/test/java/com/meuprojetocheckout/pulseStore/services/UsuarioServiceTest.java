package com.meuprojetocheckout.pulseStore.services;

import com.meuprojetocheckout.pulseStore.entity.Usuario;
import com.meuprojetocheckout.pulseStore.repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UsuarioServiceTest {

    @InjectMocks
    private UsuarioService usuarioService;

    @Mock
    private UsuarioRepository usuarioRepository;

    private Usuario usuario;

    @BeforeEach
    void setUp() {
        usuario = new Usuario();
        usuario.setId(1L);
        usuario.setNome("John Doe");
    }

    @Test
    void testarCadastrarUsuario() {
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuario);

        Usuario usuarioResultado = usuarioService.cadastrarUsuario(usuario);

        verify(usuarioRepository, times(1)).save(usuario);
        assertEquals("John Doe", usuarioResultado.getNome());
    }

    @Test
    void testarConsultarUsuarioPorId() {
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));

        Optional<Usuario> usuarioResultado = usuarioService.consultarUsuarioPorId(1L);

        assertEquals("John Doe", usuarioResultado.get().getNome());
        verify(usuarioRepository, times(1)).findById(1L);
    }

    @Test
    void testarAtualizarUsuario() {
        Usuario usuarioAtualizado = new Usuario();
        usuarioAtualizado.setNome("Jane Doe");

        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuario);

        Usuario usuarioResultado = usuarioService.atualizarUsuario(1L, usuarioAtualizado);

        assertEquals("Jane Doe", usuarioResultado.getNome());
        verify(usuarioRepository, times(1)).findById(1L);
        verify(usuarioRepository, times(1)).save(usuario);
    }

    @Test
    void testarAtualizarUsuarioUsuarioNaoEncontrado() {
        Usuario usuarioAtualizado = new Usuario();
        usuarioAtualizado.setNome("Jane Doe");

        when(usuarioRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException exception = org.junit.jupiter.api.Assertions.assertThrows(RuntimeException.class, () -> {
            usuarioService.atualizarUsuario(1L, usuarioAtualizado);
        });

        assertEquals("Usuário não encontrado", exception.getMessage());
    }
}