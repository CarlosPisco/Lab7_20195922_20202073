package com.example.lab7.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "desembolsos")
public class Desembolso {
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "monto_desembolso", length = 45)
    private String montoDesembolso;

    @Column(name = "fecha")
    private Instant fecha;

    @ManyToOne( optional = false)
    @JoinColumn(name = "creditos_id", nullable = false)
    private Credito creditos;

}