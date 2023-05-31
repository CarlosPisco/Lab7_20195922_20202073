package com.example.lab7.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "cuotas")
public class Cuota {
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "numero_cuota")
    private Integer numeroCuota;

    @Column(name = "monto")
    private Double monto;

    @ManyToOne(optional = false)
    @JoinColumn(name = "creditos_id", nullable = false)
    private Credito creditos;

}