package com.meuprojetocheckout.pulseStore.models;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "produtos")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 255)
    private String nome;

    @Column(columnDefinition = "TEXT")
    private String descricao;

    @Column(nullable = false)
    private BigDecimal preco;

    @Column(nullable = false)
    private int estoque;

    @Column(name = "codigo_barras", length = 50)
    private String codigoBarras;

    @Column(name = "unidade_medida", length = 20)
    private String unidadeMedida;

    @Column(length = 100)
    private String categoria;

    @Column(length = 8)
    private String ncm; // Código NCM

    @Column(length = 3)
    private String cst; // Código da Situação Tributária

    @Column(precision = 10, scale = 2)
    private BigDecimal pis; // Percentual de PIS

    @Column(precision = 10, scale = 2)
    private BigDecimal cofins; // Percentual de COFINS

    @Column(precision = 10, scale = 2)
    private BigDecimal icms; // Percentual de ICMS

    @Column(name = "criado_em", updatable = false)
    private LocalDateTime criadoEm;

    @Column(name = "atualizado_em")
    private LocalDateTime atualizadoEm;

    @PrePersist
    public void prePersist() {
        LocalDateTime agora = LocalDateTime.now();
        this.criadoEm = agora;
        this.atualizadoEm = agora;
    }

    @PreUpdate
    public void preUpdate() {
        this.atualizadoEm = LocalDateTime.now();
    }
}
