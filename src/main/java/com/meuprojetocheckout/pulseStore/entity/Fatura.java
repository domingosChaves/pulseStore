package com.meuprojetocheckout.pulseStore.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name = "fatura")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Fatura {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String numero; // Número da fatura gerado

    @Temporal(TemporalType.TIMESTAMP)
    private Date dataEmissao; // Data da emissão

    private double valorTotal; // Valor total da fatura

    private String status; // Status do pagamento (pago, pendente, etc.)

    private String formaPagamento; // Forma de pagamento

    @ManyToOne
    @JoinColumn(name = "pedido_id") // Referência ao pedido correspondente
    private Pedido pedido; // Pedido associado à fatura
}