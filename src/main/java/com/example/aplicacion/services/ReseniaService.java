package com.example.aplicacion.services;


import com.example.aplicacion.entity.Resenia;

import java.util.List;
import java.util.Optional;

public interface ReseniaService {
    List<Resenia> findAll();
    Optional<Resenia> findById(Long id);
    Resenia save (Resenia product);
    Optional <Resenia> update(Long id, Resenia product);
    Optional<Resenia> delete(Long id);
}
