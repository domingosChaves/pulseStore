package com.meuprojetocheckout.pulseStore.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "pedido") // Nome da tabela no banco de dados
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Geração automática de ID
    private Long id;

    @ManyToOne
    @JoinColumn(name = "cliente_id") // Referencia o cliente que fez o pedido
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "carrinho_id") // Referencia o cliente que fez o pedido
    private Carrinho carrinho;

    @ManyToOne
    @JoinColumn(name = "transportadora_id") // Referencia a transportadora escolhida
    private Transportadora transportadora;

    private double totalFrete;

}
