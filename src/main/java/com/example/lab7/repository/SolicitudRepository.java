package com.example.lab7.repository;

import com.example.lab7.entities.Solicitude;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface SolicitudRepository extends JpaRepository<Solicitude,Integer> {
}
