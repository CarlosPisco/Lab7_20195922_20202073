package com.example.lab7.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "pagos")
public class Pago {
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "monto")
    private Double monto;

    @ManyToOne(optional = false)
    @JoinColumn(name = "usuarios_id", nullable = false)
    private Usuario usuarios;

    @Column(name = "tipo_pago", length = 45)
    private String tipoPago;

    @Column(name = "fecha")
    private LocalDateTime fecha;

    @ManyToOne( optional = false)
    @JoinColumn(name = "creditos_id", nullable = false)
    private Credito creditos;

}