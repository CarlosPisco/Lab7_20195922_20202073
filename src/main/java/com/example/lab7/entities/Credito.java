package com.example.lab7.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "creditos")
public class Credito {
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "monto", length = 45)
    private String monto;

    @ManyToOne( optional = false)
    @JoinColumn(name = "usuarios_id", nullable = false)
    private Usuario usuarios;

    @Column(name = "fecha")
    private Instant fecha;

    @Column(name = "interes")
    private Double interes;

}