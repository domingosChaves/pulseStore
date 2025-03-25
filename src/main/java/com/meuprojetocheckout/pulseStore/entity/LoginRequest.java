package com.meuprojetocheckout.pulseStore.entity;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginRequest {

    @NotBlank(message = "Email não pode estar vazio.")
    @Email(message = "Formato de email inválido.")
    private String email;

    @NotBlank(message = "Senha não pode estar vazia.")
    private String senha;
}