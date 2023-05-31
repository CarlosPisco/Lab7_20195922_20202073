package com.example.lab7.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "historial")
public class Historial {
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne( optional = false)
    @JoinColumn(name = "creditos_id", nullable = false)
    private Credito creditos;

    @ManyToOne( optional = false)
    @JoinColumn(name = "usuarios_id", nullable = false)
    private Usuario usuarios;

}