package com.example.aplicacion.services;

import com.example.aplicacion.entity.Usuario;
import com.example.aplicacion.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioServiceImpl implements UsuarioService{

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Usuario> findAll() {
        return  usuarioRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Usuario> findById(Long id) {
        return usuarioRepository.findById(id);
    }

    @Override
    @Transactional
    public Usuario save(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    @Override
    @Transactional
    public Optional <Usuario> update(Long id, Usuario usuario) {
        Optional <Usuario> usuarioOptional = usuarioRepository.findById(id);
        if(usuarioOptional.isPresent()){
            Usuario usuarioDb = usuarioOptional.orElseThrow();
            usuarioDb.setNombre(usuario.getNombre());
            usuarioDb.setContrasenia(usuario.getContrasenia());
            usuarioDb.setPeliculas(usuario.getPeliculas());
            usuarioDb.setResenias(usuario.getResenias());
            return Optional.of(usuarioRepository.save(usuarioDb));
        }
        return usuarioOptional;
    }

    @Override
    @Transactional
    public Optional<Usuario> delete(Long id) {
        Optional <Usuario> usuarioOptional = usuarioRepository.findById(id);
        usuarioOptional.ifPresent( usuarioDb -> usuarioRepository.delete(usuarioDb));
        return usuarioOptional;
    }
}
