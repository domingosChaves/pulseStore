package com.meuprojetocheckout.pulseStore.services;

import com.meuprojetocheckout.pulseStore.entity.Usuario;
import com.meuprojetocheckout.pulseStore.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public Usuario cadastrarUsuario(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    // Método para buscar usuário por ID
    public Optional<Usuario> consultarUsuarioPorId(Long id) {
        return usuarioRepository.findById(id);
    }

    // Método para alterar dados ou recuperar senha
    public Usuario atualizarUsuario(Long id, Usuario usuarioAtualizado) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findById(id);
        if (usuarioOpt.isPresent()) {
            Usuario usuario = usuarioOpt.get();
            usuario.setNome(usuarioAtualizado.getNome());
            // Atualize outros campos conforme necessário
            return usuarioRepository.save(usuario);
        }
        throw new RuntimeException("Usuário não encontrado");
    }
}
