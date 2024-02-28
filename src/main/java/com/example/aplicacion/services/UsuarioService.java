package com.example.aplicacion.services;


import com.example.aplicacion.entity.Usuario;

import java.util.List;
import java.util.Optional;

public interface UsuarioService {

    List<Usuario> findAll();

    Usuario save(Usuario user);

    Usuario findbyNombre(String nombre);


}
