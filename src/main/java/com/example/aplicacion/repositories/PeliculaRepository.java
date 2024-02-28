package com.example.aplicacion.repositories;

import com.example.aplicacion.entity.Pelicula;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PeliculaRepository extends JpaRepository<Pelicula, Long> {
    Optional<Pelicula> findByTitulo(String Titulo);
}
