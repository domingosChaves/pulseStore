package com.meuprojetocheckout.pulseStore.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ItemCarrinhoDTO {
    private Long produtoId;
    private String nomeProduto;
    private BigDecimal precoProduto;
    private int quantidade;

    public ItemCarrinhoDTO(Long produtoId, String nomeProduto, BigDecimal precoProduto, int quantidade) {
        this.produtoId = produtoId;
        this.nomeProduto = nomeProduto;
        this.precoProduto = precoProduto;
        this.quantidade = quantidade;
    }
}
