package com.example.aplicacion.services;


import com.example.aplicacion.entity.Usuario;

import java.util.List;
import java.util.Optional;

public interface UsuarioService {
    List<Usuario> findAll();
    Optional<Usuario> findById(Long id);
    Usuario save (Usuario product);
    Optional <Usuario> update(Long id, Usuario product);
    Optional<Usuario> delete(Long id);
}
