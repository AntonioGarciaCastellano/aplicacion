package com.example.aplicacion.services;

import com.example.aplicacion.entity.Pelicula;
import com.example.aplicacion.entity.Usuario;
import com.example.aplicacion.repositories.PeliculaRepository;
import com.example.aplicacion.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class PeliculaServiceImpl implements PeliculaService{
    @Autowired
    private PeliculaRepository peliculaRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Pelicula> findAll() {
        return  peliculaRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Pelicula> findById(Long id) {
        return peliculaRepository.findById(id);
    }

    @Override
    @Transactional
    public Pelicula save(Pelicula pelicula) {
        return peliculaRepository.save(pelicula);
    }

    @Override
    @Transactional
    public Optional <Pelicula> update(Long id, Pelicula pelicula) {
        Optional <Pelicula> peliculaOptional = peliculaRepository.findById(id);
        if(peliculaOptional.isPresent()){
            Pelicula peliculaDb = peliculaOptional.orElseThrow();
            peliculaDb.setTitulo(pelicula.getTitulo());
            peliculaDb.setAutor(pelicula.getAutor());
            peliculaDb.setCategoria(pelicula.getCategoria());
            peliculaDb.setResenias(pelicula.getResenias());
            peliculaDb.setUsuariosPeliculas(pelicula.getUsuariosPeliculas());
            return Optional.of(peliculaRepository.save(peliculaDb));
        }
        return peliculaOptional;
    }

    @Override
    @Transactional
    public Optional<Pelicula> delete(Long id) {
        Optional <Pelicula> peliculaOptional = peliculaRepository.findById(id);
        peliculaOptional.ifPresent( peliculaDb -> peliculaRepository.delete(peliculaDb));
        return peliculaOptional;
    }
}
