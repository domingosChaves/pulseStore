package com.meuprojetocheckout.pulseStore.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data // Gera getters, setters, toString, equals e hashCode
@NoArgsConstructor // Gera um construtor sem argumentos
@AllArgsConstructor // Gera um construtor com todos os argumentos
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private String cpf; // CPF do cliente, deve ser validado

    private String telefone;

    // Campos de endereço
    private String logradouro;
    private String numero;
    private String complemento;
    private String bairro;
    private String cidade;
    private String estado;
    private String cep;

    @ManyToOne // Relacionamento muitos-para-um com Usuario
    @JoinColumn(name = "usuario_id") // Chave estrangeira
    private Usuario usuario;

    @Column(name = "criado_em")
    private LocalDateTime criadoEm;

    // Método para exibir dados do cliente na nota fiscal
    public String getInfoParaNotaFiscal() {
        String enderecoCompleto = String.format("%s, %s - %s, %s - %s, %s",
                logradouro, numero,
                bairro, cidade,
                estado, cep);

        return String.format("Nome: %s\nCPF: %s\nTelefone: %s\nEndereço: %s",
                nome, cpf, telefone, enderecoCompleto);
    }
}