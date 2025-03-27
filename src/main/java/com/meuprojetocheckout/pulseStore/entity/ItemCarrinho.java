package com.meuprojetocheckout.pulseStore.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name = "itens_carrinho")
@Getter
@Setter
@NoArgsConstructor
public class ItemCarrinho {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "carrinho_id", nullable = false)
    private Long carrinhoId; // Armazena o ID do carrinho associado

    @Column(name = "produto_id", nullable = false)
    private Long produtoId; // Armazena o ID do produto associado

    @Column(nullable = false)
    private int quantidade; // Armazena a quantidade do produto

    @ManyToOne
    @JoinColumn(name = "carrinho_id", insertable = false, updatable = false)
    @JsonIgnore // Ignora essa referência ao serializar
    private Carrinho carrinho; // Relacionamento com a entidade Carrinho

    public ItemCarrinho(Long carrinhoId, Long produtoId, int quantidade) {
        this.carrinhoId = carrinhoId;
        this.produtoId = produtoId;
        this.quantidade = quantidade;
    }
}