package com.example.aplicacion.services;

import com.example.aplicacion.entity.Role;

import java.util.List;
import java.util.Optional;

public interface RoleService {
    List<Role> findAll();
    Optional<Role> findById(Long id);
    Role save (Role role);
    Optional <Role> update(Long id, Role role);
    Optional<Role> delete(Long id);
}
