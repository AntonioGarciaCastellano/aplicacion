package com.example.aplicacion.services;

import com.example.aplicacion.entity.Pelicula;

import java.util.List;
import java.util.Optional;

public interface PeliculaService {
    List<Pelicula> findAll();
    Optional<Pelicula> findById(Long id);
    Pelicula save (Pelicula product);
    Optional <Pelicula> update(Long id, Pelicula product);
    Optional<Pelicula> delete(Long id);
}
