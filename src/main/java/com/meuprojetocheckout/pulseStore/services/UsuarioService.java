package com.meuprojetocheckout.pulseStore.services;

import com.meuprojetocheckout.pulseStore.models.Usuario;
import com.meuprojetocheckout.pulseStore.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public Usuario cadastrarUsuario(Usuario usuario){
        return usuarioRepository.save(usuario);
    }
    public Optional<Usuario> buscarUsuarioPorEmail(String email){
        return Optional.ofNullable(usuarioRepository.findByEmail(email));
    }
}
