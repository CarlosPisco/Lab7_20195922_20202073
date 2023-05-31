package com.example.lab7.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "usuarios")
public class Usuario {
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "nombre", length = 45)
    private String nombre;

    @Column(name = "apellido", length = 45)
    private String apellido;

    @Column(name = "correo", length = 45)
    private String correo;

    @Column(name = "username", length = 45)
    private String username;

    @Column(name = "password", length = 45)
    private String password;

    @Column(name = "estado_logico", length = 45)
    private String estadoLogico;

    @ManyToOne(optional = false)
    @JoinColumn(name = "rol_id", nullable = false)
    private Rol rol;

    @Column(name = "fecha_registro")
    private Instant fechaRegistro;

}