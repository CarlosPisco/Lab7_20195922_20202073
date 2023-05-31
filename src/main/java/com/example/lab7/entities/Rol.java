package com.example.lab7.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "rol")
public class Rol {
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "nombre_rol", length = 45)
    private String nombreRol;

}