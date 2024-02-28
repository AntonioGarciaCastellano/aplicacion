package com.example.aplicacion.services;

import com.example.aplicacion.entity.Role;
import com.example.aplicacion.entity.Usuario;
import com.example.aplicacion.repositories.RoleRepository;
import com.example.aplicacion.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UsuarioServiceImpl implements UsuarioService{
    @Autowired
    UsuarioRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;
    @Override
    @Transactional(readOnly = true)
    public List<Usuario> findAll() {
        return userRepository.findAll();
    }

    @Override
    @Transactional
    public Usuario save(Usuario user) {
        Optional<Role> optionalRoleUser = roleRepository.findByName("ROLE_USER");
        List <Role> roles = new ArrayList<>();
        optionalRoleUser.ifPresent(roles::add);
        if(user.isAdmin()){
            Optional<Role> optionalRoleAdmin = roleRepository.findByName("ROLE_ADMIN");
            optionalRoleAdmin.ifPresent(roles::add);
        }
        user.setRoles(roles);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        return userRepository.save(user);
    }

    @Override
    @Transactional
    public Usuario findbyNombre(String nombre) {
        Optional<Usuario> usuarioOptional = userRepository.findByUsername(nombre);
        return usuarioOptional.orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));
    }
}
