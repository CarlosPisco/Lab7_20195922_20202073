package com.example.lab7.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "solicitudes")
public class Solicitude {
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "solicitud_producto", length = 45)
    private String solicitudProducto;

    @Column(name = "solicitud_monto")
    private Double solicitudMonto;

    @Column(name = "solicitud_fecha")
    private Instant solicitudFecha;

    @ManyToOne( optional = false)
    @JoinColumn(name = "usuarios_id", nullable = false)
    private Usuario usuarios;

    @Column(name = "solicitud_estado", length = 45)
    private String solicitudEstado;

}