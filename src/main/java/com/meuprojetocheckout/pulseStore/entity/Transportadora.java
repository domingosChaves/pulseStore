package com.meuprojetocheckout.pulseStore.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "transportadora") // Nome da tabela no banco de dados
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Transportadora {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Uso de identidade para geração automática
    private Long id;

    private String nome;

    private double freteFixo;
}