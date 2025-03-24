package com.meuprojetocheckout.pulseStore.repository;

import com.meuprojetocheckout.pulseStore.models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario,Long> {
    Usuario findByEmail(String email);
}
